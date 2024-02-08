# E-Commerce Shop RESTful API

## Project Overview

You have been assigned the task of developing a functional e-commerce shop backend system. This API will act as the backbone, managing various aspects ranging from product management to order processing. Its purpose is to interact with front-end applications, allowing users to browse products, add them to a cart, and place orders.

## Requirements

### 1. Project Setup

- **Framework:** Spring Boot
- **Java Version:** Latest stable release
- **Build Tool:** Maven
- **Database:** MySQL
- **Version Control:** Git (Maintain a repository with regular commits)

### 1.1 ECommerce Application - Design 
<img width="1079" alt="Screenshot 2024-02-08 at 22 29 52" src="https://github.com/netabirami/FinalProject/assets/144056783/9afeb736-309b-473d-b328-b27c10a07054">

### 2. Core Features

#### 2.1. User Management

- **Registration:** Allow new users to register with basic information (name, email, password).
- **Authentication:** Implement JWT Auth.
- **Authorization:** Define roles (e.g., customer, admin) and permissions.

#### 2.2. Product Management

- **Product Listing:** Enable the listing of products with details (name, price, description, stock).
- **Product Addition:** Allow authorized users (e.g., admins) to add new products.
- **Product Update & Deletion:** Admins should be able to update and delete products.

#### 2.3. Shopping Cart

- **Cart Creation:** Users can create a cart and add items to it.
- **Item Addition & Removal:** Users can add or remove products from the cart.
- **Cart View:** Users can view all items in their cart along with the total price.

#### 2.4. Order Processing

- **Order Placement:** Convert cart items into an order.
- **Order History:** Users can view their past orders with statuses (placed, shipped, delivered).

### 3. Additional Components

#### 3.1. API Documentation

- **Swagger or OpenAPI:** Provide detailed documentation for your API endpoints.

#### 3.2. Validation

- Ensure data validation for user inputs and provide meaningful error messages.

#### 3.3. Error Handling

- Implement global error handling to return appropriate responses for various error scenarios.

#### 3.4. Logging

- Set up logging for tracking errors and important application events.

### 4. Testing

- **Unit Testing:** Write unit tests for your business logic.
- **Integration Testing:** Test the integration between various components of your application.

### 5. Deployment (Optional)

- **Docker:** Containerize your application.
- **Cloud Provider:** Consider deploying your application to a cloud provider like Heroku or AWS.

## Deliverables

- Source code pushed to a Git repository (e.g., GitHub, Bitbucket).
- Documentation covering:
    - Setup instructions
    - Usage examples for all API endpoints
    - Any assumptions or decisions made during development
  
## Swagger Documentation 

http://localhost:8080/swagger-ui/index.html

<img width="1430" alt="Screenshot 2024-01-09 at 22 34 39" src="https://github.com/netabirami/FinalProject/assets/144056783/f3973543-4e62-479e-87e2-57597f56aa07">

## Database Tables 

<img width="595" alt="Screenshot 2024-01-09 at 22 39 03" src="https://github.com/netabirami/FinalProject/assets/144056783/dde470e2-33ad-4db8-8bfc-cc4f581c5fbb">


## Evaluation Criteria

- **Functionality:** Does the application work as required?
- **Code Quality:** Is the code clean, well-organized, and properly commented?
- **Error Handling:** How well does the application handle unexpected situations?
- **Testing:** Adequacy and completeness of tests.
- **Documentation:** Clarity and completeness of documentation.

## Conclusion

This project is your opportunity to showcase your skills in building a real-world Java backend application using Spring Boot. Approach it with creativity and attention to detail. Good luck, and we look forward to seeing your completed e-commerce shop!
