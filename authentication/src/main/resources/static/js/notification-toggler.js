function sendEditNotificationRequest() {
    // Überprüfen, ob der Schalter aktiviert oder deaktiviert ist
    const switchState = document.getElementById('flexSwitchCheckChecked').checked;
    console.log(switchState)

    // POST-Anfrage senden

    fetch('/edit-notification', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ activate: switchState }),
        //credentials: 'same-origin'  // Fügt Cookies aus der gleichen Herkunft hinzu
    })
        .then(response => {
            if (response.ok) {
                console.log('Request was successful');
            } else {
                console.log('Request failed');
            }
        })
        .catch(error => {
            console.log('Error occurred:', error);
        });

    // Das Label basierend auf dem Zustand des Schalters ändern
    const label = document.getElementById('switchLabel');
    if (switchState) {
        label.textContent = 'Aktiviert';
    } else {
        label.textContent = 'Deaktiviert';
    }
}