# Descripción General

El Sistema de Gestión de Parqueaderos es una solución backend diseñada para la gestión eficiente de 
vehículos en parqueaderos. Este proyecto está construido con Spring Boot y sigue parcialmente arquitectura de microservicios,
lo que garantiza con base de datos por microservicio. Consta de tres microservicios principales:
Gestión de Usuarios, Gestión de Parqueaderos y Notificaciones por Correo, que se comunican entre sí utilizando Feign 
Client y/o RabbitMQ. 
El sistema utiliza PostgreSQL como base de datos, y toda la infraestructura está contenedorizada 
utilizando Docker Compose para facilitar la implementación y el escalado.

## Características
### Roles de Usuario

El sistema soporta dos roles con permisos específicos:
* Administrador:
  - Puede crear, gestionar y asignar parqueaderos a los socios.
  - Gestiona cuentas de usuarios y asignación de roles de socios.
  - Puede ver el detalle de los vehículos en parqueaderos específicos.
  - Accede a indicadores para la gestión de parqueaderos.
  - Puede enviar notificaciones por correo a los socios.

Se crea usuario admin:
```bash
email: admin@mail.com
pass: admin 
```

* Socio:
  - Puede registrar la entrada y salida de vehículos en sus parqueaderos asignados.
  - Puede ver el listado de vehículos en sus parqueaderos.
  - Accede a indicadores y estadísticas de ingresos de sus parqueaderos.
  
Se crean dos usuarios socio:
```bash
email: socio1@gmail.com
pass: socio1 
---
email: socio2@gmail.com
pass: socio2 
```

### Tecnologías Utilizadas
  - Java 17: Lenguaje de programación del backend.
  - Spring Boot: Framework utilizado para construir la aplicación.
  - Spring Security: Para la gestión de autenticación y autorización.
  - Feign Client: Comunicación HTTP entre microservicios.
  - RabbitMQ: Broker de mensajes para comunicación desacoplada entre servicios.
  - PostgreSQL: Sistema de base de datos para almacenamiento persistente.
  - Docker & Docker Compose: Para la contenedorización y orquestación de los servicios.

### Requisitos del Sistema
  - Docker: Asegúrate de tener Docker instalado para ejecutar el sistema en contenedores.
  - PostgreSQL: La base de datos está contenedorizada, no se requiere configuración separada.
  - RabbitMQ: Gestionado a través de Docker, utilizado para la notificación por correo y la comunicación entre servicios.


### Instalación
Clonar el repositorio 

```bash
git clone https://github.com/jhoserpacheco/parking-backend
```
Generar el .jar de cada microservicio
[explicado aquí](user-ms/README.md#generate-jar)

Para levantar el backend ejecutamos:
```bash
cd parking-backend
docker-compose up -d --build
```

El docker-compose se encarga de: 
- Arrancar todos los microservicios (ms-user, ms-parking, ms-email) en Spring Boot.
- Crear las base de datos Postqreslq del respectivo microservicio junto con data de prueba.
- Crear el broker de mensajería de RabbitMQ con sus respectivos queue, exchange y routing key. 

README de cada microservicio:
- [ms-user](user-ms)
- [ms-parking](parking-ms)
- [ms-email](mail-ms)





