// Funciones de utilidad comunes

const API_BASE_URL = 'http://localhost:8082/api';

async function apiRequest(path, options = {}) {
    const headers = { ...(options.headers || {}) };
    if (options.body && !headers['Content-Type'] && !(options.body instanceof FormData)) {
        const isFormBody = typeof options.body === 'string' && options.body.includes('=');
        headers['Content-Type'] = isFormBody ? 'application/x-www-form-urlencoded' : 'application/json';
    }

    const response = await fetch(`${API_BASE_URL}${path}`, { ...options, headers });
    const text = await response.text();
    let data = null;
    if (text) {
        try {
            data = JSON.parse(text);
        } catch (error) {
            data = text;
        }
    }

    if (!response.ok) {
        const message = data?.error || data?.mensaje || data?.message || 'No se pudo completar la solicitud';
        throw new Error(message);
    }

    return data;
}

// Mostrar/ocultar contraseña
function togglePassword(inputId, buttonId) {
    const input = document.getElementById(inputId);
    const button = document.getElementById(buttonId);
    if (!input || !button) return;
    button.addEventListener('click', () => {
        const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
        input.setAttribute('type', type);
        button.textContent = type === 'password' ? 'Mostrar' : 'Ocultar';
    });
}

// Validar email simple
function isValidEmail(email) {
    return /^[^\s@]+@([^\s@]+\.)+[^\s@]+$/.test(email);
}

// Mostrar mensaje de error temporal
function showError(elementId, message) {
    const errorEl = document.getElementById(elementId);
    if (errorEl) {
        errorEl.textContent = message;
        errorEl.classList.remove('hidden');
        setTimeout(() => errorEl.classList.add('hidden'), 3000);
    }
}

// Guardar en localStorage
function saveToLocalStorage(key, data) {
    localStorage.setItem(key, JSON.stringify(data));
}

// Obtener de localStorage
function getFromLocalStorage(key) {
    const data = localStorage.getItem(key);
    return data ? JSON.parse(data) : null;
}
