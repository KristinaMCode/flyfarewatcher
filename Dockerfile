FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "target/flyfarewatcher-0.0.1-SNAPSHOT.jar"]