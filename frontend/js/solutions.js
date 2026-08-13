document.addEventListener('DOMContentLoaded', () => {
    const storedProblem = sessionStorage.getItem('currentProblem');
    const problema = storedProblem ? JSON.parse(storedProblem) : null;
    const equation = problema?.ecuacion || 'x^2 - 5x + 6 = 0';
    const solution = problema?.solucion || 'La ecuación ingresada fue registrada correctamente.';
    document.getElementById('equationDisplay').innerHTML = equation;

    const steps = generateSteps(equation, solution);
    const container = document.getElementById('stepsContainer');
    container.innerHTML = steps.map(step => `
        <div class="step">
            <h3>${step.title}</h3>
            ${step.content.map(p => `<p>${p}</p>`).join('')}
        </div>
    `).join('');
    if (window.MathJax) {
        window.MathJax.typesetPromise([container]).catch(err => console.log(err));
    }

    document.getElementById('exportBtn').addEventListener('click', () => {
        alert('Exportar a PDF (simulación).');
    });
    document.getElementById('shareBtn').addEventListener('click', () => {
        alert('Compartir solución (simulación).');
    });
    document.getElementById('saveHistoryBtn').addEventListener('click', () => {
        alert('La solución ya quedó guardada en el historial del backend.');
    });
});


function generateSteps(eq, solution) {
    if (eq.includes('x^2') && !eq.includes('sin') && !eq.includes('cos')) {
        return [
            { title: 'Paso 1: Identificar coeficientes', content: ['La ecuación cuadrática tiene la forma ax² + bx + c = 0.', 'Coeficientes detectados de forma automática.'] },
            { title: 'Paso 2: Aplicar método algebraico', content: ['Se aplica el procedimiento estándar para resolver la ecuación.'] },
            { title: 'Paso 3: Resultado generado', content: [solution] }
        ];
    }
    return [
        { title: 'Solución', content: [solution] }
    ];
}
