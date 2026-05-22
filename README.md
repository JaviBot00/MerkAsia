# MerkAsia - Examen

## Descripción

Repositorio de examen para la empresa ficticia MerkAsia. Contiene varios subproyectos y recursos (Android, servlets, importador de datos), ejercicios y datos de apoyo para prácticas y evaluación.

## Contenido principal del repositorio

- README.md                -> Este archivo: visión general y guía rápida.
- examen.txt / examen simplificado.txt
- data/                    -> Datos de prueba (data.csv).
- deployment/              -> Scripts y ficheros de despliegue (docker-compose, bbdd.sql).
- MerkAsia-Android/        -> Proyecto Android (código fuente, recursos, configuración Gradle).
- MerkAsia-ImportDataClient/ -> Cliente para importación de datos (Gradle/java).
- MerkAsia-Servlets/       -> Proyecto Java Servlets (pom.xml, webapp, src).

## Estructura sugerida para el examen

- Enunciados (docs/ o ficheros examen*.txt): ejercicios y criterios de evaluación.
- Implementaciones de ejemplo:
  - Android: app/ dentro de MerkAsia-Android (actividad principal y recursos).
  - Servlets: proyecto en MerkAsia-Servlets con ejemplo de endpoint JSP.
  - ImportDataClient: utilidad para cargar data/data.csv en la BBDD.
- Despliegue: deployment/docker-compose.yml y bbdd.sql para crear el esquema y datos iniciales.
- Tests: añadir pruebas unitarias o de integración en cada subproyecto según corresponda.

## Guía rápida de uso

1. Clonar el repositorio.
2. Revisar los ficheros de enunciado: examen.txt / examen simplificado.txt.
3. Para trabajar con Android: abrir MerkAsia-Android en Android Studio.
4. Para el proyecto Servlet: importar MerkAsia-Servlets en tu IDE Maven/Java.
5. Para cargar datos de prueba: revisar deployment/bbdd.sql y ejecutar el importador (MerkAsia-ImportDataClient) o cargar data/data.csv manualmente.

## Notas

Este README es un esbozo orientado al contenido real del repositorio. Completar con instrucciones específicas de evaluación, criterios, y pasos detallados para cada subproyecto según las necesidades del examen.
