# Change Impact Report

## Overview

Evolution 3 has seen further enhancement with the integration of JavaFX, React, and Spring Boot technologies to modernize the user experience and backend services. These changes were strategically implemented to diversify the user interfaces and optimize backend performance, maintaining the client/server architecture while improving scalability and maintainability.

## New Changes

### JavaFX for application for administration

1. We Developed a robust desktop application using JavaFX, offering an enriched interface and improved performance for administrative tasks. 

2. We merged user and course administration into a single JavaFX application, simplifying and improving the administrative workflow.

### React for Web Services

1. We Transitioned our client-server architecture to a React-based architecture for a more dynamic and responsive web interface, enabling real-time interactions and updates.

2. We added functionality to restrict student check-ins to specified geographic locations, ensuring that attendance is recorded only within predefined areas.

3. We introduced time constraints on attendance, allowing students to check in only during designated time windows. This feature ensures that attendance aligns with the scheduled class times.

### Spring Boot for Backend Services

1. We transitioned our datebase DAO to using Spring Boot entities for streamlined database interactions and efficient CRUD operations.

2. We Integrated Data Transfer Objects (DTOs) to optimize data transfers and enhance security by minimizing direct exposure of database entities.

3. We implemented JSON Web Tokens (JWT) to secure check-ins and authenticate users, ensuring robust security across our services.

4. We have comprehensively restructured the backend to leverage Spring Boot's auto-configuration and ORM capabilities, significantly improving development speed and system reliability.

5. We changed the method of recording attendance from professors manually entering all student attendance information to a system where professors initiate the check-in process and students independently check in via the web interface. This shift has significantly accelerated the efficiency of the check-in process.

### Microservices Architecture

1. Communicaion between services: We separated frontend and backend to decouple services and use GET and POST method to do the data transmission.

2. Data transmission format: We use JSON to send and retrieve data.

3. JavaFX is another service in our project and it can be deployed separately.

## Impact on Previous Solutions

1. The development of a new JavaFX desktop application has replaced older, less efficient administrative tools. This has provided administrators with a more robust, faster, and visually appealing interface, which facilitates easier management of administrative tasks.

2. By merging user and course administration into one application, we have eliminated redundant systems and streamlined administrative processes. This consolidation improved the efficiency of managing administrative data.

3. Transitioning to a React-based architecture has dramatically enhanced the dynamic responsiveness of our web interface. This has led to a more engaging and interactive experience for users.

4. The new location-based and time-limited check-in functionalities have transformed how attendance is managed. These features ensure that students can only check in within specific geographic and temporal boundaries, aligning attendance more closely with actual classroom activities and schedules.

5. Transitioning to Spring Boot entities for database management has simplified data handling across our applications. This has led to faster and more reliable database operations, directly impacting the performance and scalability of our backend services.

6. The integration of DTOs has optimized the way data is transferred across systems, minimizing the exposure of sensitive data and reducing the risk of data leaks. This change has significantly improved data security.

7. The implementation of JWT for user authentication has heightened security measures, ensuring that only authorized users can access and perform actions within our systems. This is particularly crucial for maintaining the integrity and confidentiality of user and administrative data.

8. The comprehensive restructuring of the backend to utilize Spring Boot's features like auto-configuration and ORM has not only reduced development times but also enhanced the overall reliability and maintainability of the system. These improvements have made our backend services more robust and adaptable to changes.

## Design Principles Employed

### Single Responsibility Principle

a. Each component of our system is designed to handle a specific function, ensuring clarity and ease of management. For instance, we have dedicated classes for handling specific types of data operations, which simplifies the database interaction and maintenance. 

### Low Coupling and High Cohesion

a. Within the JavaFX administration application, all components are tightly integrated to support comprehensive administrative tasks, ensuring that related functionalities are grouped together.

b. Modifications in the React-based web services for handling attendance do not affect the backend structures or the JavaFX administration application, allowing independent updates and maintenance without widespread system impacts.

### Open/Closed Principle

a. Interfaces in the React web application are designed to accommodate additional features like real-time attendance updates without altering existing code.

b. The backend can accommodate new business rules or data entities without requiring significant changes to the existing codebase, facilitated by Spring Boot's configurable modules.

### Dependency Injection

a.  Spring Boot's IoC (Inversion of Control) container manages service dependencies, which allows easy integration and swapping of components without manual dependency configuration.

### Don’t Repeat Yourself (DRY)

a. We adhere to the DRY principle to minimize redundancy across our project. Common functionalities, such as authentication mechanisms using JWT and hash functions in login processes, are abstracted into shared services or utilities that are used across different modules of the application, reducing code duplication and ensuring consistency.

## Design Patterns Employed

### Model-View-Controller (MVC) Pattern

We have optimized the MVC pattern to clearly separate concerns across our applications:

a. Model: Centralizes all business logic and data interactions, particularly using Spring Boot entities for backend operations and shared model directories for consistent data handling.  
b. View: React-based interfaces handle all user-facing components, dynamically updating to reflect changes in the model without requiring page reloads.  
c. Controller: Controllers are responsible for handling user inputs and converting them into actions to be performed by the model or updates to be displayed by the view.

### Singleton Pattern

Singleton patterns govern the creation of configuration objects in our system, ensuring that there is a single instance of configuration settings and services like JWT token management.

### Observer Pattern

Our React components utilize the observer pattern to stay responsive and updated. React components automatically update the user interface in response to state changes, ensuring the UI is always synchronized with the underlying data without manual intervention.
