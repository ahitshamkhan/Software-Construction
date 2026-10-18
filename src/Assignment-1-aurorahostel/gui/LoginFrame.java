package aurorahostel.gui;

import aurorahostel.model.Student;
import aurorahostel.service.BookingService;
import aurorahostel.util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final BookingService service;
    private JComboBox<Student> studentComboBox;

    public LoginFrame(BookingService service) {
        this.service = service;
        setTitle("Aurora University - Hostel Portal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.BG_DARK);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Aurora University Hostel Portal");
        titleLabel.setFont(UIUtils.getHeadingFont());
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Student Login");
        subtitleLabel.setFont(UIUtils.getSubHeadingFont());
        subtitleLabel.setForeground(UIUtils.TEXT_SECONDARY);
        gbc.gridy = 1;
        add(subtitleLabel, gbc);

        JLabel selectLabel = new JLabel("Select Student:");
        selectLabel.setFont(UIUtils.getBodyFont());
        selectLabel.setForeground(UIUtils.TEXT_PRIMARY);
        gbc.gridy = 2; gbc.gridwidth = 1;
        add(selectLabel, gbc);

        studentComboBox = new JComboBox<>();
        studentComboBox.setFont(UIUtils.getBodyFont());
        studentComboBox.setPreferredSize(new Dimension(250, 35));
        studentComboBox.setBackground(UIUtils.BG_CARD);
        studentComboBox.setForeground(UIUtils.TEXT_PRIMARY);
        gbc.gridx = 1;
        add(studentComboBox, gbc);

        JButton loginButton = UIUtils.createPrimaryButton("Login to Dashboard");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.insets = new Insets(30, 10, 10, 10);
        add(loginButton, gbc);

        loginButton.addActionListener(e -> login());
        populateStudents();
    }

    private void populateStudents() {
        for (Student student : service.getStudents()) {
            studentComboBox.addItem(student);
        }
    }

    private void login() {
        Student selected = (Student) studentComboBox.getSelectedItem();
        if (selected != null) {
            service.setCurrentUser(selected);
            SwingUtilities.invokeLater(() -> {
                new DashboardFrame(service).setVisible(true);
                this.dispose();
            });
        }
    }
}