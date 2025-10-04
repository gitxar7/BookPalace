

<div align="center">

# BookPalace - Online Book Store Platform
  
  ![BookPalace Logo](web/images/bookstore_logo.png)
  
  **A comprehensive Java web application for book trading and e-commerce**
  
  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
  ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
  ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
  ![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)
  ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
  
</div>

## Project Overview

BookPalace is a full-stack Java web application that provides a complete online marketplace for book enthusiasts. Built with enterprise-grade technologies, it offers a seamless experience for buying, selling, and managing books online with features like user authentication, shopping cart, order management, and more.

## Key Features

### User Management

- **User Registration & Authentication** with email verification
- **Profile Management** with personal information updates
- **Secure Login/Logout** with session management
- **Password Validation** with strong security requirements

### Product Management

- **Advanced Book Catalog** with genre and age category filtering
- **Product Search & Filtering** with multiple criteria
- **Add/Remove Products** for sellers
- **Single Product View** with detailed information
- **Product Image Management** with cover image uploads

### Shopping Experience

- **Shopping Cart** with add/remove/update functionality
- **Checkout Process** with order summary
- **Order Management** with order history and status tracking
- **Address Management** for delivery locations

### Search & Discovery

- **Basic Search** across product titles and descriptions
- **Advanced Search** with genre, age, condition filters
- **Category-based Browsing** for easy navigation

## Technical Architecture

### Backend Technologies

- **Java Servlets** - Server-side request handling
- **Hibernate ORM** - Object-relational mapping and database operations
- **MySQL Database** - Data persistence and storage
- **C3P0** - Connection pooling for database efficiency
- **Gson** - JSON serialization/deserialization
- **JavaMail API** - Email verification system

### Frontend Technologies

- **HTML5 & CSS3** - Modern web standards
- **Bootstrap 5** - Responsive UI framework
- **JavaScript** - Dynamic client-side functionality
- **Bootstrap Icons** - Comprehensive icon library

### Database Schema

The application uses a well-structured relational database with the following entities:

- `User` - User account information
- `Product` - Book/product details
- `Cart` - Shopping cart items
- `Order` & `OrderItem` - Order management
- `Genre`, `Age`, `Condition` - Product categorization
- `Address`, `City` - Location management
- `Status`, `OrderStatus` - State management

## Project Structure

```
BookPalaceProject/
├── src/java/
│   ├── controller/          # Servlet controllers
│   │   ├── SignUp.java     # User registration
│   │   ├── SignIn.java     # User authentication
│   │   ├── AddProduct.java # Product management
│   │   ├── AddToCart.java  # Cart operations
│   │   ├── Checkout.java   # Order processing
│   │   └── ...
│   ├── entity/             # Hibernate entities
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Cart.java
│   │   └── ...
│   ├── dto/                # Data Transfer Objects
│   ├── model/              # Business logic & utilities
│   └── hibernate.cfg.xml   # Hibernate configuration
├── web/                    # Frontend resources
│   ├── *.html             # HTML pages
│   ├── css/               # Custom stylesheets
│   ├── js/                # JavaScript files
│   ├── images/            # Static images
│   └── bs/                # Bootstrap resources
└── lib/                   # JAR dependencies
```

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 8+**
- **Apache Tomcat 9+** or any Java EE compatible server
- **MySQL 8.0+**
- **NetBeans IDE** (recommended) or any Java IDE

### Installation Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/gitxar7/BookPalace.git
   cd BookPalace
   ```

2. **Database Setup**

   - Create a MySQL database named `book_palace`
   - Update database credentials in `src/java/hibernate.cfg.xml`
   - Run the application to auto-create tables (Hibernate will handle schema generation)

3. **Configure Email Service**

   - Update email configuration in the `MailHTML` utility class
   - Set up SMTP credentials for email verification

4. **Deploy to Server**
   - Build the project using NetBeans or your preferred IDE
   - Deploy the generated WAR file to your Tomcat server
   - Start the server and navigate to `http://localhost:8080/BookPalaceProject`

## Application Pages

- **Home** (`index.html`) - Landing page with search functionality
- **Sign Up/Sign In** - User authentication pages
- **Account Verification** - Email verification system
- **Profile Management** - User profile updates
- **Add Product** - Sellers can list their books
- **My Products** - Manage seller inventory
- **Shopping Cart** - Cart management
- **Advanced Search** - Detailed product filtering
- **Single Product View** - Detailed product information

## Key Technical Implementations

### Security Features

- Input validation for all user inputs
- SQL injection prevention through Hibernate
- Password strength requirements
- Session management for authentication

### Performance Optimizations

- Connection pooling with C3P0
- Efficient Hibernate queries with Criteria API
- Responsive design for mobile compatibility
- Optimized image handling

### Code Quality

- MVC architecture separation
- Proper exception handling
- Clean code practices
- Comprehensive entity relationships

## Technologies Demonstrated

This project showcases proficiency in:

- **Java Enterprise Edition (J2EE)** development
- **Object-Relational Mapping (ORM)** with Hibernate
- **Database Design** and MySQL operations
- **RESTful API** design patterns
- **Frontend Development** with modern web technologies
- **Email Integration** and verification systems
- **Session Management** and security
- **Responsive Web Design**

## Future Enhancements

- Payment gateway integration
- Real-time chat system
- Advanced recommendation engine
- Mobile application development
- Cloud deployment (AWS/Azure)
- API documentation with Swagger
- Unit testing implementation

## Developer

**Abdur Rahman Hanas**

- GitHub: [@gitxar7](https://github.com/gitxar7)
- Email: nxt.genar7@gmail.com
- Project: [BookPalace](https://github.com/gitxar7/BookPalace)

## License

This project is open source and available under the [MIT License](LICENSE).

---

<div align="center">
  <strong>Star this repository if you found it helpful!</strong>
</div>
