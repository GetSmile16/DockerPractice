FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src /app/src

RUN ./mvnw install -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

RUN adduser -u 1234 -D nonroot
USER nonroot

COPY --from=build /app/target/practice-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]