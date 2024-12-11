FROM openjdk:17
WORKDIR /usr/src/app
COPY ./target/bank-service-0.1.jar .
EXPOSE 8082
ENTRYPOINT ["java","-jar","bank-service-0.1.jar"]