function toggle() {
    let anzeigeBtn = document.getElementById("anzeigen-btn-div");
    let antwort = document.getElementById("kartei-antwort-div");
    let btnGroup = document.getElementById("btn-group-div");

    if (anzeigeBtn) {
        anzeigeBtn.classList.remove("active");
        anzeigeBtn.classList.add("hidden");
    }

    if(antwort && btnGroup) {
        antwort.classList.remove("hidden")
        antwort.classList.add("active")
        btnGroup.classList.remove("hidden")
        btnGroup.classList.add("active")
    }
}
