FROM openjdk:17
WORKDIR /usr/src/app
COPY ./target/notification-service-0.1.jar .
EXPOSE 8084
ENTRYPOINT ["java","-jar","notification-service-0.1.jar"]