# Shopping Basket Application
This project implements a shopping basket in Java Spring Boot. It allows adding items to the shopping basket and calculating the total price of the items in the basket. Additionally, certain discount deals are supported.

## Features
* Create the shopping basket
* Add items to the shopping basket
* Check the total price of items in the basket
* Apply sales deals: 
  * Buy 1 Get 1 Free 
  * 10% Off

## Usage
* build the project with ./gradlew build
* run the project with ./gradlew bootRun
* test the project with ./gradlew test

## Usage
Use Postman or a similiar software to call the controller endpoints.
* Create shopping basket
  * GET localhost:8080/api/v1/createbasket
* Add article to basket
  * POST localhost:8080/api/v1/scan/basket/{basketId}/article/A0002
* Calculate Sum
  * GET localhost:8080/api/v1/total/basket/{basketId}
* Add buy1Get1Free coupon
  * POST localhost:8080/api/v1/buy1get1free/basket/46c9b3b2-20ec-4947-98de-6c12ad36e657/article/A0002
* Add 10percentOff coupon
  * POST localhost:8080/api/v1/10percentoff/basket/46c9b3b2-20ec-4947-98de-6c12ad36e657/article/A0002