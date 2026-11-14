FROM eclipse-temurin:25-jdk
COPY ./target/team6.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "team6.jar", "db:3306", "3000"]