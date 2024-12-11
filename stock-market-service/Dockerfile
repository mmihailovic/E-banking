FROM openjdk:17
WORKDIR /usr/src/app
COPY ./target/stock-market-service-0.1.jar .
EXPOSE 8081
ENTRYPOINT ["java","-jar","stock-market-service-0.1.jar"]