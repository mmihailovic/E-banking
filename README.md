# E-Banking Solution

> **Note:** **All services in this repository are implemented in Java.**  
Some services have also been implemented in Kotlin, resulting in duplicate implementations within this repository.  
The full **Kotlin version** of the project, containing all services, is available in a separate repository [here](https://github.com/mmihailovic/E-Banking-Kotlin).

This project was developed as part of the Software Engineering exam at university. It was carried out by a team of approximately 20 students, organized into three specialized groups: Backend, Frontend, and CI/CD teams. The project serves as an e-banking solution, enabling clients to carry out various banking operations and trade stocks. Through this system, users can manage their bank accounts, execute transactions, and engage in stock market trading, all within a secure and efficient digital platform. **The frontend, built with React, TypeScript, and using a Microfrontend architecture, can be explored** [here](https://github.com/mmihailovic/E-Banking-Frontend)
.

This project is built using a microservices architecture consisting of five distinct microservices, each responsible for a specific functionality within the e-banking platform. The system uses **PostgreSQL** for data persistence, **Redis** NoSQL database for caching, **JUnit5** for testing, providing a reliable and scalable database solution.

The project also includes a CI/CD pipeline that automates the testing and deployment process. The pipeline runs tests on every commit, ensuring that the application is always in a deployable state. Upon successful tests, the application is automatically deployed to a Kubernetes cluster, providing scalable and efficient management of the microservices.

## User service

### Overview

User Service manages user accounts for individual and corporate clients, enabling registration, security features, and favorites management.

### Features

* **User Registration:** Individual clients register with personal details. Companies can register and add employees for corporate transactions.
* **Security Codes:** Generate codes for first login, password resets, and transaction confirmations. Sends emails to users for these codes using the **Notification Service.**
  Communicates with the Notification Service via **ActiveMQ** message broker for email delivery.
* **Favorites Management:** Save frequent transaction recipients.
* **Role Management:** The service stores user roles for access control, integrated with **Spring Security**, ensuring secure access and authorization.

## Notification service

### Overview
Notification Service is responsible for sending email notifications to users based on various events. It communicates with other services using **ActiveMQ** as the message broker.
Notifications that need to be sent are placed into a queue by other services, and the Notification Service retrieves them for processing and delivery.

### Features
* Listens to message queues in ActiveMQ for incoming notifications.
* Sends email notifications based on different message types.
* Scalable and decoupled architecture allowing integration with other services.

## Bank service

### Overview
Bank Service is the core microservice that enables users to manage various types of bank accounts and cards. It supports the creation of current, foreign currency, and business accounts
for both individuals and companies. The service also includes a currency exchange feature, which allows users to convert funds between accounts that are held in different currencies. Service
also supports loan applications, allowing users to submit requests for loans directly through the platform.

Bank service also communicates with other microservices via **RabbitMQ** for managing bank accounts funds.

### Features
* **Account creation:** Users can create different types of bank accounts.
* **Card creation:** Bank employees can create cards for clients.
* **Loan Applications:** Users can submit loan requests directly through the service, which can be approved or declined by bank.
* **Currency Exchange:** The service supports automatic currency conversion between accounts held in different currencies.

## Transaction service

### Overview
Transaction Service enables users to perform transactions between bank accounts. If a transaction involves accounts owned by different individuals, the service synchronously calls the **User microservice**
to verify the security code for transaction confirmation. Once the transaction is successfully created and validated, the service communicates with the **Bank microservice** via **RabbitMQ** to send a message
for transferring the funds between the accounts.

## Stock market service

### Overview
Stock Market Service enables bank clients to access stock market data and trade stocks by placing various types of orders. Stock market data is fetched from the **Alpha Vantage API** and cached in a **Redis** database
to ensure quick and efficient access.

### Features
* **Stock Trading:** Clients can place orders for buying or selling stocks. There are several types of orders supported:
  * **Market Order:** Executes immediately at the current market price.
  * **Limit Order:** Executes only when the stock price reaches a specified limit price.
  * **Stop Order:** Executes once the stock reaches a certain price (stop price).
  * **Stop-Limit Order:** Combines stop and limit orders, executing at a specified stop price but only if the stock price reaches a certain limit.
  * **All-or-None Order:** Executes only if the entire order can be fulfilled.

* **Order Matching:** A buy order is only executed if there is a corresponding sell order that matches the criteria, and vice versa.
* **Funds Reservation:** When a client places a buy order, the service synchronously checks with the Bank microservice to verify if the client has sufficient funds. If sufficient funds are available, the service reserves the necessary amount. Once two matching orders are executed, the Stock Market service communicates with the Bank microservice via **RabbitMQ** to facilitate the transfer of money between the client accounts involved in the trade.
