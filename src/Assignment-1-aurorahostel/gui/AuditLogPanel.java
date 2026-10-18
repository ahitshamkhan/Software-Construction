package aurorahostel.gui;

import aurorahostel.model.AuditLog;
import aurorahostel.service.BookingService;
import aurorahostel.util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class AuditLogPanel extends JPanel {
    private final BookingService service;
    private JTextArea logArea;

    public AuditLogPanel(BookingService service) {
        this.service = service;
        setBackground(UIUtils.BG_DARK);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("System Audit Log");
        titleLabel.setFont(UIUtils.getHeadingFont());
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        add(titleLabel, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        logArea.setBackground(UIUtils.BG_CARD);
        logArea.setForeground(UIUtils.TEXT_SECONDARY);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        StringBuilder sb = new StringBuilder();
        for (AuditLog log : service.getAuditLogs()) {
            sb.append("[").append(log.getFormattedTimestamp()).append("] ")
                    .append(String.format("%-18s", log.getEvent()))
                    .append(" | ").append(log.getDescription()).append("\n");
        }
        logArea.setText(sb.toString());
        logArea.setCaretPosition(0);
    }
}