import React from 'react'

// Página de entrega: contiene instrucciones para empaquetar y el checklist de evaluación
export default function Contact() {
  return (
    <section>
      <h2>Entrega</h2>
      <p>Instrucciones para la entrega de la evidencia:</p>
      <ol>
        <li>Comprueba los archivos del proyecto.</li>
        <li>Comprime la carpeta como: NOMBRE_APELLIDO_AA4_EV03.zip</li>
        <li>Incluye el enlace al repositorio (si aplica).</li>
      </ol>

      <h3>Lista de chequeo</h3>
      <pre style={{ background: '#f7f7f7', padding: 12 }}>
{`1. Diseño cumple prototipos: 30%
2. Entrega archivos y enlace: 30%
3. Navegación funciona: 30%
4. Requisitos iniciales: 10%`}
      </pre>
    </section>
  )
}
