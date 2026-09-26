FROM eclipse-temurin:25-jdk
COPY ./target/Team6-1.0-SNAPSHOT.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Team6-1.0-SNAPSHOT-jar-with-dependencies.jar"]