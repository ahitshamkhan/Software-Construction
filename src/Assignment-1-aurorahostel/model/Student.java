package aurorahostel.model;

public class Student {
    private final String studentId;
    private final String name;
    private final boolean accessibilityRequired;

    public Student(String studentId, String name, boolean accessibilityRequired) {
        this.studentId = studentId;
        this.name = name;
        this.accessibilityRequired = accessibilityRequired;
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public boolean isAccessibilityRequired() { return accessibilityRequired; }

    @Override
    public String toString() { return studentId + " - " + name; }
}