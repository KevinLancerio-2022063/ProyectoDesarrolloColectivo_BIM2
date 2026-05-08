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