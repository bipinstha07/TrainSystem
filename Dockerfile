FROM openjdk:21-jdk-slim
WORKDIR /app

COPY target/restapi-0.0.1-SNAPSHOT.jar /app/myapp.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "myapp.jar"]