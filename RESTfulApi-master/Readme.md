# Java, Maven, Spring Boot, Spring Security, MySQL, JPA, Hibernate Rest API

This is an example Restful CRUD API built on top of Spring Boot + Security + MySql as part of a challenge in the recruiting process.

## Requirements

1. Java 8

2. Maven 

3. Spring Boot

4. Mysql 

## Steps to Setup

**1. Download the zip file or clone the repository**

```bash
git clone https://github.com/conlean/RESTfulApi.git
```

**2. Create Mysql database, set username and password**

create database name rebanking (default in example) or whatever

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**3. Run the app using maven**

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.
    
    GET localhost:8080 (Welcome, App running)

    GET localhost:8080/api/products/   (All items)
    
    POST localhost:8080/api/products/ (create new item)
    
    GET localhost:8080/api/products/{productId} (get item)
    
    PUT localhost:8080/api/products/{productId} (update item)
    
    DELETE localhost:8080/api/products/{productId} (delete item)

You can test them using postman or any other rest client.

    You must to add Basic Auth in your rest client. 

+ open `SecurityJavaConfig`and you can find 2 roles and credentials

## Key points to see as an example

+ API Crud implementation and validation of parameters.
+ Error handling to build the response (code + message).
+ Supports Security /api/products/** requests

## Possible improvements / out of scope

+ Testing




