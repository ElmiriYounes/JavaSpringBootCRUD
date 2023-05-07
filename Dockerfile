FROM openjdk:17

EXPOSE 8080

WORKDIR /app

COPY target/*.jar app.jar

ENTRYPOINT [ "java", "-Dspring.profiles.active=container", "-jar", "app.jar" ]
