FROM openjdk:8
COPY *.jar wanted-backend.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=common,deploy", "-jar", "/wanted-backend.jar"]