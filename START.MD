MAVEN COMPILACIÓN:
./mvnw clean package

BUILD DE DOCKER:
docker build -t spring-hello-world .

DOCKER RUN:
docker run -p 8080:8080 spring-hello-world