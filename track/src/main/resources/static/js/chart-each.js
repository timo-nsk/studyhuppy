import { fetchStatisticEndpoint } from './api.js';
import {formatDateGer} from "./time-format.js";

let dataEach = await fetchStatisticEndpoint("/statistics/api/stats")

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
            responsive: true,
            scales: {
                x: {
                    stacked: true,
                },
                y: {
                    stacked: true
                }
            }
        }
    });
}

function getEachLabels(data) {
    return Object.keys(data).map(formatDateGer);
}

function getDatasets(data) {
    // Erstelle ein leeres Array für die Datasets
    const datasets = [];

    const colors = [
        'rgba(84, 19, 136, 1)',
        'rgba(217, 3, 104, 1)',
        'rgba(241, 233, 218, 1)',
        'rgba(46, 41, 78, 1)',
        'rgba(255, 212, 0, 1)'
    ];

    let colorIndex = 0; // Index für Farbzuweisung

    // Iteriere über jedes Datum in den Daten
    Object.keys(data).forEach(date => {
        // Iteriere über jedes Modul des jeweiligen Datums
        data[date].forEach(entry => {
            // Überprüfe, ob das Dataset für das Modul bereits existiert
            let dataset = datasets.find(ds => ds.label === entry.modulName);

            // Wenn das Dataset nicht existiert, erstelle ein neues
            if (!dataset) {
                dataset = {
                    label: entry.modulName,
                    data: [],
                    backgroundColor: colors[colorIndex % colors.length]
                };
                datasets.push(dataset);
            }
            colorIndex++;
            // Füge die gelernte Zeit (in Sekunden) zum entsprechenden Dataset hinzu
            dataset.data.push(parseInt(entry.secondsLearned) / 60);
        });
    });

    return datasets;
}