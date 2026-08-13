document.addEventListener('DOMContentLoaded', () => {
    const user = JSON.parse(sessionStorage.getItem('loggedUser'));
    if (!user) {
        window.location.href = 'login.html';
        return;
    }
    document.getElementById('profileName').textContent = user.name;
    document.getElementById('profileEmail').textContent = user.email;

    document.getElementById('changePassword').addEventListener('click', (e) => {
        e.preventDefault();
        alert('Funcionalidad de cambio de contraseña (simulación).');
    });
    document.getElementById('helpLink').addEventListener('click', (e) => {
        e.preventDefault();
        alert('Ayuda y tutoriales (simulación).');
    });
    document.getElementById('logoutBtn').addEventListener('click', () => {
        sessionStorage.removeItem('loggedUser');
        window.location.href = 'index.html';
    });
    document.getElementById('deleteAccountBtn').addEventListener('click', () => {
        if (confirm('¿Estás seguro de que quieres eliminar tu cuenta? Esta acción es irreversible.')) {
            const users = getFromLocalStorage('users') || [];
            const newUsers = users.filter(u => u.email !== user.email);
            saveToLocalStorage('users', newUsers);
            sessionStorage.removeItem('loggedUser');
            alert('Cuenta eliminada.');
            window.location.href = 'index.html';
        }
    });
});
