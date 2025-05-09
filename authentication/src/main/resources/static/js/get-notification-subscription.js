// wenn der Status tatsächlich false ist, wird bei laden der seiten der switch kurz eingeschaltet angezeigt.
async function getNotificationStatus() {
    try {
        const response = await fetch('/get-notification-subscription', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            let notificationStatus = await response.json();
            console.log("got notification status: " + notificationStatus);
            setSwitchState(notificationStatus)

        } else {
            console.log('Request failed');
        }
    } catch (error) {
        console.log('Error occurred:', error);
    }
}

function setSwitchState(notificationStatus) {
    if (notificationStatus) {
        document.getElementById('flexSwitchCheckChecked').checked = true;
    } else {
        document.getElementById('flexSwitchCheckChecked').checked = false;
    }
}

getNotificationStatus();