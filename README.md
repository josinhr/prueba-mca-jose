# Prueba técnica prodcuto - José Hernández Rizo

## Consideraciones
En el fichero hoja-de-trabajo.txt se responden a las preguntas enunciadas en la hoja prueba.txt y también se realizan 
diversas aclaraciones que considero importantes a la hora de evaluar una prueba como esta. Mi intención es 
que la persona que lea este fichero pueda ver que razonamientos he seguido a la hora de programar para ver si
estos son compatibles con la cultura y estándares de programación de la empresa.

## Requisitos necesarios:
- Java version 17
- Docker
- Maven v.3.8 / v.3.9 

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