package aurorahostel.service;

import aurorahostel.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingService {
    private final List<Student> students = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final List<AuditLog> auditLogs = new ArrayList<>();

    private Student currentUser;

    public void addStudent(Student student) { students.add(student); }
    public void addRoom(Room room) { rooms.add(room); }
    public void setCurrentUser(Student student) { this.currentUser = student; }
    public Student getCurrentUser() { return currentUser; }

    public List<Student> getStudents() { return new ArrayList<>(students); }
    public List<Room> getRooms() { return new ArrayList<>(rooms); }
    public List<Booking> getBookings() { return new ArrayList<>(bookings); }
    public List<AuditLog> getAuditLogs() { return new ArrayList<>(auditLogs); }

    private void log(String event, String description) {
        auditLogs.add(new AuditLog(event, description));
    }

    public Booking getActiveBooking(String studentId) {
        for (Booking booking : bookings) {
            if (booking.getStudentId().equals(studentId) && booking.getStatus() == BookingStatus.ACTIVE) {
                return booking;
            }
        }
        return null;
    }

    private Student findStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) return student;
        }
        return null;
    }

    private Room findRoom(String roomId) {
        for (Room room : rooms) {
            if (room.getRoomId().equals(roomId)) return room;
        }
        return null;
    }

    public Booking bookRoom(String studentId, RoomType requestedType, LocalDateTime now) {
        Student student = findStudent(studentId);
        if (student == null) {
            log("BOOKING_REJECTED", "Unknown student: " + studentId);
            return null;
        }

        Booking existing = getActiveBooking(studentId);
        if (existing != null) {
            log("DUPLICATE_REQUEST", "Existing booking returned: " + existing.getBookingReference());
            return existing;
        }

        Room selected = null;
        for (Room room : rooms) {
            boolean typeMatches = room.getRoomType() == requestedType;
            boolean available = room.getStatus() == RoomStatus.AVAILABLE;
            boolean accessibilityMatches = !student.isAccessibilityRequired() || room.isAccessible();

            if (typeMatches && available && accessibilityMatches) {
                selected = room;
                break;
            }
        }

        if (selected == null) {
            log("BOOKING_REJECTED", "No suitable room available for " + studentId);
            return null;
        }

        selected.setStatus(RoomStatus.BOOKED);
        String reference = "AUR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDateTime deadline = now.plusHours(48);

        Booking booking = new Booking(reference, studentId, selected.getRoomId(), now, deadline);
        bookings.add(booking);
        log("BOOKED", "Booking " + reference + " created for student " + studentId + " in room " + selected.getRoomId());

        return booking;
    }

    public boolean makeDepositPayment(String bookingReference, LocalDateTime paymentTime) {
        for (Booking booking : bookings) {
            if (booking.getBookingReference().equals(bookingReference) && booking.getStatus() == BookingStatus.ACTIVE) {
                if (paymentTime.isAfter(booking.getDepositDeadline())) {
                    log("PAYMENT_REJECTED", "Deadline passed for " + bookingReference);
                    return false;
                }
                booking.setDepositPaidAt(paymentTime);
                log("DEPOSIT_PAID", "Deposit paid for " + bookingReference);
                return true;
            }
        }
        return false;
    }

    public boolean cancelBooking(String studentId, String bookingReference, LocalDateTime now) {
        for (Booking booking : bookings) {
            if (booking.getBookingReference().equals(bookingReference) &&
                    booking.getStudentId().equals(studentId) &&
                    booking.getStatus() == BookingStatus.ACTIVE) {

                Room room = findRoom(booking.getRoomId());
                if (room != null) room.setStatus(RoomStatus.AVAILABLE);

                booking.setStatus(BookingStatus.CANCELLED);
                booking.setCancelledAt(now);
                log("CANCELLED", "Booking " + bookingReference + " cancelled; room " + booking.getRoomId() + " released.");
                return true;
            }
        }
        return false;
    }

    public boolean switchRoom(String studentId, String bookingReference, RoomType newType, LocalDateTime now) {
        Booking booking = getActiveBooking(studentId);
        if (booking == null || !booking.getBookingReference().equals(bookingReference)) return false;

        Student student = findStudent(studentId);
        Room newRoom = null;

        for (Room room : rooms) {
            boolean sameOldRoom = room.getRoomId().equals(booking.getRoomId());
            boolean typeMatches = room.getRoomType() == newType;
            boolean available = room.getStatus() == RoomStatus.AVAILABLE;
            boolean accessibilityMatches = !student.isAccessibilityRequired() || room.isAccessible();

            if (!sameOldRoom && typeMatches && available && accessibilityMatches) {
                newRoom = room;
                break;
            }
        }

        if (newRoom == null) {
            log("SWITCH_REJECTED", "No new room available for " + bookingReference);
            return false;
        }

        newRoom.setStatus(RoomStatus.BOOKED);
        Room oldRoom = findRoom(booking.getRoomId());
        String oldRoomId = booking.getRoomId();

        booking.setRoomId(newRoom.getRoomId());
        if (oldRoom != null) oldRoom.setStatus(RoomStatus.AVAILABLE);

        log("SWITCHED", "Booking " + bookingReference + " moved from " + oldRoomId + " to " + newRoom.getRoomId());
        return true;
    }

    public void releaseExpiredBookings(LocalDateTime currentTime) {
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.ACTIVE &&
                    !booking.isDepositPaid() &&
                    currentTime.isAfter(booking.getDepositDeadline())) {

                Room room = findRoom(booking.getRoomId());
                if (room != null) room.setStatus(RoomStatus.AVAILABLE);

                booking.setStatus(BookingStatus.EXPIRED);
                log("EXPIRED", "Booking " + booking.getBookingReference() + " expired; room " + booking.getRoomId() + " released.");
            }
        }
    }
}