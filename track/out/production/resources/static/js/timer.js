import * as api from './api.js';
import * as tf from './time-format.js';

let running = false;
const PLAY = "⏵";
const STOP = "stop";
let timer;


async function startTimer(fachId) {
    let seconds = await api.getSeconds(fachId);

    if (running) {
        timer = setInterval(() => seconds = updateTime(fachId, seconds), 1000);
    } else {
        running = false;
    }
}

function updateTime(fachId, seconds) {
    seconds++;
    document.getElementById(fachId).innerText = tf.formatTime(seconds);
    document.getElementById(fachId).dataset.value = seconds;
    return seconds;
}

document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll(".start-button").forEach(button => {
        button.addEventListener("click", function() {
            const fachId = this.dataset.fachid;
            if(running) {
                clearInterval(timer);
                api.postFinishInterval(fachId)
                api.postNewSeconds(fachId)
                changeButtonSymbol(fachId, PLAY);
            } else {
                api.postStartInterval(fachId);
                changeButtonSymbol(fachId, STOP);
                startTimer(fachId)
            }
            running = running === false;
        });
    });
});

function changeButtonSymbol(fachId, symbol) {
    const button = document.querySelector(`button[data-fachid='${fachId}']`);
    if (button) {
        const symbolSpan = button.querySelector("#symbol");
        if (symbolSpan) symbolSpan.innerText = symbol;
    }
}