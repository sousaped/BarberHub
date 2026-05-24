FROM eclipse-temurin:21-jre-alpine
WORKDIR /app-java
ARG PORT_BUILD=8080
ENV PORT=$PORT_BUILD
EXPOSE $PORT_BUILD
COPY target/barberhub-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

