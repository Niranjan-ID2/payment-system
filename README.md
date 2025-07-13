# Payment System

This is a sample payment processing microservice that demonstrates the use of the Strategy pattern.

## Strategy Pattern

The Strategy pattern is a behavioral design pattern that enables selecting an algorithm at runtime. Instead of implementing a single algorithm directly, code receives run-time instructions as to which in a family of algorithms to use.

In this project, the `PaymentStrategy` interface defines a common contract for all payment algorithms. Each concrete implementation (`GPayPaymentStrategy`, `CreditCardPaymentStrategy`, `BankTransferPaymentStrategy`) provides a different way to process a payment.

The `PaymentProcessor` acts as the context, which is configured with a concrete strategy object. When a payment needs to be processed, the context delegates the work to the strategy object. The client (in this case, the `PaymentService`) is responsible for creating a payment request and passing it to the processor.

### Why this pattern fits

The Strategy pattern is a good fit for this problem for the following reasons:

*   **Flexibility**: It allows us to add new payment methods without changing the core logic of the `PaymentProcessor`. We can simply create a new class that implements the `PaymentStrategy` interface and register it with the processor.
*   **Decoupling**: It decouples the payment processing logic from the client that uses it. The `PaymentService` doesn't need to know the details of how each payment method is implemented.
*   **Testability**: It makes it easier to test the payment processing logic in isolation. We can test each payment strategy independently, and we can use mock objects to test the `PaymentProcessor` without actually calling the payment APIs.
*   **Open/Closed Principle**: The design is open for extension (we can add new payment methods) but closed for modification (we don't need to change the existing code).

## How to run

This is a standard Spring Boot application. You can run it from your IDE or by using the following Maven command:

```bash
mvn spring-boot:run
```

## APIs

### Initiate a payment

*   **URL**: `/payments`
*   **Method**: `POST`
*   **Body**:
    ```json
    {
      "userId": "user-123",
      "paymentMode": "creditCard",
      "amount": 100.00,
      "currency": "USD",
      "remarks": "Test payment"
    }
    ```
*   **Success Response**:
    *   **Code**: 200 OK
    *   **Content**: The processing ID (UUID) of the payment.

### Get payment status

*   **URL**: `/payments/{processingId}`
*   **Method**: `GET`
*   **Success Response**:
    *   **Code**: 200 OK
    *   **Content**:
        ```json
        {
          "processingId": "...",
          "userId": "user-123",
          "paymentMode": "creditCard",
          "amount": 100.00,
          "currency": "USD",
          "remarks": "Test payment",
          "status": "SUCCESSFUL"
        }
        ```
*   **Error Response**:
    *   **Code**: 404 Not Found
    *   **Content**: Empty