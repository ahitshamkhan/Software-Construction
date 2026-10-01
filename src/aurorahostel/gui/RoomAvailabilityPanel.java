package aurorahostel.gui;

import aurorahostel.model.Room;
import aurorahostel.service.BookingService;
import aurorahostel.util.UIUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RoomAvailabilityPanel extends JPanel {
    private final BookingService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public RoomAvailabilityPanel(BookingService service) {
        this.service = service;
        setBackground(UIUtils.BG_DARK);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Room Availability");
        titleLabel.setFont(UIUtils.getHeadingFont());
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Room ID", "Type", "Floor", "Accessible", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(UIUtils.getBodyFont());
        table.setBackground(UIUtils.BG_CARD);
        table.setForeground(UIUtils.TEXT_PRIMARY);
        table.setGridColor(UIUtils.BG_DARK);
        table.setRowHeight(30);
        table.getTableHeader().setFont(UIUtils.getSubHeadingFont());
        table.getTableHeader().setBackground(UIUtils.PRIMARY);
        table.getTableHeader().setForeground(UIUtils.TEXT_PRIMARY);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Room room : service.getRooms()) {
            tableModel.addRow(new Object[]{
                    room.getRoomId(),
                    room.getRoomType().getDisplayName(),
                    room.getFloor(),
                    room.isAccessible() ? "Yes" : "No",
                    room.getStatus().toString()
            });
        }
    }
}