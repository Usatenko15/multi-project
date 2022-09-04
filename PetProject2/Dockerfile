FROM openjdk:11
ADD build/libs/multi-project/PetProject2-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=test", "-jar", "app.jar"]