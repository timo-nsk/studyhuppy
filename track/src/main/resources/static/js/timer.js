import * as api from './api.js';
import * as tf from './time-format.js';

let running = false;
const PLAY = 'fa-play';
const STOP = 'fa-stop';
let timer;
let sessionSecondsLearned = 0;


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
    sessionSecondsLearned++;
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
                api.postNewSeconds(fachId, sessionSecondsLearned)
                sessionSecondsLearned = 0;
                switchButtonStyle(fachId, 1);
            } else {
                switchButtonStyle(fachId, 0);
                startTimer(fachId)
            }
            running = running === false;
        });
    });
});

function switchButtonStyle(fachId, flag) {
    const button = document.querySelector(`button[data-fachid='${fachId}']`);
    const icon = button.querySelector("#button-icon");

    if (flag === 1) {
        icon.classList.add(PLAY)
        icon.classList.remove(STOP);
        button.classList.remove("stop")
        button.classList.add("play")
    } else if (flag === 0) {
        icon.classList.add(STOP)
        icon.classList.remove(PLAY);
        button.classList.add("stop")
        button.classList.remove("play")
    }
}