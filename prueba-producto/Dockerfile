FROM openjdk:17
ARG DEPENDENCY=target/dependency

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","com.mca.pruebaproducto.PruebaProductoApplication"]