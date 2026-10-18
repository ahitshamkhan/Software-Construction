# Software Construction Assignments

This repository contains Java implementations completed for the **SEN-311 Software Construction** course at **Bahria University Islamabad**.

The work was completed by:

- **Ahitsham Khan**
- **Ali Huzaifa**

## Repository Contents

### Assignment 1 — Aurora University Hostel Booking System

A Java Swing desktop application that redesigns the Aurora University hostel booking case study. The system demonstrates practical software construction principles while enforcing realistic booking rules.

#### Implemented features

- Student login and authentication
- Room availability management
- One active booking per student
- Prevention of duplicate and conflicting bookings
- Accessibility-aware room allocation
- Deposit payment tracking
- 48-hour unpaid booking expiration
- Booking cancellation
- Room switching
- Timestamped audit logging
- Admin time simulation for testing expiration behavior
- Handling of invalid or interrupted booking requests

#### Architecture

The application uses a clear separation of responsibilities:

- **Model:** Students, rooms, bookings, booking statuses, room types, and audit logs
- **Service:** Booking rules, validation, room allocation, payment handling, cancellation, switching, and expiration logic
- **GUI:** Java Swing screens for login, booking, room availability, administration, and audit logs
- **Utilities:** Shared user-interface helper methods

### Assignment 2 — Music Organizer

A Java application based on a music-organizer system. It manages music files and tracks and demonstrates collection handling, searching, playback sequencing, ratings, and removal operations.

#### Implemented features

- Add and count music files
- List files with indexes
- Search for files by name
- Validate invalid file indexes
- Create and manage tracks
- Store track title, artist, audio path, and rating
- Play tracks sequentially by artist
- Remove tracks by title
- Use a sample WAV audio file for testing

## Software Construction Principles

The implementations demonstrate the following principles:

- **Minimizing Complexity:** Responsibilities are divided into focused model, service, GUI, and utility classes.
- **Anticipating Change:** Booking rules and domain concepts are represented through reusable classes and enums such as `RoomType`, `RoomStatus`, and `BookingStatus`.
- **Reuse:** Common operations are centralized in service and organizer classes instead of being duplicated across user-interface code.
- **Constructing for Verification:** Validation, deterministic time parameters, audit logs, and executable scenarios make important behaviors easier to verify.
- **Standards in Construction:** The code follows package organization, meaningful class names, encapsulation, and consistent Java conventions.

## Technology Stack

| Component | Technology |
| --- | --- |
| Language | Java 11 or later |
| GUI | Java Swing |
| Collections | Java Standard Library collections |
| Date and time | `java.time.LocalDateTime` |
| Data storage | In-memory collections |
| Audio testing | WAV audio file through Java file/audio support |
| External dependencies | None |

## Project Structure

```text
Software-Construction/
├── README.md
└── src/
    ├── Assignment-1-aurorahostel/
    │   ├── Main.java
    │   ├── gui/
    │   │   ├── AdminPanel.java
    │   │   ├── AuditLogPanel.java
    │   │   ├── BookingPanel.java
    │   │   ├── DashboardFrame.java
    │   │   ├── LoginFrame.java
    │   │   ├── MyBookingPanel.java
    │   │   └── RoomAvailabilityPanel.java
    │   ├── model/
    │   │   ├── AuditLog.java
    │   │   ├── Booking.java
    │   │   ├── BookingStatus.java
    │   │   ├── Room.java
    │   │   ├── RoomStatus.java
    │   │   ├── RoomType.java
    │   │   └── Student.java
    │   ├── service/
    │   │   └── BookingService.java
    │   └── util/
    │       └── UIUtils.java
    └── Assignment-2-Music-Organizer/
        ├── Main.java
        ├── MusicOrganizer.java
        ├── MusicOrganizerGUI.java
        ├── MusicPlayer.java
        ├── Track.java
        └── audio/
            └── sample.wav
```

## How to Run

### Assignment 1: Hostel Booking System

1. Open the repository in IntelliJ IDEA, Eclipse, or another Java IDE.
2. Configure a Java 11 or later JDK.
3. Mark `src/Assignment-1-aurorahostel` as a source root, or configure the source path according to your IDE.
4. Run `src/Assignment-1-aurorahostel/Main.java`.
5. Use the sample students initialized by `Main.java` to explore the booking workflow.

### Assignment 2: Music Organizer

1. Configure a Java 11 or later JDK.
2. Open the `src/Assignment-2-Music-Organizer` directory as a source directory or project module.
3. Ensure the relative path to `audio/sample.wav` is available from the project working directory.
4. Run `src/Assignment-2-Music-Organizer/Main.java`.

## Verification Scenarios

The hostel booking implementation supports scenarios such as:

1. Successful room booking
2. Duplicate booking prevention
3. Room availability conflict handling
4. Deposit payment before the deadline
5. Rejection of late deposit payment
6. Booking cancellation and room release
7. Room switching
8. Accessibility-based allocation
9. Automatic expiration of unpaid bookings
10. Audit logging of important events

The music organizer implementation demonstrates:

1. File collection and counting
2. File search
3. Invalid index handling
4. Track collection management
5. Track rating
6. Sequential playback by artist
7. Track removal

## Limitations

- Data is stored in memory and is lost when the program terminates.
- The sample applications do not use a database or external services.
- Authentication is intended for academic demonstration rather than production security.
- The applications are educational implementations and are not intended to be deployed as production hostel or music-management systems without additional testing and persistence support.

## Future Improvements

- Add automated unit tests with JUnit.
- Introduce persistent storage using a database or file-based repository.
- Add stronger authentication and authorization.
- Improve exception handling and user-facing error messages.
- Add build configuration with Maven or Gradle.
- Add continuous integration for compilation and testing.
- Improve accessibility and responsive behavior in the Swing interface.
