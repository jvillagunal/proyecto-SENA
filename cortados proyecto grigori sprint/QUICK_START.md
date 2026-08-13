# INSTRUCCIONES DE EJECUCIÓN RÁPIDA

## Paso 1: Verificar Requisitos

```bash
# Verificar Java 17+
java -version

# Verificar Maven 3.6+
mvn --version

# Verificar MySQL está corriendo
mysql -u root -p
```

## Paso 2: Preparar Base de Datos

```bash
# Conectar a MySQL
mysql -u root -p

# Pegar el contenido de src/main/resources/schema.sql
# O ejecutar:
mysql -u root -p < src/main/resources/schema.sql
```

## Paso 3: Ajustar Configuración (si es necesario)

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA_MYSQL
```

## Paso 4: Descargar Dependencias

```bash
cd GrigoriPerelmanApp-Spring
mvn clean install
```

## Paso 5: Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O en tu IDE favorito:
- Click derecho en `GrigoriApplication.java`
- Run → Run as Java Application

## Paso 6: Acceder a la Aplicación

###  Módulo Web
```
http://localhost:8080/login
```

**Usuarios de prueba:**
- Email: `juan@example.com`
- Contraseña: `password123`

### API REST
```
http://localhost:8080/api/health
```

### Cliente Standalone
```bash
# En otra terminal
javac -cp ".:target/*" src/main/java/com/grigori/client/StandaloneClient.java
java -cp ".:target/*" com.grigori.client.StandaloneClient
```

---

## Lo que se ha Logrado

✓ **Framework:** Spring Boot 3.2.5 seleccionado y aplicado (30%)
✓ **Persistencia:** JPA/Hibernate + MySQL 8.0 integrados (30%)
✓ **Estándares:** Código comentado con Javadoc, PascalCase/camelCase (30%)
✓ **Módulos:** Web (Thymeleaf), API REST (móvil), Standalone (consola) (40%)
✓ **Control de Versiones:** Git inicializado y primer commit realizado ()

---

##  Estructura de Directorios Completa

```
GrigoriPerelmanApp-Spring/
├── .git/                                # Control de versiones
├── .gitignore
├── pom.xml                              # Dependencias Maven
├── README.md                            # Documentación completa
├── QUICK_START.md                       # Este archivo
├── src/
│   ├── main/
│   │   ├── java/com/grigori/app/
│   │   │   ├── GrigoriApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── ApiController.java
│   │   │   │   └── DashboardController.java
│   │   │   ├── model/
│   │   │   │   ├── Usuario.java
│   │   │   │   └── Problema.java
│   │   │   ├── repository/
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   └── ProblemaRepository.java
│   │   │   ├── service/
│   │   │   │   ├── UsuarioService.java
│   │   │   │   └── ProblemaService.java
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.java
│   │   │   │   └── ProblemaRequest.java
│   │   │   └── client/
│   │   │       └── StandaloneClient.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       ├── static/css/
│   │       │   └── styles.css
│   │       └── templates/
│   │           ├── login.html
│   │           ├── registro.html
│   │           ├── dashboard.html
│   │           ├── nuevo-problema.html
│   │           ├── historial.html
│   │           └── solucion.html
│   └── test/java/...                   # Tests (agregar si es necesario)
└── target/                              # Artefactos compilados (generado)
```

---

##  Endpoints Principales

### Autenticación y Usuarios
```
POST   /api/login              → Iniciar sesión
POST   /api/registro           → Registrar nuevo usuario
GET    /login                  → Formulario login
GET    /registro               → Formulario registro
GET    /logout                 → Cerrar sesión
```

### Problemas
```
GET    /api/usuarios/{id}/problemas    → Listar mis problemas
POST   /api/problemas                  → Crear problema
GET    /api/problemas/{id}             → Obtener problema
PUT    /api/problemas/{id}             → Actualizar problema
DELETE /api/problemas/{id}             → Eliminar problema
```

### Vistas Web
```
GET    /dashboard              → Panel principal
GET    /nuevo-problema         → Formulario crear problema
GET    /historial              → Ver todos los problemas
GET    /problema/{id}          → Ver solución detallada
```

### Health Check
```
GET    /api/health             → Estado de la API
```

---

## Problemas Comunes

| Problema | Solución |
|----------|----------|
| "Cannot connect to MySQL" | Verificar que MySQL está corriendo: `mysql -u root -p` |
| "Port 8080 already in use" | Cambiar puerto en `application.properties`: `server.port=8081` |
| "Maven not found" | Descargar Maven de https://maven.apache.org/download.cgi |
| "Java version not compatible" | Instalar Java 17+: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html |
| "Tables not created" | Ejecutar manualmente: `mysql -u root -p < src/main/resources/schema.sql` |

---

## Checklist de Entrega

- [ ] Proyecto compilado sin errores: `mvn clean install`
- [ ] Base de datos creada y poblada
- [ ] Aplicación ejecutándose en `http://localhost:8080/login`
- [ ] Puedo loguearme con usuario: `juan@example.com / password123`
- [ ] API REST responde en `/api/health`
- [ ] Cliente Standalone funciona
- [ ] Git inicializado con commits
- [ ] README.md completo y actualizado
- [ ] Proyecto comprimido en ZIP

---

## Comprimir Proyecto para Entrega

```bash
# Desde el directorio padre del proyecto
zip -r GrigoriPerelmanApp-Spring.zip GrigoriPerelmanApp-Spring/ \
  -x "GrigoriPerelmanApp-Spring/target/*" \
  "GrigoriPerelmanApp-Spring/.idea/*" \
  "GrigoriPerelmanApp-Spring/.git/*"
```

---

## Información del Proyecto

**Institución:** SENA
**Programa:** Análisis y Desarrollo de Software
**Proyecto:** Construcción de software integrador de tecnologías orientadas a servicios
**Evidencia:** GA7-220501096-AA3-EV01
**Versión:** 1.0.0
**Estado:**  Completado

---

**Éxito en tu evaluación! **
