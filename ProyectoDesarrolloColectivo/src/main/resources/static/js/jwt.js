(function () {
    const token     = sessionStorage.getItem('jwt_token');
    const enLogin   = window.location.pathname.startsWith('/login');
    const enRegister= window.location.pathname.startsWith('/register');

    // Si no hay token y no estamos en login/register, redirigir al login
    if (!token && !enLogin && !enRegister) {
        window.location.href = '/login';
        return;
    }

    // Esto permite que el JwtAuthFilter lo lea en cada petición
    document.addEventListener('DOMContentLoaded', () => {
        document.querySelectorAll('form').forEach(form => {
            // Evitar duplicados
            if (form.querySelector('input[name="_jwt"]')) return;

            const input = document.createElement('input');
            input.type  = 'hidden';
            input.name  = '_jwt';
            input.value = token;
            form.appendChild(input);
        });
    });

    // Interceptar todos los links (GET) para agregar el token como parámetro
    document.addEventListener('DOMContentLoaded', () => {
        document.querySelectorAll('a[href]').forEach(link => {
            const href = link.getAttribute('href');

            // Solo interceptar links internos
            if (!href || href.startsWith('http') ||
                href.startsWith('#')  ||
                href.startsWith('mailto')) return;

            link.addEventListener('click', e => {
                e.preventDefault();
                const token = sessionStorage.getItem('jwt_token');
                if (!token) {
                    window.location.href = '/login';
                    return;
                }

                // Hacer fetch con el token en el header
                fetch(href, {
                    headers: { 'Authorization': 'Bearer ' + token }
                }).then(response => {
                    if (response.redirected) {
                        window.location.href = response.url;
                    } else if (response.ok) {
                        window.location.href = href;
                    } else {
                        window.location.href = '/login';
                    }
                }).catch(() => {
                    window.location.href = '/login';
                });
            });
        });
    });

})();

// Función global para cerrar sesión desde cualquier vista
function cerrarSesion() {
    sessionStorage.removeItem('jwt_token');
    window.location.href = '/login?logout=true';
}