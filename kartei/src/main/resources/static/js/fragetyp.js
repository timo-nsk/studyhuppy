document.getElementById("fragetyp").addEventListener("change", function () {
    let selectedValue = this.value;
    let karteiSetId = document.getElementById("karteiSetId").value;

    console.log(selectedValue)

    fetch("/set-fragetyp", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ frageTyp: selectedValue, karteiSetId: karteiSetId }),
    }).then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        }
    })
        .catch(error => console.error("Fehler beim Setzen des Fragetypen:", error));
});