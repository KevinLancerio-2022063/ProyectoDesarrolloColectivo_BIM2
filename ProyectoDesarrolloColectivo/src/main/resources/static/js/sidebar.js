const sidebar = document.getElementById('sidebar');
    const overlay = document.getElementById('sidebarOverlay');
    const closeBtn = document.getElementById('sidebarClose');

    let isOpen = false;

    /* ===== RESTAURAR ESTADO ===== */
    if (localStorage.getItem('sidebarState') === 'open') {
        openSidebar(false);
    }

    /* ===== FUNCIONES ===== */
    function openSidebar(save = true) {
        isOpen = true;

        sidebar.classList.add('open');
        overlay.classList.add('visible');

        document.body.style.overflow = 'hidden';

        if (save) {
            localStorage.setItem('sidebarState', 'open');
        }
    }

    function closeSidebar(save = true) {
        isOpen = false;

        sidebar.classList.remove('open');
        overlay.classList.remove('visible');

        document.body.style.overflow = '';

        if (save) {
            localStorage.setItem('sidebarState', 'closed');
        }
    }

    function toggleSidebar(e) {
        e.preventDefault();

        isOpen ? closeSidebar() : openSidebar();
    }

    /* ===== EVENTOS ===== */
    closeBtn.addEventListener('click', () => closeSidebar());

    overlay.addEventListener('click', () => closeSidebar());

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && isOpen) {
            closeSidebar();
        }
    });