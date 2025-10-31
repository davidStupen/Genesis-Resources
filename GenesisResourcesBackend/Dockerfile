FROM amazoncorretto:21-alpine-jdk
COPY target/genesis.jar genesis.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "genesis.jar"]