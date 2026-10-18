
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MusicOrganizerGUI extends JFrame {

    // ---------- Theme ----------
    private static final Color BACKGROUND = new Color(15, 23, 42);
    private static final Color SIDEBAR = new Color(11, 18, 32);
    private static final Color CARD = new Color(30, 41, 59);
    private static final Color ACCENT = new Color(56, 189, 248);
    private static final Color TEXT = new Color(241, 245, 249);
    private static final Color MUTED = new Color(148, 163, 184);
    private static final Color BORDER = new Color(51, 65, 85);

    private final MusicOrganizer organizer = new MusicOrganizer();
    private final ArrayList<Track> library = new ArrayList<>();
    private final ArrayList<Track> visibleTracks = new ArrayList<>();

    private DefaultTableModel tableModel;
    private JTable trackTable;
    private JTextField searchField;

    private JLabel totalTracksLabel;
    private JLabel totalArtistsLabel;
    private JLabel statusLabel;

    public MusicOrganizerGUI() {
        setTitle("SoundSpace | Music Organizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        setLayout(new BorderLayout());
        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);

        refreshTable();
    }

    // ---------- Sidebar ----------

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(205, 0));
        sidebar.setBorder(new EmptyBorder(25, 16, 20, 16));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("♫  SoundSpace");
        logo.setFont(new Font("SansSerif", Font.BOLD, 22));
        logo.setForeground(ACCENT);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("YOUR MUSIC LIBRARY");
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        subtitle.setForeground(MUTED);
        subtitle.setBorder(new EmptyBorder(8, 2, 30, 0));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel libraryHeading = new JLabel("LIBRARY");
        libraryHeading.setFont(new Font("SansSerif", Font.BOLD, 11));
        libraryHeading.setForeground(MUTED);
        libraryHeading.setBorder(new EmptyBorder(0, 2, 12, 0));
        libraryHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton libraryButton = createSidebarButton("♫   All Tracks", true);
        JButton addButton = createSidebarButton("+   Add Music", false);
        JButton artistButton = createSidebarButton("♪   Play by Artist", false);

        libraryButton.addActionListener(e -> {
            searchField.setText("");
            statusLabel.setText("Showing your complete music library");
        });

        addButton.addActionListener(e -> addTrackDialog());
        artistButton.addActionListener(e -> playByArtistDialog());

        sidebar.add(logo);
        sidebar.add(subtitle);
        sidebar.add(libraryHeading);
        sidebar.add(libraryButton);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(addButton);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(artistButton);
        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("JAVA MUSIC PLAYER");
        footer.setForeground(MUTED);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 10));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(footer);

        return sidebar;
    }

    private JButton createSidebarButton(String text, boolean selected) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(selected ? ACCENT : TEXT);
        button.setBackground(selected ? CARD : SIDEBAR);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(13, 12, 13, 8));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // ---------- Main content ----------

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(0, 22));
        main.setBackground(BACKGROUND);
        main.setBorder(new EmptyBorder(28, 30, 22, 30));

        JPanel topSection = new JPanel();
        topSection.setOpaque(false);
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("YOUR PERSONAL AUDIO SPACE");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 10));
        eyebrow.setForeground(ACCENT);

        JLabel title = new JLabel("Music Library");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(TEXT);

        JLabel description = new JLabel(
                "Manage your tracks and enjoy your favorite music."
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 12));
        description.setForeground(MUTED);

        titles.add(eyebrow);
        titles.add(Box.createVerticalStrut(5));
        titles.add(title);
        titles.add(Box.createVerticalStrut(5));
        titles.add(description);

        JButton addTrackButton = createButton("+ Add Track", ACCENT, BACKGROUND);
        addTrackButton.addActionListener(e -> addTrackDialog());

        heading.add(titles, BorderLayout.CENTER);
        heading.add(addTrackButton, BorderLayout.EAST);

        JPanel stats = new JPanel(new GridLayout(1, 2, 15, 0));
        stats.setOpaque(false);
        stats.setBorder(new EmptyBorder(22, 0, 0, 0));

        totalTracksLabel = new JLabel("0");
        totalArtistsLabel = new JLabel("0");

        stats.add(createStatCard("TOTAL TRACKS", totalTracksLabel, "♫"));
        stats.add(createStatCard("UNIQUE ARTISTS", totalArtistsLabel, "♪"));

        topSection.add(heading);
        topSection.add(stats);

        JPanel libraryPanel = new JPanel(new BorderLayout(0, 14));
        libraryPanel.setBackground(CARD);
        libraryPanel.setBorder(new EmptyBorder(20, 20, 18, 20));

        JPanel tableHeading = new JPanel(new BorderLayout(10, 0));
        tableHeading.setOpaque(false);

        JLabel tracksTitle = new JLabel("All Tracks");
        tracksTitle.setForeground(TEXT);
        tracksTitle.setFont(new Font("SansSerif", Font.BOLD, 18));

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        searchField.setForeground(TEXT);
        searchField.setBackground(BACKGROUND);
        searchField.setCaretColor(ACCENT);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(9, 12, 9, 12)
        ));
        searchField.setPreferredSize(new Dimension(220, 38));

        tableHeading.add(tracksTitle, BorderLayout.WEST);
        tableHeading.add(searchField, BorderLayout.EAST);

        String[] columns = {"#", "TITLE", "ARTIST", "PLAYS", "RATING"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        trackTable = new JTable(tableModel);
        trackTable.setBackground(CARD);
        trackTable.setForeground(TEXT);
        trackTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        trackTable.setRowHeight(42);
        trackTable.setShowGrid(false);
        trackTable.setIntercellSpacing(new Dimension(0, 0));
        trackTable.setSelectionBackground(new Color(51, 65, 85));
        trackTable.setSelectionForeground(Color.WHITE);
        trackTable.setFillsViewportHeight(true);
        trackTable.setAutoCreateRowSorter(false);

        JTableHeader header = trackTable.getTableHeader();
        header.setBackground(BACKGROUND);
        header.setForeground(MUTED);
        header.setFont(new Font("SansSerif", Font.BOLD, 10));
        header.setPreferredSize(new Dimension(0, 36));
        header.setReorderingAllowed(false);

        trackTable.getColumnModel().getColumn(0).setPreferredWidth(35);
        trackTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        trackTable.getColumnModel().getColumn(2).setPreferredWidth(140);
        trackTable.getColumnModel().getColumn(3).setPreferredWidth(55);
        trackTable.getColumnModel().getColumn(4).setPreferredWidth(65);

        JScrollPane scrollPane = new JScrollPane(trackTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(CARD);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        controls.setOpaque(false);

        JButton playButton = createButton("▶  Play Selected", ACCENT, BACKGROUND);
        JButton artistButton = createButton("♪  Play by Artist", BORDER, TEXT);
        JButton stopButton = createButton("■  Stop", BORDER, TEXT);
        JButton ratingButton = createButton("★  Set Rating", BORDER, TEXT);

        playButton.addActionListener(e -> playSelectedTrack());
        artistButton.addActionListener(e -> playByArtistDialog());
        stopButton.addActionListener(e -> stopPlayback());
        ratingButton.addActionListener(e -> rateSelectedTrack());

        controls.add(playButton);
        controls.add(artistButton);
        controls.add(stopButton);
        controls.add(ratingButton);

        statusLabel = new JLabel("Ready to play your music.");
        statusLabel.setForeground(MUTED);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JPanel bottom = new JPanel(new BorderLayout(0, 12));
        bottom.setOpaque(false);
        bottom.add(controls, BorderLayout.NORTH);
        bottom.add(statusLabel, BorderLayout.SOUTH);

        libraryPanel.add(tableHeading, BorderLayout.NORTH);
        libraryPanel.add(scrollPane, BorderLayout.CENTER);
        libraryPanel.add(bottom, BorderLayout.SOUTH);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                refreshTable();
            }

            public void removeUpdate(DocumentEvent e) {
                refreshTable();
            }

            public void changedUpdate(DocumentEvent e) {
                refreshTable();
            }
        });

        main.add(topSection, BorderLayout.NORTH);
        main.add(libraryPanel, BorderLayout.CENTER);

        return main;
    }

    private JPanel createStatCard(
            String heading, JLabel valueLabel, String symbol) {

        JPanel card = new JPanel(new BorderLayout(12, 8));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel icon = new JLabel(symbol);
        icon.setForeground(ACCENT);
        icon.setFont(new Font("SansSerif", Font.BOLD, 25));

        JLabel caption = new JLabel(heading);
        caption.setForeground(MUTED);
        caption.setFont(new Font("SansSerif", Font.BOLD, 10));

        valueLabel.setForeground(TEXT);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 25));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(caption);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(valueLabel);

        card.add(icon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        background.equals(ACCENT) ? ACCENT : BORDER
                ),
                new EmptyBorder(10, 13, 10, 13)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // ---------- Add music ----------

    private void addTrackDialog() {
        JTextField titleField = new JTextField(22);
        JTextField artistField = new JTextField(22);
        JTextField fileField = new JTextField(22);
        fileField.setEditable(false);

        JButton browseButton = new JButton("Browse...");
        browseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select an audio file");

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                fileField.setText(
                        chooser.getSelectedFile().getAbsolutePath()
                );
            }
        });

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(10, 5, 10, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 5, 7, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(form, gbc, 0, "Track title", titleField);
        addFormRow(form, gbc, 1, "Artist", artistField);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        form.add(new JLabel("Audio file"), gbc);

        JPanel fileRow = new JPanel(new BorderLayout(5, 0));
        fileRow.add(fileField, BorderLayout.CENTER);
        fileRow.add(browseButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(fileRow, gbc);

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                "Add a New Track",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String filename = fileField.getText().trim();

        if (title.isEmpty() || artist.isEmpty() || filename.isEmpty()) {
            showMessage("Please enter a title, artist, and audio file.");
            return;
        }

        File file = new File(filename);
        if (!file.isFile()) {
            showMessage("The selected audio file does not exist.");
            return;
        }

        Track track = new Track(title, artist, file.getAbsolutePath());
        library.add(track);
        organizer.addTrack(track);

        refreshTable();
        statusLabel.setText("Added track: " + title);
    }

    private void addFormRow(
            JPanel panel, GridBagConstraints gbc,
            int row, String label, JTextField field) {

        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    // ---------- Playback ----------

    private void playSelectedTrack() {
        Track track = getSelectedTrack();

        if (track == null) {
            showMessage("Select a track from the table first.");
            return;
        }

        statusLabel.setText("Playing: " + track.getTitle());

        Thread playbackThread = new Thread(() -> {
            organizer.playTrack(track);

            SwingUtilities.invokeLater(() -> {
                refreshTable();
                statusLabel.setText("Playback finished: " + track.getTitle());
            });
        });

        playbackThread.setDaemon(true);
        playbackThread.start();

        refreshTable();
    }

    private void playByArtistDialog() {
        if (library.isEmpty()) {
            showMessage("Add at least one track first.");
            return;
        }

        String artist = JOptionPane.showInputDialog(
                this,
                "Enter the artist name:",
                "Play Tracks by Artist",
                JOptionPane.QUESTION_MESSAGE
        );

        if (artist == null || artist.trim().isEmpty()) {
            return;
        }

        String requestedArtist = artist.trim();

        boolean exists = false;
        for (Track track : library) {
            if (track.getArtist().equalsIgnoreCase(requestedArtist)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            showMessage("No tracks found for " + requestedArtist + ".");
            return;
        }

        statusLabel.setText("Playing tracks by " + requestedArtist + "...");

        Thread artistThread = new Thread(() -> {
            organizer.playTracksByArtist(requestedArtist);

            SwingUtilities.invokeLater(() -> {
                refreshTable();
                statusLabel.setText(
                        "Finished playing tracks by " + requestedArtist
                );
            });
        });

        artistThread.setDaemon(true);
        artistThread.start();
    }

    private void stopPlayback() {
        organizer.stopPlaying();
        statusLabel.setText("Playback stopped.");
    }

    private void rateSelectedTrack() {
        Track track = getSelectedTrack();

        if (track == null) {
            showMessage("Select a track first.");
            return;
        }

        String value = JOptionPane.showInputDialog(
                this,
                "Enter a rating from 1 to 5:",
                track.getRating()
        );

        if (value == null) {
            return;
        }

        try {
            int rating = Integer.parseInt(value.trim());

            if (rating < 1 || rating > 5) {
                showMessage("Rating must be between 1 and 5.");
                return;
            }

            track.setRating(rating);
            refreshTable();
            statusLabel.setText("Rating updated for " + track.getTitle());

        } catch (NumberFormatException ex) {
            showMessage("Please enter a whole number from 1 to 5.");
        }
    }

    // ---------- Table and helpers ----------

    private Track getSelectedTrack() {
        int selectedRow = trackTable.getSelectedRow();

        if (selectedRow < 0 || selectedRow >= visibleTracks.size()) {
            return null;
        }

        return visibleTracks.get(selectedRow);
    }

    private void refreshTable() {
        if (tableModel == null) {
            return;
        }

        String query = searchField == null
                ? ""
                : searchField.getText().trim().toLowerCase();

        visibleTracks.clear();
        tableModel.setRowCount(0);

        int number = 1;

        for (Track track : library) {
            boolean matches = track.getTitle().toLowerCase().contains(query)
                    || track.getArtist().toLowerCase().contains(query);

            if (!matches) {
                continue;
            }

            visibleTracks.add(track);

            String rating = track.getRating() == 0
                    ? "—"
                    : track.getRating() + " / 5";

            tableModel.addRow(new Object[]{
                    number++,
                    track.getTitle(),
                    track.getArtist(),
                    track.getPlayCount(),
                    rating
            });
        }

        totalTracksLabel.setText(String.valueOf(library.size()));

        Set<String> artists = new HashSet<>();
        for (Track track : library) {
            artists.add(track.getArtist().toLowerCase());
        }

        totalArtistsLabel.setText(String.valueOf(artists.size()));
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "SoundSpace",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ---------- Application entry point ----------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {
                // Use Java's default look and feel if unavailable.
            }

            MusicOrganizerGUI gui = new MusicOrganizerGUI();
            gui.setVisible(true);
        });
    }
}
