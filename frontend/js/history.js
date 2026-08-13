document.addEventListener('DOMContentLoaded', async () => {
    const container = document.getElementById('historyList');
    const searchInput = document.getElementById('searchInput');
    const noResults = document.getElementById('noResults');
    const user = JSON.parse(sessionStorage.getItem('loggedUser') || 'null');

    if (!user) {
        window.location.href = 'login.html';
        return;
    }

    let history = [];

    async function loadHistory() {
        try {
            history = await apiRequest(`/usuarios/${user.id}/problemas`);
            renderHistory();
        } catch (error) {
            container.innerHTML = '<p class="text-red-500">No se pudo cargar el historial.</p>';
        }
    }

    function renderHistory(filter = '') {
        const filtered = history.filter(item => (item.ecuacion || '').toLowerCase().includes(filter.toLowerCase()));
        if (filtered.length === 0) {
            container.innerHTML = '';
            noResults.classList.remove('hidden');
            return;
        }
        noResults.classList.add('hidden');
        container.innerHTML = filtered.map(item => `
            <div class="history-item" data-id="${item.id}">
                <div class="history-equation">${item.ecuacion}</div>
                <div class="history-date">${item.fechaCreacion || 'Reciente'}</div>
                <div class="history-actions">
                    <button class="view" data-id="${item.id}">Ver solución</button>
                    <button class="delete" data-id="${item.id}">Eliminar</button>
                </div>
            </div>
        `).join('');

        document.querySelectorAll('.view').forEach(btn => {
            btn.addEventListener('click', () => {
                const id = parseInt(btn.dataset.id);
                const item = history.find(i => i.id === id);
                if (item) {
                    sessionStorage.setItem('currentProblem', JSON.stringify(item));
                    window.location.href = 'solution.html';
                }
            });
        });
        document.querySelectorAll('.delete').forEach(btn => {
            btn.addEventListener('click', async () => {
                const id = parseInt(btn.dataset.id);
                try {
                    await apiRequest(`/problemas/${id}`, { method: 'DELETE' });
                    await loadHistory();
                } catch (error) {
                    alert(error.message);
                }
            });
        });
    }

    searchInput.addEventListener('input', (e) => renderHistory(e.target.value));
    loadHistory();
});
