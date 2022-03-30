FROM adoptopenjdk/openjdk11:alpine-jre

COPY target/poll-0.0.1-SNAPSHOT.jar /poll-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "poll-0.0.1-SNAPSHOT.jar"]