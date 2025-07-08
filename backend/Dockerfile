FROM gradle:8.5.0-jdk17-jammy AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]