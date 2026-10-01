package aurorahostel.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private final String bookingReference;
    private final String studentId;
    private String roomId;
    private BookingStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime depositDeadline;
    private LocalDateTime depositPaidAt;
    private LocalDateTime cancelledAt;

    public Booking(String bookingReference, String studentId, String roomId,
                   LocalDateTime createdAt, LocalDateTime depositDeadline) {
        this.bookingReference = bookingReference;
        this.studentId = studentId;
        this.roomId = roomId;
        this.createdAt = createdAt;
        this.depositDeadline = depositDeadline;
        this.status = BookingStatus.ACTIVE;
    }

    public String getBookingReference() { return bookingReference; }
    public String getStudentId() { return studentId; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getDepositDeadline() { return depositDeadline; }
    public LocalDateTime getDepositPaidAt() { return depositPaidAt; }
    public void setDepositPaidAt(LocalDateTime depositPaidAt) { this.depositPaidAt = depositPaidAt; }
    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; }
    public boolean isDepositPaid() { return depositPaidAt != null; }

    public String getFormattedDeadline() {
        return depositDeadline.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
    }
}