# LetsShop 🛒

A production-oriented **e-commerce REST API** built with **Java 21, Spring Boot, MongoDB, Spring Security, JWT, and Moyasar Payments**.

LetsShop provides the backend foundation for an online shopping platform, including user authentication, product and category management, shopping carts, orders, reviews, addresses, inventory management, and real payment processing with **Moyasar**.

---

## 🚀 Features

### 🔐 Authentication & Authorization

* User registration and login
* JWT-based authentication
* Login using email or phone
* Password hashing using `PasswordEncoder`
* Role-based authorization
* Authenticated user context through Spring Security
* Protected REST endpoints
* Guest authentication flow planned

### 👤 User Management

* User profiles
* Email verification support
* Phone verification support
* User roles
* Account status management
* Secure password storage

### 📦 Product Management

* Create products
* Get product details
* Get all products
* Search products by name
* Filter products by:

    * Category
    * Brand
    * Minimum price
    * Maximum price
* Sort products by:

    * Price ascending
    * Price descending
    * Newest
    * Oldest
* Inventory quantity management
* Product images support

### 🗂️ Category Management

* Create categories
* Retrieve categories
* Parent/child category structure
* Active/inactive category support

### 🛒 Shopping Cart

* Add products to cart
* Update product quantities
* Increase/decrease item quantity
* Remove individual products
* Retrieve current user's cart
* Embedded cart items using MongoDB documents
* Automatic removal of purchased products after successful payment

### ⭐ Product Reviews

* Add reviews to products
* Star ratings
* User-based review ownership
* Retrieve product reviews

### 📍 Address Management

* Add delivery addresses
* Retrieve user addresses
* Update existing addresses
* Associate addresses with users

### 📋 Order Management

* Create orders
* Retrieve authenticated user's orders
* Store order items and prices
* Calculate order totals on the server
* Validate product availability
* Track order status
* Track payment status
* Store payment-provider transaction ID

### 💳 Payment Integration

Integrated with **Moyasar** for real payment processing.

Payment flow:

```text
Create Order
     ↓
Calculate total from database
     ↓
Create Moyasar Payment
     ↓
Receive Payment ID
     ↓
3D Secure Authentication
     ↓
Verify Payment with Moyasar
     ↓
Verify Payment Amount
     ↓
Verify Currency
     ↓
Mark Order as PAID
     ↓
Confirm Order
     ↓
Reduce Product Stock
     ↓
Remove Purchased Products From Cart
```

The backend does **not trust the payment amount supplied by the client**. The amount is calculated from the order stored in MongoDB.

---

# 🏗️ Architecture

LetsShop follows a layered Spring Boot architecture:

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
MongoDB
```

External payment processing:

```text
LetsShop Backend
       │
       ▼
    Moyasar
       │
       ▼
   Card / 3DS
```

### Project Structure

```text
src/main/java/com/ecom
│
├── config
│   ├── SecurityConfig
│   ├── MoyasarConfig
│   └── RestClientConfig
│
├── controller
│   ├── auth
│   ├── product
│   ├── category
│   ├── cart
│   ├── order
│   ├── payment
│   ├── review
│   └── address
│
├── dto
│   ├── auth
│   ├── product
│   ├── category
│   ├── cart
│   ├── order
│   ├── payment
│   ├── review
│   └── address
│
├── models
│   ├── auth
│   ├── product
│   ├── category
│   ├── cart
│   ├── order
│   ├── payment
│   ├── review
│   └── address
│
├── repository
│   ├── user
│   ├── product
│   ├── category
│   ├── cart
│   ├── order
│   ├── review
│   └── address
│
├── service
│   ├── auth
│   ├── product
│   ├── category
│   ├── cart
│   ├── order
│   ├── payment
│   ├── review
│   └── address
│
└── exceptions
    ├── auth
    ├── product
    ├── order
    └── ...
```

---

# 🛠️ Technology Stack

| Technology          | Purpose                         |
| ------------------- | ------------------------------  |
| Java 21             | Programming language            |
| Spring Boot         | Backend framework               |
| Spring Web          | REST API development            |
| Spring Security     | Authentication & authorization  |
| JWT                 | Stateless authentication        |
| Spring Data MongoDB | MongoDB integration             |
| MongoDB             | Database                        |
| Lombok              | Boilerplate reduction           |
| Jakarta Validation  | Request validation              |
| RestClient          | External API communication      |
| Moyasar             | Payment processing              |
| Maven               | Dependency management           |
| Git & GitHub        | Version control                 |
| Redis               | In memory management            |
| Kafka               | Event streaming between services|

---

# 🔒 Security

LetsShop uses Spring Security with JWT authentication.

Authentication flow:

```text
Login
  ↓
Validate Credentials
  ↓
Generate JWT
  ↓
Client Stores Token
  ↓
Client Sends JWT
  ↓
JWT Filter
  ↓
Validate Token
  ↓
Load User
  ↓
SecurityContext
  ↓
Controller / Service
```

Protected requests use:

```http
Authorization: Bearer <JWT_TOKEN>
```

Sensitive configuration such as database credentials, JWT secrets, and Moyasar API keys are stored outside the source code using environment variables.

---

# 💳 Moyasar Payment Flow

The payment integration uses Moyasar's token-based payment flow.

### 1. Create Order

```http
POST /api/order
```

The server:

* Retrieves product information from MongoDB
* Validates stock
* Uses database prices
* Calculates the total
* Creates the order

### 2. Create Payment

```http
POST /api/payment
```

Example request:

```json
{
  "orderId": "ORDER_ID",
  "token": "MOYASAR_TOKEN"
}
```

The backend:

1. Authenticates the user
2. Finds the order
3. Verifies order ownership
4. Calculates the amount from the database
5. Sends the payment request to Moyasar
6. Stores the Moyasar payment ID
7. Returns the payment response

### 3. Complete 3DS

Moyasar returns a transaction URL when additional authentication is required.

```text
transaction_url
```

The client completes the 3D Secure authentication.

### 4. Verify Payment

```http
GET /api/payment/{paymentId}
```

The backend retrieves the latest payment information from Moyasar and verifies:

* Payment status
* Payment amount
* Currency
* Order ownership
* Payment/order association

Only after successful verification does the backend update the order.

```text
paymentStatus = PAID
orderStatus   = CONFIRMED
```

### 5. Inventory & Cart Update

After successful payment:

```text
Product Stock
      ↓
Quantity Reduced

Cart
      ↓
Purchased Items Removed
```

The payment verification flow is designed to be idempotent so that repeatedly checking an already-paid payment does not reduce inventory multiple times.

---

# 📡 API Overview

## Authentication

| Method | Endpoint             | Description               |
| ------ | -------------------- | ------------------------- |
| POST   | `/api/auth/register` | Register user             |
| POST   | `/api/auth/login`    | Login                     |
| ...    | `/api/auth/...`      | Authentication operations |

## Products

| Method | Endpoint              | Description              |
| ------ | --------------------- | ------------------------ |
| POST   | `/api/product`        | Create product           |
| GET    | `/api/product`        | Get products             |
| GET    | `/api/product/{id}`   | Get product              |
| GET    | `/api/product/search` | Search products          |
| GET    | `/api/product/filter` | Filter and sort products |

## Categories

| Method | Endpoint                     | Description     |
| ------ | ---------------------------- | --------------- |
| POST   | `/api/category`              | Create category |
| GET    | `/api/category/get-category` | Get categories  |

## Cart

| Method | Endpoint                              | Description       |
| ------ | ------------------------------------- | ----------------- |
| POST   | `/api/cart`                           | Add item          |
| GET    | `/api/cart`                           | Get current cart  |
| PATCH  | `/api/cart/item/{productId}/increase` | Increase quantity |
| PATCH  | `/api/cart/item/{productId}/decrease` | Decrease quantity |
| DELETE | `/api/cart/item/{productId}`          | Remove item       |

## Orders

| Method | Endpoint     | Description       |
| ------ | ------------ | ----------------- |
| POST   | `/api/order` | Create order      |
| GET    | `/api/order` | Get user's orders |

## Payments

| Method | Endpoint                   | Description             |
| ------ | -------------------------- | ----------------------- |
| POST   | `/api/payment`             | Create Moyasar payment  |
| GET    | `/api/payment/{paymentId}` | Verify/retrieve payment |

## Reviews

| Method | Endpoint                           | Description         |
| ------ | ---------------------------------- | ------------------- |
| POST   | `/api/reviews/product/{productId}` | Add product review  |
| GET    | `/api/reviews/product/{productId}` | Get product reviews |

## Addresses

| Method | Endpoint                   | Description        |
| ------ | -------------------------- | ------------------ |
| POST   | `/api/address`             | Add address        |
| GET    | `/api/address`             | Get user addresses |
| PATCH  | `/api/address/{addressId}` | Update address     |

> Endpoint names may evolve as the API continues to develop.

---

# 🗄️ MongoDB Collections

The application currently uses collections such as:

```text
users
products
categories
carts
orders
reviews
addresses
```

Example order document:

```json
{
  "_id": "order-id",
  "userId": "user-id",
  "orderItemList": [
    {
      "productId": "product-id",
      "quantity": 2,
      "price": 899.99
    }
  ],
  "address": "Riyadh, Saudi Arabia",
  "totalAmount": 1799.98,
  "paymentStatus": "PAID",
  "orderStatus": "CONFIRMED",
  "moyasarPaymentId": "moyasar-payment-id",
  "createdAt": "2026-10-02T10:00:00",
  "updatedAt": "2026-10-02T10:10:00"
}
```

---

# ⚙️ Getting Started

## Prerequisites

Make sure you have installed:

* Java 21
* Maven
* MongoDB
* Git
* Redis
* Kafka

You also need a Moyasar test account/API credentials for payment testing.

---

## Clone the Repository

```bash
git clone https://github.com/whokashmiri/LetsShop.git

cd LetsShop
```

---

## Environment Variables

Create a `.env` file in the project root.

```env
MONGODB_DATABASE=letshop
MONGODB_URI=mongodb://localhost:27017/letshop

SECRET=your-jwt-secret
JWT_EXPIRATION=86400000

MOYASAR_SECRET_KEY=your-moyasar-secret-key
MOYASAR_PUBLISHABLE_KEY=your-moyasar-publishable-key
```

**Never commit `.env` or real API keys to GitHub.**

Add it to `.gitignore`:

```gitignore
.env
```

---

# ▶️ Running the Application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API will run on:

```text
http://localhost:8080
```

---

# 🧪 Testing

The API can be tested using:

* Postman
* Insomnia
* Swagger/OpenAPI if added
* Frontend application

Recommended testing flow:

```text
1. Register
      ↓
2. Login
      ↓
3. Create Category
      ↓
4. Create Product
      ↓
5. Add Product to Cart
      ↓
6. Create Order
      ↓
7. Create Moyasar Payment
      ↓
8. Complete 3DS
      ↓
9. Verify Payment
      ↓
10. Confirm Order
      ↓
11. Verify Stock Reduction
      ↓
12. Verify Cart Cleanup
```

---

# 🧠 Important Business Rules

### Server-side price calculation

The client does not determine the final order price.

```text
Client
  ↓
productId + quantity
  ↓
Backend
  ↓
Product price from MongoDB
  ↓
Calculate total
```

### Server-side payment verification

The backend does not trust a frontend `"paid"` status.

Instead:

```text
Backend
   ↓
Moyasar API
   ↓
Verify actual payment
```

### Payment amount verification

The payment amount must match:

```text
Order.totalAmount × 100
```

because Moyasar uses the smallest currency unit.

For example:

```text
3159.96 SAR

3159.96 × 100
       ↓
315996 halalas
```

### Payment idempotency

Once an order has:

```text
paymentStatus = PAID
```

the backend must not process the payment again.

This prevents:

```text
Stock = 10

First verification:
10 → 8

Second verification:
8 → 6 ❌
```

Instead:

```text
Stock = 10

First verification:
10 → 8

Second verification:
8 → 8 ✓
```

---

# 🔮 Future Improvements

Planned improvements include:

* [ ] Admin order management
* [ ] Order cancellation
* [ ] Order shipping workflow
* [ ] Product reservation to prevent overselling
* [ ] Atomic inventory updates
* [ ] Wishlist
* [ ] Coupon/discount system
* [ ] Pagination improvements
* [ ] Global exception handling with standardized error responses
* [ ] API documentation with Swagger/OpenAPI
* [ ] Automated unit tests
* [ ] Integration tests
* [ ] Payment webhook/callback handling
* [ ] Email notifications
* [ ] SMS notifications
* [ ] Docker support
* [ ] CI/CD pipeline
* [ ] Production deployment
* [ ] Monitoring and logging

---

# 🧪 Project Status

### Core Features

| Feature                | Status  |
| ---------------------- | ------  |
| User Authentication    | ✅      |
| JWT Security           | ✅      |
| Products               | ✅      |
| Categories             | ✅      |
| Product Filtering      | ✅      |
| Cart                   | ✅      |
| Orders                 | ✅      |
| Reviews                | ✅      |
| Addresses              | ✅      |
| Moyasar Payment        | ✅      |
| 3D Secure Payment      | ✅      |
| Payment Verification   | ✅      |
| Stock Reduction        | ✅      |
| Cart Cleanup           | ✅      |
| Redis                  | ✅      |
| Kafka                  | ✅      |
| Admin Order Management | 🚧      |
| Wishlist               | 🚧      |
| Coupons                | 🚧      |
| Notifications          | 🚧      |
| Automated Tests        | 🚧      |
| CI/CD                  | 🚧      |

---

# 📚 Learning Objectives

This project was built to gain practical experience with:

* Java 21
* Spring Boot
* REST API design
* Spring Security
* JWT authentication
* Dependency Injection
* MongoDB
* Spring Data MongoDB
* DTO-based API design
* Layered architecture
* Exception handling
* Input validation
* Payment gateway integration
* 3D Secure payment flows
* Inventory management
* Order lifecycle management
* External API integration
* Secure configuration
* Git/GitHub
* Redis
* Kafka

---

# 👨‍💻 Author

**Aaqib Bashir Mir**

Full Stack Developer • Java/Spring Boot Developer • Salesforce Developer • AI Automation Engineer

GitHub:
https://github.com/whokashmiri

LinkedIn:
https://linkedin.com/in/whokashmiri

---

# 📄 License

This project is intended for learning, portfolio development, and demonstration purposes.
