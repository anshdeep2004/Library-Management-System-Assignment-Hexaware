# Library Management System

This project is a Java-based library management application built using Hibernate ORM and MySQL. It demonstrates how a simple console application can be structured with entities, DAO classes, services, controllers, and database interactions.

## What I implemented

The project covers the required tasks 1, 2, 3, 6, 7, and 8. The application is built around a menu-driven console interface where users can manage books and event registrations.

## Task 1: Basic CRUD operations

I created the main entity model for books and implemented the basic CRUD operations:
- insert a book
- get a book by ID
- get all books
- update a book
- delete a book

These operations were implemented through the DAO layer using Hibernate sessions and transactions. The service layer handles the business logic, while the controller layer connects the user input to the database operations.

## Task 2: Relationship-based queries

I implemented queries to fetch books based on relationships:
- books written by a specific author
- books currently borrowed by a specific member

These were done using HQL/JPQL with Hibernate, showing how to query related entities through their association fields.

## Task 3: Dynamic filtering

I added a feature to filter books by optional genre and/or book status. The filter works when:
- only genre is provided
- only status is provided
- both are provided
- neither is provided

This was implemented using the Hibernate Criteria API, which allows dynamic query building without hardcoding all possible combinations.

## Tasks 6, 7, and 8: Event registration features

I also implemented the event-related tasks:
- Task 6: fetch all registrations for a given event ID
- Task 7: find members who registered for an event but did not attend
- Task 8: convert event registration data into a DTO using a mapper class for cleaner data transfer

These were implemented with JPQL queries and a dedicated DTO/mapping layer.

## How the project is structured

- Model classes define the database entities
- DAO classes contain the Hibernate database logic
- Service classes handle the application logic
- Controller classes connect the console menu to the services
- Hibernate configuration connects the Java application to the MySQL database

## Technologies used

- Java 17
- Hibernate ORM
- MySQL
- Maven

## Setup and run

1. Make sure MySQL is running and a database named libraryDB exists.
2. Update the database credentials in the Hibernate configuration file if needed.
3. Run the main class from src/main/java/com/library/LibraryManagementApp.java.

This project is a practical example of using Hibernate for CRUD, relationship queries, dynamic filtering, and DTO-based data handling in a real Java application.
