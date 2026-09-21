```
User
├── id
├── firstName
├── lastName
├── email
├── phone
├── password
├── emailVerified
├── phoneVerified
├── roles
├── enabled
├── createdAt
└── updatedAt
```

```
Otp
├── id
├── userId
├── destination
├── otp
├── type          // EMAIL / PHONE
├── expiresAt
├── verified
└── createdAt
```

````
Product
├── id
├── name
├── description
├── price
├── stock
├── categoryId
├── images
├── active
├── createdAt
└── updatedAt

```
Cart
├── id
├── userId
├── items
└── updatedAt
```

```
CartItem
├── productId
├── quantity
└── price
```

```
Order
├── id
├── userId
├── items
├── totalAmount
├── status
├── shippingAddress
├── paymentId
├── createdAt
└── updatedAt
```

```
Payment
├── id
├── orderId
├── userId
├── amount
├── provider
├── transactionId
├── status
└── createdAt
```

```
 Address
├── id
├── userId
├── firstName
├── lastName
├── phone
├── addressLine1
├── addressLine2
├── city
├── postalCode
└── country
```

```
Category
├── id
├── name
├── description
└── active
├── parentId
├── updatedAt
└── createdAt
``