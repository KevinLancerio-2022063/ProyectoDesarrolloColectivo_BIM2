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

// Script del cursor del mouse
const dot = document.querySelector(".cursor-dot");
    const ring = document.querySelector(".cursor-ring");

    window.addEventListener("mousemove", function(e) {
        dot.style.left = e.clientX + "px";
        dot.style.top = e.clientY + "px";

        ring.animate({
            left: e.clientX + "px",
            top: e.clientY + "px"
        }, {
            duration: 420,
            fill: "forwards"
        });

        const trail = document.createElement("span");
        trail.className = "cursor-trail";
        trail.style.left = e.clientX + "px";
        trail.style.top = e.clientY + "px";
        document.body.appendChild(trail);

        setTimeout(() => trail.remove(), 600);
    });
    document.querySelectorAll('a, button, .btn-icon, .usuario-card').forEach(el => {
        el.addEventListener('mouseenter', () => { ring.style.width='48px'; ring.style.height='48px'; ring.style.borderColor='var(--cafe-claro)'; });
        el.addEventListener('mouseleave', () => { ring.style.width='32px'; ring.style.height='32px'; ring.style.borderColor='var(--verde-menta)'; });
    });