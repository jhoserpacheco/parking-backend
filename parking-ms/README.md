# MICROSERVICE PARKING

## Descripción

El Microservicio de Parqueaderos es el núcleo del sistema, encargado de gestionar las operaciones relacionadas con 
los parqueaderos, vehículos y los registros de entrada y salida de vehículos. Además, proporciona indicadores sobre 
la actividad en los parqueaderos.

### Funcionalidades Principales
- CRUD de parqueaderos.
- Registro de entradas y salidas de vehículos.
- Generación de indicadores de parqueadero o vehiculos.

### Tecnologías Utilizadas
- Java 17
- Spring Boot
- PostgreSQL

### ER - Microservicio usuarios
<div style="align-items: center">
    <img src="src/main/resources/static/er-ms-parking.png" alt="Centered Image" />
</div>

### Endpoints de la API

Para obtener más información sobre los endpoints disponibles, consulta la documentación de la API.
Puedes importar la [colección de Postman](../Parking%20Backend.postman_collection.json)
o acceder a la documentación de Swagger [http://localhost:8081/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


### Generate Jar

Generamos el jar de cada microservicio, [aquí](../user-ms/README.md#Generate-Jar) un ejemplo:

### Variables de entorno

Crear un archivo .env siguiendo el ejemplo -> [sample.env](sample.env)

(Tanto en rabbit o postgres en vez de localhost colocar 'postgres' o 'rabbitmq')


```bash
POSTGRES_USER=postgresUserSample
POSTGRES_PASSWORD=postgresPasswordSample
POSTGRES_URI=postgresUriSample

JWT_SECRET=jwtSecretSample
JWT_EXPIRE_TIME=jwtExpireSample

SERVICE_EMAIL=email@sample.com

RABBIT_HOST=rabbitHostSample
RABBIT_PORT=rabbitPortSample
RABBIT_USER=rabbitUserSample
RABBIT_PASSWORD=rabbitPasswordSample
```



