// Este es la función para buscar por Id
function filtrarTabla() {

    let input = document.getElementById("buscar").value.toLowerCase();
    let tarjetas = document.querySelectorAll(".usuario-card");

    tarjetas.forEach(card => {

        let id = card.getAttribute("data-id").toLowerCase();

        if (id.includes(input)) {
            card.style.display = "";
        } else {
            card.style.display = "none";
        }

    });
}

let deleteUrl = "";

// abrir modal
function openModal(url) {
    deleteUrl = url;
    document.getElementById("confirmModal").classList.remove("hidden");
}

// cerrar modal
function closeModal() {
    document.getElementById("confirmModal").classList.add("hidden");
}


document.addEventListener("DOMContentLoaded", () => {
    const btn = document.getElementById("btnDelete");

    if (btn) {
        btn.addEventListener("click", () => {
            window.location.href = deleteUrl;
        });
    }
});
