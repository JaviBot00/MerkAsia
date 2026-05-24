# ═══════════════════════════════════════════════════════════
# Stage 1 — Build
# Usa la imagen oficial de Maven con Java 21 para compilar
# el proyecto y generar el .war
# ═══════════════════════════════════════════════════════════
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia primero solo el pom.xml para aprovechar la caché de
# Docker: si no cambian las dependencias, no las vuelve a
# descargar aunque sí cambie el código fuente
COPY MerkAsia-Servlets/pom.xml .
RUN mvn dependency:go-offline -B

# Ahora copia el código fuente y compila
COPY MerkAsia-Servlets/src ./src
RUN mvn clean package -DskipTests -B

# ═══════════════════════════════════════════════════════════
# Stage 2 — Runtime
# Imagen limpia de Tomcat 9, sin Maven ni JDK
# Solo copia el .war generado en el stage anterior
# ═══════════════════════════════════════════════════════════
FROM tomcat:9.0-jdk21

# Elimina las aplicaciones de ejemplo que trae Tomcat por defecto
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia el .war desde el stage de build directamente a webapps
# Tomcat lo despliega automáticamente al arrancar
COPY --from=build /app/target/MerkAsia-Servlets.war \
    /usr/local/tomcat/webapps/MerkAsia-Servlets.war

EXPOSE 8888
