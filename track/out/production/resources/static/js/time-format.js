export function formatTime(secondsLearned) {
    const days = Math.floor(secondsLearned / (24 * 3600));
    secondsLearned %= (24 * 3600);
    const hours = Math.floor(secondsLearned / 3600);
    secondsLearned %= 3600;
    const minutes = Math.floor(secondsLearned / 60);
    secondsLearned %= 60;
    const remainingSeconds = secondsLearned;
    return `${String(days).padStart(2, '0')}d ${String(hours).padStart(2, '0')}h ${String(minutes).padStart(2, '0')}m ${String(remainingSeconds).padStart(2, '0')}s`;
}

export function formatDateGer(dateString) {
    const date = new Date(dateString);
    const options = { day: 'numeric', month: 'short', year: 'numeric' };
    return date.toLocaleDateString('de-DE', options);
}

export function calculateSeconds(start, finish) {
    const s = new Date(start);
    const f = new Date(finish);
    const diffMilliseconds = f - s;
    const diffSeconds = diffMilliseconds / 1000;
    const diffMinutes = diffSeconds / 60;
    return diffMinutes;
}