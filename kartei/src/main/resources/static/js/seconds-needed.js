// Funktion zum Erhöhen des Werts
function increaseSeconds() {
    const inputElement = document.querySelector('input[name="secondsNeeded"]');

    // Alle 1 Sekunde den Wert um 1 erhöhen
    setInterval(function() {
        let currentValue = parseInt(inputElement.value, 10); // Aktuellen Wert holen
        inputElement.value = currentValue + 1; // Wert erhöhen
    }, 1000); // 1000 ms = 1 Sekunde
}

// Funktion aufrufen, um zu starten
increaseSeconds();
