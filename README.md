# Stripe Payment Integration with Java and Spring Boot

This repository provides a simple implementation of Stripe payments in a Java and Spring Boot application. 
It includes a single microservice dedicated to handling Stripe payments. 
The project is built using Maven and demonstrates the integration of the Stripe Java library.

## Prerequisites

Before starting, make sure you have a Stripe Secret Key. 
Additionally, Stripe offers a Test mode for developers to simulate transactions without real money.

## Getting Started

Clone the repository:

```git clone git@github.com:YaroslavVoronovskyi/SpringBootStripePaymentDemoApplication.git```

## API Endpoints
### Create Payment
#### Endpoint: /api/v1/stripe/create-payment
##### Method: POST
#### Request Body:

```
{
  "amount": 1000,
  "quantity": 1,
  "currency": "USD",
  "name": "Product Name",
  "successUrl": "http://localhost:3000/success",
  "cancelUrl": "http://localhost:3000/cancel"
}
```
#### Response:
```
{
  "status": "SUCCESS",
  "message": "Payment session created successfully",
  "httpStatus": 200,
  "data": {
    "sessionId": "stripe-session-id",
    "sessionUrl": "checkout-url"
  }
}
```

### Capture Payment
#### Endpoint: /api/v1/stripe/capture-payment
##### Method: GET
#### Request Parameter:
```
sessionId: The ID of the payment session
```
#### Response:
```
{
  "status": "SUCCESS",
  "message": "Payment successfully captured.",
  "httpStatus": 200,
  "data": {
    "sessionId": "stripe-session-id",
    "sessionStatus": "complete",
    "paymentStatus": "paid"
  }
}

```
