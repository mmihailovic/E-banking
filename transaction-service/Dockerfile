FROM openjdk:17
WORKDIR /usr/src/app
COPY ./target/transaction-service-0.1.jar .
EXPOSE 8083
ENTRYPOINT ["java","-jar","transaction-service-0.1.jar"]