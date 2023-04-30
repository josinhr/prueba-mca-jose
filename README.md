# prueba-mca-jose

## Requisitos necesarios:
- Java version 17
- Docker
- Maven v.3.8 >

## Instalación

Primero hay que compilar la aplicación para crear el .jar

```
cd prueba-producto
mvn clean install
```

Despues vamos a descomprimir el jar creado que nos permitirá dockerizar nuestra aplicación con los ficheros .csv de los datos.

```
mkdir target/dependency
cd target/dependency; jar -xf ../*.jar
cd ../..
```
Finalmente vamos a crear la imagen de nuestra aplicación y lanzarla en un servicio con docker compose.
```
docker build -t prueba-producto .
cd ..
docker-compose -f .\docker-componse.yml create
docker-compose -f .\docker-componse.yml start
```