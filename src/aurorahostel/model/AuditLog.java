package aurorahostel.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {
    private final LocalDateTime timestamp;
    private final String event;
    private final String description;

    public AuditLog(String event, String description) {
        this.timestamp = LocalDateTime.now();
        this.event = event;
        this.description = description;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getEvent() { return event; }
    public String getDescription() { return description; }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}