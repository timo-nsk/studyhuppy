import { fetchStatisticEndpoint } from './api.js';
import * as tf from "./time-format.js";

let dataEach = await fetchStatisticEndpoint("/statistics/api/stats/fetch-modules")

initChartEachModule("time learned per modul", getEachLabels(dataEach), getDatasets(dataEach), "chart-each");

function initChartEachModule(label, labels, datasets, elementId) {
    const ctx = document.getElementById(elementId).getContext('2d');
    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: datasets
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function getDatasets(data) {
    let datasets = new Map();
    let dates = Object.keys(data).sort();

    // 🌈 Farbpalette für Module
    const colors = [
        'rgba(84, 19, 136, 1)',
        'rgba(217, 3, 104, 1)',
        'rgba(241, 233, 218, 1)',
        'rgba(46, 41, 78, 1)',
        'rgba(255, 212, 0, 1)'
    ];

    let colorIndex = 0; // Index für Farbzuweisung

    for (let [date, entries] of Object.entries(data)) {
        for (let entry of entries) {
            for (let interval of entry.interval) {
                let seconds = tf.calculateSeconds(interval.start, interval.finish);
                let modulName = interval.modulName;
                if (!datasets.has(modulName)) {
                    datasets.set(modulName, {
                        label: modulName,
                        data: new Array(dates.length).fill(0),
                        backgroundColor: colors[colorIndex % colors.length],
                    });
                    colorIndex++;
                }

                let index = dates.indexOf(date);
                datasets.get(modulName).data[index] += seconds;
            }
        }
    }
    return Array.from(datasets.values());
}


function getEachLabels(data) {
    const allKeys = Object.keys(data);
    let labels = [];
    for(let i = 0; i < allKeys.length; i++) {
        labels.push(tf.formatDateGer(allKeys[i]));
    }
    return labels.sort();
}