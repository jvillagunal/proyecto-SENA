# Evidencia GA7-220501096-AA5-EV01

Servicio web para registro e inicio de sesión (API REST) — caso de evaluación.

Descripción:
- Endpoints:
  - `POST /api/registro` — registra un usuario (body: `username`, `email`, `password`).
  - `POST /api/login` — autentica un usuario (body: `email`, `password`).

Requisitos:
- Node.js 18+ (o compatible)

Instrucciones para ejecutar:

1. Abrir terminal en esta carpeta:

```powershell
cd "carpeta de evidencias/GA7-220501096-AA5-EV01"
npm install
npm start
```

2. La API escuchará por defecto en `http://localhost:8082/api`.

Notas:
- El proyecto almacena usuarios en `data/users.json` (archivo JSON local) para simplicidad.
- El código contiene comentarios explicativos según el requerimiento.
