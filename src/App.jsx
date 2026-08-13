import React, { useState } from 'react'
import Home from './pages/Home'
import About from './pages/About'
import Contact from './pages/Contact'

// Componente principal que gestiona la navegación simple del frontend
// Evitamos dependencias extra (react-router) para mantener el ejemplo autocontenido
export default function App() {
  const [route, setRoute] = useState('home')

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', padding: 16 }}>
      <header>
        <h1>Proyecto Formativo — Frontend (Evidencia)</h1>
        <nav>
          <button onClick={() => setRoute('home')}>Inicio</button>{' '}
          <button onClick={() => setRoute('about')}>Diseño</button>{' '}
          <button onClick={() => setRoute('contact')}>Entrega</button>
        </nav>
      </header>

      <main style={{ marginTop: 20 }}>
        {route === 'home' && <Home />}
        {route === 'about' && <About />}
        {route === 'contact' && <Contact />}
      </main>

      <footer style={{ marginTop: 40, fontSize: 12, color: '#666' }}>
        <p>Autor: Estudiante — Evidencia GA7-220501096-AA4-EV03</p>
      </footer>
    </div>
  )
}
