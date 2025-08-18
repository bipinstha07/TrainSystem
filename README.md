# 🚆 Train Management System Backend

A **FULL-BACKEND TRAIN MANAGEMENT SYSTEM** built with **JAVA SPRING BOOT** to manage **TRAINS, STATIONS, ROUTES, SCHEDULES, SEATS, BOOKINGS, AND PAYMENTS** with secure **JWT-BASED AUTHENTICATION**.

---

## 📦 Project Purpose
- REST API that manages a railway reservation system  
- Supports **train, station, route, schedule, seat, booking, payment, and user management**  
- **Admin endpoints** (CRUD for trains, stations, routes, schedules, seats)  
- **User endpoints** (search trains, make bookings, view/cancel bookings, handle payments)  
- Integrated with **Spring Security + JWT** for authentication/authorization  
- Uses **Aspect-Oriented Programming (AOP)** for logging & payment handling  
- Auto-generated **OpenAPI / Swagger documentation**

---

## 🗂️ Repository Structure
```├─ .gitignore / .gitattributes / .DS_Store
├─ mvnw / mvnw.cmd / pom.xml
├─ README.md
├─ src
│ ├─ main
│ │ ├─ java/com/restapi
│ │ │ ├─ RestapiApplication.java
│ │ │ ├─ aspectOriented
│ │ │ │ ├─ CustomAopAnnotation.java
│ │ │ │ ├─ CustomAspect.java
│ │ │ │ ├─ CustomPaymentAnnotation.java
│ │ │ │ └─ LoggingAspect.java
│ │ │ ├─ config
│ │ │ │ ├─ AppConfig.java
│ │ │ │ ├─ OpenAPIConfig.java
│ │ │ │ └─ security
│ │ │ ├─ controller
│ │ │ │ ├─ admin (AdminStation, AdminTrain, …)
│ │ │ │ └─ user (TrainSearchController, UserBooking, UserPayment)
│ │ │ ├─ dto
│ │ │ ├─ entity
│ │ │ ├─ exceptionhandler
│ │ │ ├─ repository
│ │ │ └─ service
│ │ └─ resources
│ │ ├─ application.properties
│ │ └─ logback-spring.xml
└─ uploads (media files)
```

---

## 🔑 Core Modules
```| Layer       | Key Classes | Responsibility |
|------------|-------------|----------------|
| Entity     | User, Role, Station, Train, TrainRoute, TrainSchedule, TrainSeat, Booking, BookingPassenger, Payment | JPA mappings |
| DTO        | UserDto, StationDto, TrainDto, TrainRouteDto, TrainScheduleDto, TrainSeatDto, BookingDto, BookingRequestDto, BookingResponseDto, PaymentDto, JwtResponse, PageResponse | API objects |
| Repository | UserRepo, RoleRepo, StationRepo, TrainRepo, TrainRouteRepo, TrainScheduleRepo, TrainSeatRepo, BookingRepo, BookingPassengerRepo, PaymentRepo | CRUD & custom queries |
| Service    | UserService, StationService, TrainService, TrainRouteService, TrainScheduleService, TrainSeatService, BookingService, PaymentService | Business logic, transactions, AOP hooks |
| Controller | admin/*, user/* | REST endpoints, validation, response mapping |
| Security   | JwtHelper, JwtAuthenticationFilter, SecurityConfig, CustomUserDetails, CustomUserService, JWTAuthenticationEntryPoint, configPasswordEncoder | JWT auth, role-based access |
| AOP        | CustomAspect, LoggingAspect, CustomPaymentAnnotation | Logging & payment cross-cutting concerns |
| Config     | AppConfig, OpenAPIConfig | Bean definitions & Swagger UI |
```
---

## ⚙️ Technical Stack
- **Framework:** Spring Boot 3 (Spring MVC, Spring Data JPA, Spring Security)  
- **Database:** MySQL/H2 via JPA/Hibernate  
- **Security:** JWT tokens, BCrypt password encoder  
- **API Docs:** Springdoc OpenAPI / Swagger UI  
- **Testing:** JUnit 5  
- **Logging:** SLF4J + Logback  
- **AOP:** Spring AOP  
- **Validation:** Jakarta Validation  
- **Other:** Lombok, Jackson  

---

## 📚 Notable Features
- **Admin CRUD:** Full management of stations, trains, routes, schedules, seats, and users  
- **User Search & Booking:** Users can search for trains and create bookings  
- **Payment Workflow:** Wrapped with `@CustomPaymentAnnotation` for logging & validation  
- **Role-Based Access:** Admin vs. normal user permissions  
- **Pagination & Sorting:** Generic `PageResponse<T>` for large results  
- **Exception Handling:** Centralized `GlobalExceptionHandler` for consistent errors  
- **OpenAPI Documentation:** Swagger UI for all endpoints  

---

## Env variables
```JWT_SECRET=
DB_URL=
DB_USERNAME=
DB_PASSWORD=
```
---

## 🚀 How to Run
```bash
git clone https://github.com/bipinstha07/TrainSystem.git
cd TrainSystem
./mvnw clean install
./mvnw spring-boot:run
```
---
## 📝 Summary
A full-stack backend for railway reservation systems leveraging JWT security, AOP, Lombok, and OpenAPI, with layered architecture that supports easy integration with frontends, payment gateways, and notification services.
