import React from 'react'
import { createRoot } from 'react-dom/client'
import App from './App'

// Punto de entrada de la aplicación React
// Se recomienda mantener comentarios claros y cumplir estándares de codificación.
const root = createRoot(document.getElementById('root'))
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)
