document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const name = document.getElementById('name').value.trim();
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;
            const confirm = document.getElementById('confirm-password').value;

            if (!name || !email || !password || !confirm) {
                showError('email-error', 'Todos los campos son obligatorios');
                return;
            }
            if (!isValidEmail(email)) {
                showError('email-error', 'Correo inválido');
                return;
            }
            if (password.length < 8) {
                showError('email-error', 'La contraseña debe tener al menos 8 caracteres');
                return;
            }
            if (password !== confirm) {
                showError('email-error', 'Las contraseñas no coinciden');
                return;
            }

            try {
                const params = new URLSearchParams({ nombre: name, email, passwordHash: password });
                await apiRequest('/registro', {
                    method: 'POST',
                    body: params.toString()
                });
                alert('Registro exitoso. Ahora puedes iniciar sesión.');
                window.location.href = 'login.html';
            } catch (error) {
                showError('email-error', error.message);
            }
        });
        togglePassword('password', 'togglePassword');
    }

    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;

            try {
                const params = new URLSearchParams({ email, password });
                const user = await apiRequest('/login', {
                    method: 'POST',
                    body: params.toString()
                });

                const normalizedUser = {
                    id: user.id,
                    name: user.nombre || user.name,
                    email: user.email
                };

                sessionStorage.setItem('loggedUser', JSON.stringify(normalizedUser));
                window.location.href = 'dashboard.html';
            } catch (error) {
                alert(error.message);
            }
        });
    }
});
