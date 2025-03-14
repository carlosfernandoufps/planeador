# Planeador
Planeador. 
Proyecto de Grado Trabajo Dirigido.
Carlos Calderon.

# Requisitos para ejecutar la aplicación de manera local:

## Java

Para poder ejecutar el back en local se necesita Java 17 o superior y Gradle. 

1. Descargar JDK 17 según sistema operativo. Sitio oficial: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
2. Seguir las instrucciones de instalación
3. Abrir una consola de comandos y verificar instalación con `java -version`

## Gradle

1. Instalar última versión de Gradle. Sitio oficial: https://gradle.org/install/
2. Seguir las instrucciones de instalación.
3. Abrir una consola de comandos y verificar instalación con `gradle -v`

## NodeJs

Para poder ejecutar el front en local se necesita NodeJs 18 o superior.

1. Descargar última versión de NodeJs. Sitio oficial: https://nodejs.org/en/download
2. Seguir las instrucciones de instalación.
3. Abrir una consola de comandos y verificar instalación con `npm -v`

# Usar la aplicación

La aplicación, de manera local, utiliza una base de datos embebida H2. En caso de que exista una carpeta raíz «Data», la eliminamos para trabajar con los datos inicializados por defecto de prueba.

1. Construir el Jar con Gradle utilizando el comando `./gradlew build` en la raíz de la carpeta backend.
2. Ejecutar el backend con: `java -jar build/libs/planeador-0.0.1-SNAPSHOT` 
3. Acceder a la documentación de la API: http://localhost:8080/swagger-ui/index.html#/
4. Acceder a la base de datos embedida en http://localhost:8080/h2-console
5. Instalar las dependencias a nivel de raíz de la capeta front con el comnando `npm i`
6. Ejecutar el front con el comando `npm run dev`
7. Acceder al front a través de la url: http://localhost:5173/

## Usuarios de prueba por defecto

### Director de prueba

1. user:director@mail.com pass:director123 

### Docentes de prueba

2. user:techer1@mail.com pass:docente123
3. user:techer2@mail.com pass:docente123
4. user:techer3@mail.com pass:docente123 