FROM openjdk:15

MAINTAINER Philipp Hackner <philipp.hackner@protonmail.com>

ADD backend/target/musicompass.jar app.jar

CMD ["sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGODB_URI -Ddiscogs.auth.discogsToken=$DISCOGS_TOKEN -jar /app.jar"]
