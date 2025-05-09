import { fetchStatisticEndpoint } from './api.js';
import {formatDateGer} from "./time-format.js";

let dataOverall = await fetchStatisticEndpoint("/statistics/api/stats")

initChartOverall("Minuten gelernt", getOverallLabels(dataOverall), getOverallDataMinutes(dataOverall), "chart-total");

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
    return Object.keys(data).map(formatDateGer);
}

function getOverallDataMinutes(data) {
    let timeLearned = [];

    for (const key in data) {
        let seconds = 0;
        let entry = data[key];

        for (let j = 0; j < entry.length; j++) {
            seconds += parseInt(entry[j].secondsLearned, 10);
        }
        timeLearned.push(seconds / 60);
    }

    return timeLearned;
}