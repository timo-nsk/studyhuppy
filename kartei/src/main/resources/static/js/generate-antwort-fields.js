document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("anzahl-aw").addEventListener("change", function () {
        const anzahl = parseInt(this.value, 10);
        const antwortenContainer = document.getElementById("antworten-sc");

        console.log("set anzahl fragen")

        antwortenContainer.innerHTML = "";

        for (let i = 1; i <= anzahl; i++) {
            const label = document.createElement("label");
            label.textContent = `Antwort ${i}`;

            const radio = document.createElement("input");
            radio.type = "radio";
            radio.name = "wahrheitIndex";
            radio.value = i-1;

            const textInput = document.createElement("input");
            textInput.type = "text";
            textInput.placeholder = "Antwort...";
            textInput.name = "antworten[" + (i-1) +"].antwort";

            antwortenContainer.appendChild(label);
            antwortenContainer.appendChild(radio);
            antwortenContainer.appendChild(textInput);
            antwortenContainer.appendChild(document.createElement("br"));
        }
    });
});
