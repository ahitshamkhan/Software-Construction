package aurorahostel.gui;

import aurorahostel.service.BookingService;
import aurorahostel.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AdminPanel extends JPanel {
    private final BookingService service;
    private final DashboardFrame parentFrame;

    public AdminPanel(BookingService service, DashboardFrame parentFrame) {
        this.service = service;
        this.parentFrame = parentFrame;
        setBackground(UIUtils.BG_DARK);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Administrative Tools");
        titleLabel.setFont(UIUtils.getHeadingFont());
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel descLabel = new JLabel("Use these tools to simulate system events and maintain data integrity.");
        descLabel.setFont(UIUtils.getBodyFont());
        descLabel.setForeground(UIUtils.TEXT_SECONDARY);
        gbc.gridy = 1;
        add(descLabel, gbc);

        JButton releaseBtn = UIUtils.createPrimaryButton("Release Expired Bookings");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(releaseBtn, gbc);
        releaseBtn.addActionListener(e -> {
            service.releaseExpiredBookings(LocalDateTime.now());
            JOptionPane.showMessageDialog(this, "Expired bookings released.", "Success", JOptionPane.INFORMATION_MESSAGE);
            parentFrame.refreshAllPanels();
        });

        JButton simulateBtn = UIUtils.createSecondaryButton("Simulate 48h Time Jump (For Testing)");
        gbc.gridy = 3;
        add(simulateBtn, gbc);
        simulateBtn.addActionListener(e -> {
            service.releaseExpiredBookings(LocalDateTime.now().plusHours(49));
            JOptionPane.showMessageDialog(this, "Time simulated forward. Expired bookings released.", "Success", JOptionPane.INFORMATION_MESSAGE);
            parentFrame.refreshAllPanels();
        });
    }

    public void refresh() {
        // No internal state to refresh
    }
}