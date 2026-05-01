document.addEventListener('DOMContentLoaded', function () {

    const toastMsg = document.getElementById('toast-msg');
    if (toastMsg) {
        const toast = document.getElementById('toast');
        const toastText = document.getElementById('toast-text');
        if (toast && toastText) {
            toastText.textContent = toastMsg.textContent.trim();
            setTimeout(() => toast.classList.add('show'), 100);
            setTimeout(() => toast.classList.remove('show'), 3600);
        }
    }

    const modal = document.getElementById('modal-confirmar');
    const modalOverlay = document.getElementById('modal-overlay-confirmar');
    const btnConfirmar = document.getElementById('btn-confirmar-eliminar');
    const btnCancelar = document.getElementById('btn-cancelar-eliminar');
    let formPendiente = null;

    document.querySelectorAll('.form-eliminar').forEach(function (form) {
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            formPendiente = form;
            modalOverlay.classList.add('open');
        });
    });
