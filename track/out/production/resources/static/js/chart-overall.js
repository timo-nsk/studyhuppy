import { fetchStatisticEndpoint } from './api.js';
import * as tf from "./time-format.js";
let dataOverall = await fetchStatisticEndpoint("/statistics/api/stats/fetch/0")

initChartOverall("time learned overall", getOverallLabels(dataOverall.content), getOverallDataSeconds(dataOverall.content), "chart-total");

function initChartOverall(label, labels, data, elementId) {
    const ctx = document.getElementById(elementId).getContext('2d');
    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                label: label,
                backgroundColor: 'rgba(84, 19, 136, 1)',
                data: data,
                borderWidth: 1
                }]
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

function getOverallLabels(data) {
    let labels = [];
    for(let i = 0; i < data.length; i++) {
        let entry = data[i].date;
        labels.push(tf.formatDateGer(entry));
    }
    return labels;
}

function getOverallDataSeconds(data) {
    let timeLearned = [];
    for(let i = 0; i < data.length; i++) {
        let entry = data[i];
        let seconds = 0
        for (let j = 0; j < entry.interval.length; j++) {
            let currentInterval = entry.interval[j];
            let secondsFromInterval = tf.calculateSeconds(currentInterval.start, currentInterval.finish)
            seconds += secondsFromInterval;
        }
        timeLearned.push(seconds)
    }
    return timeLearned;
}