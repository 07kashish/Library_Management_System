# Library Management System

This project is a Java 17 Library Management System built to demonstrate OOP concepts, SOLID principles, and common design patterns without introducing database or API complexity.

## Features

- The Book management: add, update, remove, and search books by title, author, or ISBN.
- Patron management: register patrons, update details, and view borrowing history.
- Lending process: checkout and return books with loan tracking.
- Inventory management: track available, borrowed, reserved, and in-transfer books.
- Multi-branch support: maintain branches and transfer available books between branches.
- Reservation system: allow patrons to reserve checked-out books and receive availability notifications.
- Recommendation system: suggest available books based on borrowing history and genre preferences.

## Design Overview

### OOP Concepts

- Encapsulation: domain objects own and validate their state.
- Abstraction: repositories and services are expressed as interfaces.
- Polymorphism: search and recommendation behaviors are swapped through strategy interfaces.
- Composition over inheritance: services collaborate with repositories and strategies instead of relying on deep inheritance trees.

### SOLID Principles

- Single Responsibility: catalog, patron, circulation, branch, and recommendation flows are split into focused services.
- Open/Closed: new search types or recommendation algorithms can be added without changing service consumers.
- Liskov Substitution: repository and strategy implementations can be replaced behind their interfaces.
- Interface Segregation: small interfaces keep consumers dependent only on the methods they use.
- Dependency Inversion: services depend on abstractions such as repositories and strategies rather than concrete storage.

### Design Patterns

- Strategy Pattern: `BookSearchStrategy` and `RecommendationStrategy` support pluggable algorithms.
- Observer Pattern: reservation notifications use `ReservationNotificationSubject` with `LibraryEventObserver` subscribers.

## Build and Run

```bash
javac -d out (Get-ChildItem -Recurse -Path src/main/java -Filter *.java | ForEach-Object { $_.FullName })
java -cp out com.airtribe.librarymanagementsystem.App
java -cp out com.airtribe.librarymanagementsystem.LibraryManagementSystemVerifier
```

## Notes

- Storage is intentionally in-memory to keep focus on design and code quality.
- Logging uses Java's built-in `java.util.logging` framework for important business events.
