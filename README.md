# Aurora University Hostel Booking System

**Course:** SEN-311 Software Construction
**Assignment:** Assignment 1 — Case Study Critique and Redesign
**University:** Bahria University Islamabad

## Assignment Overview

Aurora University Hostel Booking System is a Java Swing desktop application designed to solve the booking problems identified in the Aurora University hostel case study.

The system manages student hostel reservations while enforcing important business rules such as one active reservation per student, room availability, accessibility requirements, 48-hour deposit expiration, cancellation, room switching, and audit logging.

The project uses Java Standard Library components only and follows a Model-Service-GUI architecture.

## Key Features

* Student login and authentication
* One active reservation per student
* Room availability management
* Protection against duplicate and conflicting bookings
* 48-hour unpaid booking expiration
* Accessibility-aware room allocation
* Booking cancellation
* Room switching
* Interrupted request handling
* Timestamped audit logging
* Admin time simulation for testing expiration

## Technology Stack

| Component             | Technology              |
| --------------------- | ----------------------- |
| Language              | Java 11+                |
| GUI                   | Java Swing              |
| Data Storage          | In-memory ArrayList     |
| Date and Time         | java.time.LocalDateTime |
| Architecture          | Model-Service-GUI       |
| External Dependencies | None                    |

## Project Structure

```text
AuroraHostelBooking/
├── README.md
└── src/
    └── aurorahostel/
        ├── Main.java
        ├── model/
        │   ├── Student.java
        │   ├── Room.java
        │   ├── Booking.java
        │   ├── AuditLog.java
        │   ├── RoomType.java
        │   ├── RoomStatus.java
        │   └── BookingStatus.java
        ├── service/
        │   └── BookingService.java
        ├── gui/
        │   ├── LoginFrame.java
        │   ├── DashboardFrame.java
        │   ├── BookingPanel.java
        │   ├── MyBookingPanel.java
        │   ├── RoomAvailabilityPanel.java
        │   ├── AdminPanel.java
        │   └── AuditLogPanel.java
        └── util/
            └── UIUtils.java
```

## Architecture

The application separates responsibilities into three main layers:

**Model:** Represents students, rooms, bookings, and audit logs.

**Service:** Contains the core booking rules and business logic.

**GUI:** Provides the Java Swing interface through which students and administrators interact with the system.

## Software Construction Principles

The implementation demonstrates:

* Minimizing Complexity through separation of responsibilities
* Anticipating Change through flexible booking rules
* Reuse through common service methods and model classes
* Constructing for Verification through validation and audit logging
* Standards in Construction through organized packages and consistent Java conventions

## Testing

The system tests important scenarios including:

1. Successful room booking
2. Multiple booking prevention
3. Last-room booking conflict
4. Deposit payment
5. Booking cancellation
6. Room switching
7. Accessibility-based allocation
8. 48-hour booking expiration
9. Interrupted request handling

## Limitations

The current version uses in-memory data and does not require a database or external services. Data is lost when the application is restarted.

The application is intended as an academic implementation of the Aurora University case study rather than a production hostel management system.

## How to Run

1. Open the project in IntelliJ IDEA.
2. Configure Java JDK 11 or later.
3. Ensure `src` is marked as the Sources Root.
4. Open `Main.java`.
5. Run the `main` method.
