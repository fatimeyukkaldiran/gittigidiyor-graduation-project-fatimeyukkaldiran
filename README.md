# Loan Application System
___
### Spring Boot Application

---
This project provides to apply credit for existing customers or new customers.

### Summary
The assessment consists of a credit service to be used already existing customers or new customers.
The credit score will be calculated with identification number and the credit result will be returned to the customer according to the relevant criteria.

#### Requirements

•  The API will expose an endpoint which accepts the user information (nationalID, firstName, lastName, monthlyIncome, phoneNumber) for new application.

• Once the endpoint is called, a new application will be opened connected to the customer whose nationalId is.

• The customer will be sent to the credit service, the score will be calculated according to the last digit of the identification number and the credit result will be returned according to the relevant criteria.

• Another Endpoint will output the user information showing creditResult, creditLimit.
___
The application has 2 apis
* CreditAPI
* CustomerAPI

```html
POST /creditApp/get-credit-result - calculate credit score and returns credit result.
GET /customer/getByNationalId/{nationalId}" - applies for loan by existing customer.
GET /customer/{nationalId} - retrieves all loan application for a customer.
```

JUnit test coverage is 100% 


### Tech Stack

---
- Java 8
- Spring Boot
- Spring Data JPA
- Maven 4.0.0
- Restful API
- Swagger documentation
- MySql database
- JUnit 5


### Prerequisites

---
- Maven


### Run & Build

---
run & build the application.

#### Maven

*$PORT: 8080*
```ssh
$ cd gittigidiyor-graduation-project-fatimeyukkaldiran/CustomerService
$ mvn clean install
$ mvn spring-boot:run
```
*$PORT: 8081*
```ssh
$ cd gittigidiyor-graduation-project-fatimeyukkaldiran/Credit-Application-Service
$ mvn clean install
$ mvn spring-boot:run
```

### Swagger UI will be run on this url
`http://localhost:${8080}/swagger-ui.html`

---
![loan-requests](https://github.com/113-GittiGidiyor-Java-Spring-Bootcamp/gittigidiyor-graduation-project-fatimeyukkaldiran/blob/main/swagger-http-requests/apply-loan.JPG)
![customer-crud-requests](https://github.com/113-GittiGidiyor-Java-Spring-Bootcamp/gittigidiyor-graduation-project-fatimeyukkaldiran/blob/main/swagger-http-requests/customer.JPG)
![error-requests](https://github.com/113-GittiGidiyor-Java-Spring-Bootcamp/gittigidiyor-graduation-project-fatimeyukkaldiran/blob/main/swagger-http-requests/error.JPG)

