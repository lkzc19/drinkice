FROM bitnami/java:17

ARG active=dev

WORKDIR /app

COPY ./target/loafer-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9101

ENTRYPOINT ["java", "-Dspring.profiles.active=${active}", "-jar", "app.jar"]