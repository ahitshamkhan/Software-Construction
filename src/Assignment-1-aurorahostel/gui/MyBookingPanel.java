package aurorahostel.gui;

import aurorahostel.model.Booking;
import aurorahostel.model.Room;
import aurorahostel.model.RoomType;
import aurorahostel.model.Student;
import aurorahostel.service.BookingService;
import aurorahostel.util.UIUtils;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDateTime;

public class MyBookingPanel extends JPanel {
    private final BookingService service;
    private final DashboardFrame parentFrame;
    private JPanel contentPanel;

    public MyBookingPanel(BookingService service, DashboardFrame parentFrame) {
        this.service = service;
        this.parentFrame = parentFrame;
        setBackground(UIUtils.BG_DARK);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("My Current Reservation");
        titleLabel.setFont(UIUtils.getHeadingFont());
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);
        add(Box.createVerticalStrut(20), BorderLayout.NORTH);

        // Content area
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UIUtils.BG_DARK);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        contentPanel.removeAll();
        Student current = service.getCurrentUser();
        Booking activeBooking = service.getActiveBooking(current.getStudentId());

        if (activeBooking != null) {
            Room room = null;
            for (Room r : service.getRooms()) {
                if (r.getRoomId().equals(activeBooking.getRoomId())) {
                    room = r;
                    break;
                }
            }

            // Main booking card
            JPanel card = UIUtils.createCardPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

            // Room info header
            JPanel roomHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            roomHeader.setOpaque(false);

            JLabel roomLabel = new JLabel("Room " + activeBooking.getRoomId());
            roomLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
            roomLabel.setForeground(UIUtils.PRIMARY);
            roomHeader.add(roomLabel);

            // Status badge
            JLabel statusBadge = new JLabel("  CONFIRMED  ");
            statusBadge.setFont(UIUtils.getSmallFont());
            statusBadge.setBackground(UIUtils.SUCCESS);
            statusBadge.setForeground(UIUtils.TEXT_PRIMARY);
            statusBadge.setOpaque(true);
            statusBadge.setBorder(new EmptyBorder(5, 10, 5, 10));
            roomHeader.add(Box.createHorizontalStrut(15));
            roomHeader.add(statusBadge);

            card.add(roomHeader);
            card.add(Box.createVerticalStrut(15));

            // Room details
            JLabel typeLabel = new JLabel(room.getRoomType().getDisplayName() + "  •  Floor " + room.getFloor() + "  •  " + (room.isAccessible() ? "Accessible" : "Standard"));
            typeLabel.setFont(UIUtils.getBodyFont());
            typeLabel.setForeground(UIUtils.TEXT_SECONDARY);
            card.add(typeLabel);
            card.add(Box.createVerticalStrut(20));

            // Divider
            JPanel divider = new JPanel();
            divider.setPreferredSize(new Dimension(card.getWidth(), 1));
            divider.setBackground(UIUtils.BORDER_COLOR);
            divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            card.add(divider);
            card.add(Box.createVerticalStrut(20));

            // Deposit status
            if (activeBooking.isDepositPaid()) {
                JPanel paidPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
                paidPanel.setOpaque(false);

                JLabel paidIcon = new JLabel("✓");
                paidIcon.setFont(new Font("Segoe UI", Font.BOLD, 20));
                paidIcon.setForeground(UIUtils.SUCCESS);
                paidPanel.add(paidIcon);

                JLabel paidLabel = new JLabel("Deposit Paid - Ready for Move-in");
                paidLabel.setFont(UIUtils.getBodyFont());
                paidLabel.setForeground(UIUtils.SUCCESS);
                paidPanel.add(paidLabel);

                card.add(paidPanel);
            } else {
                JLabel deadlineLabel = new JLabel("⚠ Deposit Deadline: " + activeBooking.getFormattedDeadline());
                deadlineLabel.setFont(UIUtils.getBodyFont());
                deadlineLabel.setForeground(UIUtils.WARNING);
                card.add(deadlineLabel);
                card.add(Box.createVerticalStrut(15));

                JButton payBtn = UIUtils.createPrimaryButton(" Pay Deposit Now");
                payBtn.addActionListener(e -> payDeposit(activeBooking));
                card.add(payBtn);
            }

            contentPanel.add(card);
            contentPanel.add(Box.createVerticalStrut(20));

            // Action buttons panel
            JPanel actionPanel = UIUtils.createCardPanel();
            actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

            JButton cancelBtn = UIUtils.createDangerButton("✕ Cancel Booking");
            cancelBtn.addActionListener(e -> cancelBooking(activeBooking));
            actionPanel.add(cancelBtn);

            JButton switchBtn = UIUtils.createSecondaryButton("⇄ Switch Room Type");
            switchBtn.addActionListener(e -> switchRoom(activeBooking));
            actionPanel.add(switchBtn);

            contentPanel.add(actionPanel);
        } else {
            // Empty state
            JPanel emptyCard = UIUtils.createCardPanel();
            emptyCard.setLayout(new BoxLayout(emptyCard, BoxLayout.Y_AXIS));
            emptyCard.setPreferredSize(new Dimension(600, 200));

            JLabel emptyIcon = new JLabel("📭");
            emptyIcon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
            emptyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyCard.add(emptyIcon);
            emptyCard.add(Box.createVerticalStrut(15));

            JLabel emptyLabel = new JLabel("No Active Reservation");
            emptyLabel.setFont(UIUtils.getSubHeadingFont());
            emptyLabel.setForeground(UIUtils.TEXT_PRIMARY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyCard.add(emptyLabel);
            emptyCard.add(Box.createVerticalStrut(10));

            JLabel emptySubLabel = new JLabel("Book a room to get started");
            emptySubLabel.setFont(UIUtils.getBodyFont());
            emptySubLabel.setForeground(UIUtils.TEXT_SECONDARY);
            emptySubLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyCard.add(emptySubLabel);

            contentPanel.add(emptyCard);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void payDeposit(Booking booking) {
        boolean success = service.makeDepositPayment(booking.getBookingReference(), LocalDateTime.now());
        if (success) {
            JOptionPane.showMessageDialog(this, "✓ Deposit paid successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "✗ Payment failed. Deadline may have passed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelBooking(Booking booking) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            service.cancelBooking(service.getCurrentUser().getStudentId(), booking.getBookingReference(), LocalDateTime.now());
            JOptionPane.showMessageDialog(this, "✓ Booking cancelled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refresh();
        }
    }

    private void switchRoom(Booking booking) {
        RoomType[] types = RoomType.values();
        RoomType newType = (RoomType) JOptionPane.showInputDialog(this, "Select new room type:", "Switch Room",
                JOptionPane.QUESTION_MESSAGE, null, types, types[0]);

        if (newType != null) {
            boolean success = service.switchRoom(service.getCurrentUser().getStudentId(), booking.getBookingReference(), newType, LocalDateTime.now());
            if (success) {
                JOptionPane.showMessageDialog(this, "✓ Room switched successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refresh();
            } else {
                JOptionPane.showMessageDialog(this, "✗ No available rooms of that type.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}