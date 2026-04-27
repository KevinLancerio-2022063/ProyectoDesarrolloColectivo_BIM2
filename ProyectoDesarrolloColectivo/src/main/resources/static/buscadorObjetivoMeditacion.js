// Este script es para la función buscar
function filtrarTabla() {
    let input = document.getElementById("buscar").value.toLowerCase();
    let filas = document.querySelectorAll("#tablaObjetivos tbody tr");

    filas.forEach(fila => {
        let texto = fila.querySelector("td").innerText.toLowerCase();
        fila.style.display = texto.includes(input) ? "" : "none";
    });

}