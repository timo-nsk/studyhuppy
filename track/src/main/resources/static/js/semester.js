let modulCount = 0; // Zähler für eindeutige IDs

function addModulFormFields() {
    modulCount++; // Erhöhe den Zähler für jedes neue Modul

    // Container im ersten Form suchen
    const container = document.getElementById("module-container");

    // Neues div-Element erstellen
    const modulDiv = document.createElement("div");
    modulDiv.classList.add("modul-container");

    // Label für Modulname
    const label1 = document.createElement("label");
    label1.textContent = "Modulname:";
    label1.className = "form-label";
    label1.setAttribute("for", `modulName-${modulCount}`);

    // Input für Modulname
    const input1 = document.createElement("input");
    input1.type = "text";
    input1.name = `module[${modulCount}].modulName`;
    input1.id = `modulName-${modulCount}`;
    input1.className = "form-control d-inline-flex focus-ring py-1 px-2 text-decoration-none border rounded-2";

    // Label für Leistungspunkte
    const label2 = document.createElement("label");
    label2.textContent = "Leistungspunkte:";
    label2.className = "form-label";
    label2.setAttribute("for", `creditPoints-${modulCount}`);

    // Input für Leistungspunkte
    const input2 = document.createElement("input");
    input2.type = "number";
    input2.name = `module[${modulCount}].creditPoints`;
    input2.id = `creditPoints-${modulCount}`;
    input2.className = "form-control d-inline-flex focus-ring py-1 px-2 text-decoration-none border rounded-2";

    // Alle Elemente zum Modul-Container hinzufügen
    modulDiv.appendChild(label1);
    modulDiv.appendChild(input1);
    modulDiv.appendChild(label2);
    modulDiv.appendChild(input2);

    // Neues div ins erste Form einfügen
    container.appendChild(modulDiv);
}