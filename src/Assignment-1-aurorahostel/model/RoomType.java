package aurorahostel.model;

public enum RoomType {
    SINGLE("Single Room", 1),
    DOUBLE("Double Room", 2),
    SHARED("Shared Room", 4);

    private final String displayName;
    private final int capacity;

    RoomType(String displayName, int capacity) {
        this.displayName = displayName;
        this.capacity = capacity;
    }

    public String getDisplayName() { return displayName; }
    public int getCapacity() { return capacity; }
}