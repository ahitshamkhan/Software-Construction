package aurorahostel.gui;

import aurorahostel.model.Booking;
import aurorahostel.model.RoomType;
import aurorahostel.model.Student;
import aurorahostel.service.BookingService;
import aurorahostel.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class BookingPanel extends JPanel {
    private final BookingService service;
    private final DashboardFrame parentFrame;
    private JComboBox<RoomType> roomTypeComboBox;

    public BookingPanel(BookingService service, DashboardFrame parentFrame) {
        this.service = service;
        this.parentFrame = parentFrame;
        setBackground(UIUtils.BG_DARK);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find and Book a Room");
        titleLabel.setFont(UIUtils.getHeadingFont());
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel typeLabel = new JLabel("Select Room Type:");
        typeLabel.setFont(UIUtils.getBodyFont());
        typeLabel.setForeground(UIUtils.TEXT_PRIMARY);
        gbc.gridy = 1; gbc.gridwidth = 1;
        add(typeLabel, gbc);

        roomTypeComboBox = new JComboBox<>(RoomType.values());
        roomTypeComboBox.setFont(UIUtils.getBodyFont());
        roomTypeComboBox.setPreferredSize(new Dimension(250, 35));
        roomTypeComboBox.setBackground(UIUtils.BG_CARD);
        roomTypeComboBox.setForeground(UIUtils.TEXT_PRIMARY);
        gbc.gridx = 1;
        add(roomTypeComboBox, gbc);

        JButton bookButton = UIUtils.createPrimaryButton("Book Room");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.insets = new Insets(30, 10, 10, 10);
        add(bookButton, gbc);

        bookButton.addActionListener(e -> bookRoom());
    }

    private void bookRoom() {
        Student current = service.getCurrentUser();
        RoomType selectedType = (RoomType) roomTypeComboBox.getSelectedItem();

        Booking booking = service.bookRoom(current.getStudentId(), selectedType, LocalDateTime.now());

        if (booking != null) {
            if (booking == service.getActiveBooking(current.getStudentId())) {
                JOptionPane.showMessageDialog(this,
                        "Room booked successfully!\nReference: " + booking.getBookingReference() +
                                "\nRoom: " + booking.getRoomId() + "\n\nPlease pay deposit within 48 hours.",
                        "Booking Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "You already have an active booking.\nReference: " + booking.getBookingReference(),
                        "Existing Booking", JOptionPane.INFORMATION_MESSAGE);
            }
            parentFrame.tabbedPane.setSelectedIndex(0);
            parentFrame.refreshAllPanels();
        } else {
            JOptionPane.showMessageDialog(this, "No suitable room available for your requirements.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}