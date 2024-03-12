#FROM openjdk:17-jdk-alpine3.14
FROM eclipse-temurin:21.0.2_13-jdk-alpine

VOLUME /tmp

RUN apk update && apk add tzdata
ENV TZ=America/Asuncion

RUN addgroup -S devops && adduser -S javams -G devops
USER javams:devops

ENV JAVA_OPTS=""
ARG PORT=8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE ${PORT}
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]