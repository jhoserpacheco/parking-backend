# Sistema de Gestión de Parqueaderos — Parking Backend

El Sistema de Gestión de Parqueaderos es una solución backend diseñada para la gestión eficiente de
vehículos en parqueaderos. Este proyecto está construido con Spring Boot y sigue una arquitectura de microservicios,
lo que garantiza base de datos por microservicio. Consta de tres microservicios principales:
Gestión de Usuarios, Gestión de Parqueaderos y Notificaciones por Correo, que se comunican entre sí utilizando Feign
Client y/o RabbitMQ.
El sistema utiliza PostgreSQL como base de datos, y toda la infraestructura está contenedorizada
utilizando Docker Compose para facilitar la implementación y el escalado.

---

## Características

### Roles de Usuario

El sistema soporta dos roles con permisos específicos:

* **Administrador:**
  - Puede crear, gestionar y asignar parqueaderos a los socios.
  - Gestiona cuentas de usuarios y asignación de roles de socios.
  - Puede ver el detalle de los vehículos en parqueaderos específicos.
  - Accede a indicadores para la gestión de parqueaderos.
  - Puede enviar notificaciones por correo a los socios.

* **Socio:**
  - Puede registrar la entrada y salida de vehículos en sus parqueaderos asignados.
  - Puede ver el listado de vehículos en sus parqueaderos.
  - Accede a indicadores y estadísticas de ingresos de sus parqueaderos.

---

### Tecnologías Utilizadas

- **Java 17:** Lenguaje de programación del backend.
- **Spring Boot 3.3.3:** Framework utilizado para construir la aplicación.
- **Spring Security + JWT:** Para la gestión de autenticación y autorización.
- **Feign Client:** Comunicación HTTP entre microservicios.
- **RabbitMQ:** Broker de mensajes para comunicación desacoplada entre servicios.
- **PostgreSQL 15:** Sistema de base de datos para almacenamiento persistente.
- **MongoDB 6.0:** Almacenamiento de logs de notificaciones por correo.
- **Docker & Docker Compose:** Para la contenedorización y orquestación de los servicios.

---

## Requisitos Previos

- **Docker Desktop** v20.10 o superior (con Docker Compose v2).
- **Git:** Para clonar el repositorio.
- **Postman** *(opcional, recomendado)*: Para probar los endpoints con la colección incluida.

> **Nota:** Gracias a los Dockerfiles Multi-Stage, **no es necesario tener Java ni Gradle instalados** en tu máquina. Docker compila los microservicios internamente.

---

## Instalación y Ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/jhoserpacheco/parking-backend
cd parking-backend
```

### 2. Levantar todos los servicios

```bash
docker-compose up -d --build
```

Docker Compose se encarga automáticamente de:
1. Compilar los microservicios con Gradle y JDK 17 (Multi-Stage).
2. Levantar PostgreSQL 15 y sembrar la data de prueba inicial.
3. Levantar MongoDB 6.0.
4. Levantar RabbitMQ con sus exchanges, colas y bindings precargados.
5. Iniciar Eureka Server, API Gateway y los 3 microservicios de negocio.

### 3. Verificar el estado de los contenedores

```bash
docker-compose ps
```

Deberás ver los siguientes 8 contenedores en estado `Up`:

| Contenedor | Puerto |
|---|---|
| `parking_db` (PostgreSQL) | `localhost:5432` |
| `mongo_db` (MongoDB) | `localhost:27017` |
| `rabbitmq` | `localhost:5672` |
| `discover-service` (Eureka) | `http://localhost:8761` |
| `api-gateway` | `http://localhost:8079` |
| `ms-user` | `http://localhost:8080` |
| `ms-parking` | `http://localhost:8081` |
| `ms-mail` | `http://localhost:8082` |

---

## Consolas y Documentación

Una vez levantado el sistema, accede desde el navegador a:

| Herramienta | URL | Credenciales |
|---|---|---|
| **Eureka Service Discovery** | http://localhost:8761 | No requiere autenticación |
| **RabbitMQ Management** | http://localhost:15672 | `guest` / `guest` |
| **Swagger — User MS** | http://localhost:8080/api/user-ms/swagger-ui.html | Bearer JWT Token |
| **Swagger — Parking MS** | http://localhost:8081/api/parking-ms/swagger-ui.html | Bearer JWT Token |
| **Swagger — Mail MS** | http://localhost:8082/api/mail-ms/swagger-ui.html | Bearer JWT Token |

---

## Usuarios de Prueba

El script de base de datos inicializa automáticamente los siguientes usuarios:

| Rol | Correo | Contraseña |
|---|---|---|
| **ADMIN** | `admin@mail.com` | `admin` |
| **SOCIO 1** | `socio1@gmail.com` | `socio1` |
| **SOCIO 2** | `socio2@gmail.com` | `socio2` |

---

## Pruebas con Postman

Importa el archivo **`Parking Backend.postman_collection.json`** ubicado en la raíz del proyecto.

Secuencia de prueba recomendada:

**1. Login**
```
POST http://localhost:8079/api/user-ms/auth/login
Body: { "email": "socio1@gmail.com", "password": "socio1" }
```

**2. Registrar entrada de vehículo**
```
POST http://localhost:8079/api/parking-ms/parking/history/register-entry/{parkingId}
Header: Authorization: Bearer <TOKEN>
Body: { "vehiclePlate": "ABC123", "model": "Mazda 3" }
```

**3. Consultar vehículos activos**
```
GET http://localhost:8079/api/parking-ms/parking/{parkingId}/detail
Header: Authorization: Bearer <TOKEN>
```

**4. Registrar salida y liquidar cobro**
```
POST http://localhost:8079/api/parking-ms/parking/history/register-exit/{parkingId}
Header: Authorization: Bearer <TOKEN>
Body: { "vehiclePlate": "ABC123" }
```

---

## Comandos Útiles

```bash
# Ver logs de un microservicio específico
docker-compose logs -f ms-parking

# Detener todos los contenedores
docker-compose down

# Detener y eliminar volúmenes (reinicia las bases de datos)
docker-compose down -v

# Reconstruir tras cambios en el código
docker-compose up -d --build
```

---

## Infraestructura

<div style="align-items: center">
    <img src="db-init/Infraestructure%20Parking%20Backend.png" alt="Infraestructura Parking Backend" />
</div>

---

## README por Microservicio

- [MS-USUARIOS](user-ms)
- [MS-PARQUEADERO](parking-ms)
- [MS-EMAIL](mail-ms)
