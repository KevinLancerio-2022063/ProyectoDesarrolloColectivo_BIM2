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