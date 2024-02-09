FROM eclipse-temurin:17-jdk-focal
LABEL authors="Abirami Vinoth"

WORKDIR /app
COPY target/E-CommerceManagement-0.0.1-SNAPSHOT.jar /app/ecommerce-spring-boot.jar
ENTRYPOINT ["java", "-jar", "ecommerce-spring-boot.jar"]

ENV PORT 8080
EXPOSE 8080