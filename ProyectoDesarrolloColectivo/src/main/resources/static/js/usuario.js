// Este script es para la función buscar
function filtrarTabla() {

    // Obtener lo que escribe el usuario
    let input = document.getElementById("buscar").value.toLowerCase();

    // Obtener todas las tarjetas
    let tarjetas = document.querySelectorAll(".usuario-card");

    // Recorrer cada tarjeta
    tarjetas.forEach(card => {

        // Obtener el ID de la tarjeta
        let id = card.getAttribute("data-id").toLowerCase();

        // Comparar
        if (id.includes(input)) {
            card.style.display = "";   // Se muestra
        } else {
            card.style.display = "none";   // Se oculta
        }

    });

}

let deleteUrl = "";

    // Abrir Modal
    function openModal(url) {
        deleteUrl = url;
        document.getElementById("confirmModal").classList.remove("hidden");
    }

    // Cerrar Modal
    function closeModal() {
        document.getElementById("confirmModal").classList.add("hidden");
    }

    document.getElementById("btnDelete").addEventListener("click", () => {
        window.location.href = deleteUrl;
    });

    // Errores Visuales
    function mostrarErrores(errores) {
        const container = document.getElementById("errorContainer");
        container.innerHTML = "";

        errores.forEach(err => {
            const card = document.createElement("div");
            card.className = "card error-card";

            card.innerHTML = `
                <div class="header">
                    <div class="image">❌</div>
                    <div class="content">
                        <span class="title">Error</span>
                        <p class="message">${err}</p>
                    </div>
                </div>
            `;

            container.appendChild(card);
        });

        setTimeout(() => container.innerHTML = "", 5000);
    }