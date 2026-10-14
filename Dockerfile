FROM eclipse-temurin:25-jdk
COPY ./target/Team6-0.2.0-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Team6-0.2.0-jar-with-dependencies.jar"]