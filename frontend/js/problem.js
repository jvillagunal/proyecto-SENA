document.addEventListener('DOMContentLoaded', () => {
    const latexInput = document.getElementById('latex');
    const preview = document.getElementById('preview');

    function updatePreview() {
        const eq = latexInput.value.trim();
        if (eq) {
            preview.innerHTML = `Vista previa: \\(${eq}\\)`;
            if (window.MathJax) {
                window.MathJax.typesetPromise([preview]).catch(err => console.log(err));
            }
        } else {
            preview.innerHTML = 'Vista previa: ';
        }
    }
    latexInput.addEventListener('input', updatePreview);

    document.getElementById('ocrBtn').addEventListener('click', () => {
        alert('Funcionalidad de OCR: se abriría la cámara del dispositivo.');
        latexInput.value = 'x^2 - 5x + 6 = 0';
        updatePreview();
    });
    document.getElementById('uploadBtn').addEventListener('click', () => {
        alert('Carga de archivo: se abriría el selector de archivos.');
    });

    document.getElementById('problemForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const equation = latexInput.value.trim();
        if (!equation) {
            alert('Por favor ingresa una ecuación.');
            return;
        }

        const loggedUser = JSON.parse(sessionStorage.getItem('loggedUser') || 'null');
        if (!loggedUser) {
            window.location.href = 'login.html';
            return;
        }

        try {
            const problema = await apiRequest('/problemas', {
                method: 'POST',
                body: JSON.stringify({ usuarioId: loggedUser.id, ecuacion: equation, solucion: '' })
            });
            sessionStorage.setItem('currentProblem', JSON.stringify(problema));
            window.location.href = 'solution.html';
        } catch (error) {
            alert(error.message);
        }
    });
});
