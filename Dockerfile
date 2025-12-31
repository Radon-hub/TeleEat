#1. First Stage BUILD
FROM maven:3.9.5-eclipse-temurin-21 AS build

LABEL maintainer="Radon"
LABEL env="stage"
LABEL purpose="TeleEat Application (Food reservation from telegram chat bot)"
LABEL time="2025/12/31 01:16 AM"

ARG APPAREA=/app
ARG SOURCE=./src

WORKDIR $APPAREA

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src $SOURCE

RUN mvn clean package -DskipTests

#2. Second Stage RUNTIME
FROM eclipse-temurin:21-jre-alpine

ENV APPAREA=/app

RUN addgroup -g 1000 teleeat && adduser -u 1000 -G teleeat -h /app -D teleeatuser

WORKDIR $APPAREA

COPY --from=build $APPAREA/target/*.jar app.jar

RUN chown -R teleeatuser:teleeat /app

USER teleeatuser:teleeat

EXPOSE 8080

ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-XX:+UseG1GC","-jar","app.jar"]
