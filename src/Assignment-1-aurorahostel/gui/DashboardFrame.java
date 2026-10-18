package aurorahostel.gui;

import aurorahostel.service.BookingService;
import aurorahostel.util.UIUtils;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final BookingService service;
    public JTabbedPane tabbedPane;

    private MyBookingPanel myBookingPanel;
    private BookingPanel bookingPanel;
    private RoomAvailabilityPanel roomAvailabilityPanel;
    private AdminPanel adminPanel;
    private AuditLogPanel auditLogPanel;

    public DashboardFrame(BookingService service) {
        this.service = service;
        setTitle("Aurora University - Hostel Portal");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.BG_DARK);
        setLayout(new BorderLayout());

        // Modern Top Bar
        JPanel topBar = createTopBar();
        add(topBar, BorderLayout.NORTH);

        // Styled Tabbed Pane
        tabbedPane = createStyledTabbedPane();

        // Create panels
        myBookingPanel = new MyBookingPanel(service, this);
        bookingPanel = new BookingPanel(service, this);
        roomAvailabilityPanel = new RoomAvailabilityPanel(service);
        adminPanel = new AdminPanel(service, this);
        auditLogPanel = new AuditLogPanel(service);

        // Add tabs
        tabbedPane.addTab("📋 My Booking", myBookingPanel);
        tabbedPane.addTab(" Book a Room", bookingPanel);
        tabbedPane.addTab("📊 Room Availability", roomAvailabilityPanel);
        tabbedPane.addTab("⚙️ Admin Tools", adminPanel);
        tabbedPane.addTab("📜 Audit Log", auditLogPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UIUtils.BG_CARD);

        // Left side - Welcome message
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome, " + service.getCurrentUser().getName());
        welcomeLabel.setFont(UIUtils.getSubHeadingFont());
        welcomeLabel.setForeground(UIUtils.TEXT_PRIMARY);
        leftPanel.add(welcomeLabel);

        topBar.add(leftPanel, BorderLayout.WEST);

        // Right side - Logout button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);

        JButton logoutButton = UIUtils.createSecondaryButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 38));
        logoutButton.addActionListener(e -> {
            service.setCurrentUser(null);
            new LoginFrame(service).setVisible(true);
            this.dispose();
        });
        rightPanel.add(logoutButton);

        topBar.add(rightPanel, BorderLayout.EAST);

        // Bottom border
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtils.BORDER_COLOR),
                new EmptyBorder(20, 30, 20, 30)
        ));

        return topBar;
    }

    private JTabbedPane createStyledTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(UIUtils.getBodyFont());
        tabs.setBackground(UIUtils.BG_DARK);
        tabs.setForeground(UIUtils.TEXT_PRIMARY);
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Custom UI for tabs
        tabs.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isSelected) {
                    g2.setColor(UIUtils.PRIMARY);
                } else {
                    g2.setColor(UIUtils.BG_CARD);
                }
                g2.fillRoundRect(x, y + 4, w, h - 4, 8, 8);
                g2.dispose();
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                // No border painting
            }

            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return 45;
            }

            @Override
            protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                // FIXED: Use tabPane.getTitleAt() instead of getTitleAt()
                return metrics.stringWidth(tabPane.getTitleAt(tabIndex)) + 40;
            }


        });

        return tabs;
    }

    public void refreshAllPanels() {
        if (myBookingPanel != null) myBookingPanel.refresh();
        if (roomAvailabilityPanel != null) roomAvailabilityPanel.refresh();
        if (adminPanel != null) adminPanel.refresh();
        if (auditLogPanel != null) auditLogPanel.refresh();
    }
}