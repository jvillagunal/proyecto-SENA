// Servicio REST simple para registro e inicio de sesión
// Usa un archivo JSON local (`data/users.json`) como almacenamiento simple

const express = require('express');
const fs = require('fs');
const path = require('path');
const bcrypt = require('bcryptjs');
const helmet = require('helmet');
const morgan = require('morgan');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 8082;
const DATA_DIR = path.join(__dirname, 'data');
const USERS_FILE = path.join(DATA_DIR, 'users.json');

// Middlewares
app.use(helmet());
app.use(express.json());
app.use(morgan('dev'));
// Habilitar CORS para permitir peticiones desde el cliente de prueba
app.use(cors());

// Asegura que el directorio de datos exista
if (!fs.existsSync(DATA_DIR)) {
  fs.mkdirSync(DATA_DIR, { recursive: true });
}

// Inicializa archivo de usuarios si no existe
if (!fs.existsSync(USERS_FILE)) {
  fs.writeFileSync(USERS_FILE, JSON.stringify([]));
}

// Helper: lee usuarios desde archivo
function readUsers() {
  const raw = fs.readFileSync(USERS_FILE, 'utf8');
  try {
    return JSON.parse(raw);
  } catch (e) {
    return [];
  }
}

// Helper: escribe usuarios al archivo
function writeUsers(users) {
  fs.writeFileSync(USERS_FILE, JSON.stringify(users, null, 2));
}

// Validaciones sencillas
function isValidEmail(email) {
  return typeof email === 'string' && /\S+@\S+\.\S+/.test(email);
}

// Ruta POST /api/registro
// Recibe: { username, email, password }
// Crea el usuario (si no existe) y devuelve mensaje de éxito.
app.post('/api/registro', (req, res) => {
  const { username, email, password } = req.body || {};

  // Validaciones básicas
  if (!username || !email || !password) {
    return res.status(400).json({ error: 'Todos los campos son requeridos.' });
  }
  if (!isValidEmail(email)) {
    return res.status(400).json({ error: 'Email inválido.' });
  }
  if (password.length < 6) {
    return res.status(400).json({ error: 'La contraseña debe tener al menos 6 caracteres.' });
  }

  const users = readUsers();

  // Comprobar usuario existente por email
  const exists = users.find(u => u.email.toLowerCase() === email.toLowerCase());
  if (exists) {
    return res.status(409).json({ error: 'El usuario ya existe.' });
  }

  // Hash de la contraseña
  const salt = bcrypt.genSaltSync(10);
  const hashed = bcrypt.hashSync(password, salt);

  const newUser = {
    id: Date.now(),
    username,
    email,
    password: hashed,
    createdAt: new Date().toISOString()
  };

  users.push(newUser);
  writeUsers(users);

  return res.status(201).json({ message: 'Registro exitoso.' });
});

// Ruta POST /api/login
// Recibe: { email, password }
// Si la autenticación es correcta devuelve mensaje de autenticación satisfactoria.
app.post('/api/login', (req, res) => {
  const { email, password } = req.body || {};

  if (!email || !password) {
    return res.status(400).json({ error: 'Email y contraseña son requeridos.' });
  }

  const users = readUsers();
  const user = users.find(u => u.email.toLowerCase() === email.toLowerCase());

  if (!user) {
    // No revelar si el email no existe — respuesta genérica de error
    return res.status(401).json({ error: 'Error en la autenticación.' });
  }

  const match = bcrypt.compareSync(password, user.password);
  if (!match) {
    return res.status(401).json({ error: 'Error en la autenticación.' });
  }

  // Autenticación satisfactoria
  return res.json({ message: 'Autenticación satisfactoria', user: { id: user.id, username: user.username, email: user.email } });
});

// Ruta raíz para verificar servicio
app.get('/', (req, res) => {
  res.send({ ok: true, msg: 'API de evidencia GA7-AA5-EV01 en /api' });
});

app.listen(PORT, () => {
  console.log(`API escuchando en http://localhost:${PORT}/api`);
});
