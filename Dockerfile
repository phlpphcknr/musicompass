FROM openjdk:15

MAINTAINER Philipp Hackner <philipp.hackner@protonmail.com>

ADD backend/target/musicompass.jar app.jar

CMD ["sh", "-c", "java -jar /app.jar"]
