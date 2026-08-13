import React from 'react'

// Página que documenta artefactos del ciclo de software: diagrama de clases,
// casos de uso, historias de usuario y prototipos. Aquí incluimos enlaces
// a los documentos entregables (archivos markdown dentro de la carpeta).
export default function About() {
  return (
    <section>
      <h2>Diseño y Artefactos</h2>
      <p>Archivos incluidos en la entrega:</p>
      <ul>
        <li><a href="./../docs/diagrama_clases.md">Diagrama de clases</a></li>
        <li><a href="./../docs/historias_usuario.md">Historias de usuario</a></li>
        <li><a href="./../docs/prototipos.md">Prototipos</a></li>
        <li><a href="./../docs/plan_trabajo.md">Plan de trabajo</a></li>
      </ul>
    </section>
  )
}
