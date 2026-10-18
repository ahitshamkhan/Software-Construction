package aurorahostel.model;

public class Room {
    private final String roomId;
    private final RoomType roomType;
    private final int floor;
    private final boolean accessible;
    private RoomStatus status;

    public Room(String roomId, RoomType roomType, int floor, boolean accessible) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.floor = floor;
        this.accessible = accessible;
        this.status = RoomStatus.AVAILABLE;
    }

    public String getRoomId() { return roomId; }
    public RoomType getRoomType() { return roomType; }
    public int getFloor() { return floor; }
    public boolean isAccessible() { return accessible; }
    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }
}