FROM openjdk:15

MAINTAINER Philipp Hackner <philipp.hackner@protonmail.com>

ADD backend/target/musicompass.jar app.jar

CMD ["sh", "-c", "java -Dserver.port=$PORT -Ddiscogs.auth.discogsToken=$DISCOGS_TOKEN -jar /app.jar"]
