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

    if (btnConfirmar) {
        btnConfirmar.addEventListener('click', function () {
            if (formPendiente) {
                modalOverlay.classList.remove('open');
                formPendiente.submit();
            }
        });
    }

    if (btnCancelar) {
        btnCancelar.addEventListener('click', function () {
            modalOverlay.classList.remove('open');
            formPendiente = null;
        });
    }

    if (modalOverlay) {
        modalOverlay.addEventListener('click', function (e) {
            if (e.target === modalOverlay) {
                modalOverlay.classList.remove('open');
                formPendiente = null;
            }
        });
    }

    document.querySelectorAll('span.error').forEach(function (span) {
        if (span.textContent.trim() !== '') {
            const input = span.previousElementSibling;
            if (input && (input.tagName === 'INPUT' || input.tagName === 'SELECT' || input.tagName === 'TEXTAREA')) {
                input.style.borderColor = '#C4684A';
                input.style.boxShadow  = '0 0 0 3px rgba(196,104,74,0.15)';
                input.addEventListener('input', function () {
                    input.style.borderColor = '';
                    input.style.boxShadow   = '';
                    span.textContent = '';
                }, { once: true });
            }
        }
    });

    const primerError = document.querySelector('span.error:not(:empty)');
    if (primerError) {
        const formSection = document.querySelector('.form-section-wrap');
        if (formSection) {
            setTimeout(function () {
                formSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }, 200);
        }
    }
});