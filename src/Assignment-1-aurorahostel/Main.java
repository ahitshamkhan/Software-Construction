package aurorahostel;

import aurorahostel.gui.LoginFrame;
import aurorahostel.model.Room;
import aurorahostel.model.RoomType;
import aurorahostel.model.Student;
import aurorahostel.service.BookingService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            BookingService service = new BookingService();
            initializeTestData(service);

            LoginFrame loginFrame = new LoginFrame(service);
            loginFrame.setVisible(true);
        });
    }

    private static void initializeTestData(BookingService service) {
        // Students
        service.addStudent(new Student("S001", "Ali Khan", false));
        service.addStudent(new Student("S002", "Ahmed Hassan", false));
        service.addStudent(new Student("S003", "Usman Malik", false));
        service.addStudent(new Student("S004", "Sara Ahmed", true));
        service.addStudent(new Student("S005", "Hafsa Al-Mansoor", false));

        // Rooms
        service.addRoom(new Room("S-101", RoomType.SINGLE, 1, true));
        service.addRoom(new Room("S-102", RoomType.SINGLE, 1, true));
        service.addRoom(new Room("S-103", RoomType.SINGLE, 1, false));
        service.addRoom(new Room("D-201", RoomType.DOUBLE, 2, false));
        service.addRoom(new Room("D-202", RoomType.DOUBLE, 2, true));
        service.addRoom(new Room("D-203", RoomType.DOUBLE, 2, false));
        service.addRoom(new Room("SH-301", RoomType.SHARED, 3, false));
        service.addRoom(new Room("SH-302", RoomType.SHARED, 3, false));
        service.addRoom(new Room("SH-303", RoomType.SHARED, 3, true));
    }
}