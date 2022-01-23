FROM openjdk:8-jdk

ENV PORT 8080
ENV ADMIN_PORT 8081

EXPOSE 8080
EXPOSE 8081

COPY /src/main/resources/config.yml /config.yml
COPY /target/OracleTasks-1.jar /OracleTasks-1.jar


CMD java -jar OracleTasks-1.jar server config.yml
