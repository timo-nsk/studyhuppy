export async function getSeconds(fachId) {
    try {
        const response = await fetch('/api/get-seconds', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': getCsrfToken()
            },
            body: JSON.stringify({ fachId: fachId}) // Send fachId in a JSON object
        });

        //if (!response.ok) {
        //    throw new Error(`HTTP error! Status: ${response.status}`);
        //}
        return await response.text();

    } catch (error) {
        //console.error('Fehler beim Abrufen der Sekunden:', error);
    }
}

export async function postStartInterval(fachId) {
    const payload = {
        fachId: fachId
    };

    try {
        const response = await doPost(payload, '/api/set-start');

        if (response.ok) {
            //console.log("SartInterval erfolgreich gesendet!");
            //const responseData = await response.text();
            //console.log("Antwort der API:", responseData);
        } else {
            //console.error("Fehler beim Senden der Daten:", response.statusText);
        }
    } catch (error) {
        //console.error("Fehler beim Abrufen der API:", error);
    }
}

export async function postFinishInterval(fachId) {
    const payload = {
        fachId: fachId
    };

    try {
        const response = await doPost(payload, '/api/set-finish');

        if (response.ok) {
            //console.log("FinishInterval erfolgreich gesendet!");
            //const responseData = await response.text();
            //console.log("Antwort der API:", responseData);
        } else {
            //console.error("Fehler beim Senden der Daten:", response.statusText);
        }
    } catch (error) {
        //console.error("Fehler beim Abrufen der API:", error);
    }
}

export async function postNewSeconds(fachId) {
    let seconds = document.getElementById(fachId).dataset.value;

    const payload = {
        fachId: fachId,
        secondsLearned: seconds
    };

    try {
        const response = await doPost(payload, '/api/update');

        if (response.ok) {
            //console.log("Daten erfolgreich gesendet!");
            //const responseData = await response.text();
            //console.log("Antwort der API:", responseData);
        } else {
            //console.error("Fehler beim Senden der Daten:", response.statusText);
        }
    } catch (error) {
        //console.error("Fehler beim Abrufen der API:", error);
    }
}

export async function fetchStatisticEndpoint(endpoint) {
    const response = await fetch(endpoint);
    const data = response.json();
    return data;
}

async function doPost(payload, api) {
    return  await fetch(api, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCsrfToken()
        },
        body: JSON.stringify(payload)
    });
}

function getCsrfToken() {
    return document.cookie
        .split('; ')
        .find(row => row.startsWith('XSRF-TOKEN='))
        ?.split('=')[1];
}