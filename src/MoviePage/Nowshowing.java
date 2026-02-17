package MoviePage;

import design.BaseFrame;
import config.config;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;

public class Nowshowing extends BaseFrame {

    private Color darkRed = new Color(120, 0, 0);
    private Color deepBlack = new Color(15, 15, 15);
    private Color accentRed = new Color(200, 0, 0);
    private Color softWhite = new Color(230, 230, 230);

    public Nowshowing() {
        super(946, 611);
        initUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {

        JPanel mainPanel = new JPanel() {

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, darkRed,
                        getWidth(), getHeight(), deepBlack
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        mainPanel.setPreferredSize(new Dimension(946, 611));
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        JLabel title = new JLabel("Movie Ticket Booking", SwingConstants.CENTER);
        title.setBounds(0, 20, 946, 40);

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mainPanel.add(title);

        // FULL SCREEN NOW SHOWING
        createNowShowingSection(mainPanel);

        // ===== BOTTOM SELECTORS =====
        JButton nowBtn = new JButton("NOW SHOWING");
        nowBtn.setBounds(30, 540, 180, 40);
        nowBtn.setBackground(accentRed);
        nowBtn.setForeground(Color.WHITE);
        nowBtn.setFocusPainted(false);
        nowBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(nowBtn);

        JButton upcomingBtn = new JButton("UPCOMING");
        upcomingBtn.setBounds(730, 540, 180, 40);
        upcomingBtn.setBackground(new Color(60, 0, 0));
        upcomingBtn.setForeground(Color.WHITE);
        upcomingBtn.setFocusPainted(false);
        upcomingBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(upcomingBtn);

        upcomingBtn.addActionListener(e -> {
            // Open the Upcoming window
            new Upcoming();  // This will instantiate and display the Upcoming class
        });

    }

    private void addSectionLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 300, 30);
        label.setForeground(softWhite);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(label);
    }

    private void loadMovies(JPanel panel, int y, String statusFilter) {

        int x = 30;

        try (Connection conn = config.connectDB();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT * FROM tbl_movies WHERE status = ?")) {

            ps.setString(1, statusFilter);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("m_id");
                String name = rs.getString("movie_name");
                String rating = rs.getString("rated");
                byte[] posterBytes = rs.getBytes("poster");

                // DEBUG
                System.out.println("Movie: " + name
                        + " | Poster bytes: "
                        + (posterBytes != null ? posterBytes.length : "NULL"));

                JPanel card = createMovieCard(
                        id,
                        name,
                        rating,
                        posterBytes,
                        statusFilter.equals("NOWSHOWING")
                );

                card.setBounds(x, y, 200, 200);
                panel.add(card);

                x += 220;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createMovieCard(int movieId, String title, String rating,
            byte[] posterBytes, boolean nowShowing) {

        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(new Color(30, 0, 0));
        card.setBorder(BorderFactory.createLineBorder(accentRed, 2));

        // ================= IMAGE FROM BLOB =================
        JLabel posterLabel = new JLabel();
        posterLabel.setBounds(0, 0, 200, 110);

        if (posterBytes != null && posterBytes.length > 0) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(posterBytes);
                BufferedImage bufferedImage = ImageIO.read(bis);

                Image scaledImage = bufferedImage.getScaledInstance(
                        200, 110, Image.SCALE_SMOOTH);

                posterLabel.setIcon(new ImageIcon(scaledImage));

            } catch (Exception e) {
                e.printStackTrace();
                posterLabel.setIcon(createPlaceholderImage());
            }
        } else {
            posterLabel.setIcon(createPlaceholderImage());
        }

        card.add(posterLabel);

        // ================= RATING =================
        JLabel rate = new JLabel(rating, SwingConstants.CENTER);
        rate.setBounds(140, 5, 50, 25);
        rate.setOpaque(true);
        rate.setBackground(accentRed);
        rate.setForeground(Color.WHITE);
        rate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        card.add(rate);

        // ================= TITLE =================
        JLabel movieTitle = new JLabel(title);
        movieTitle.setBounds(10, 115, 180, 20);
        movieTitle.setForeground(softWhite);
        movieTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(movieTitle);

        // ================= STATUS =================
        JLabel status = new JLabel(nowShowing ? "Now Showing" : "Upcoming");
        status.setBounds(10, 135, 150, 18);
        status.setForeground(Color.LIGHT_GRAY);
        status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(status);

        // ================= BOOK BUTTON =================
        JButton bookBtn = new JButton("BOOK NOW");
        bookBtn.setBounds(25, 160, 150, 30);
        bookBtn.setBackground(accentRed);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFocusPainted(false);
        bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));

        if (!nowShowing) {
            bookBtn.setEnabled(false);
        }

        bookBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Booking for " + title,
                    "Booking",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        card.add(bookBtn);

        return card;
    }

    private void createNowShowingSection(JPanel mainPanel) {

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0, 1));
        container.setBackground(new Color(0, 0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBounds(30, 80, 880, 440);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        mainPanel.add(scrollPane);

        try (Connection conn = config.connectDB();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT * FROM tbl_movies WHERE status = 'NOWSHOWING'")) {

            ResultSet rs = ps.executeQuery();

            int movieCount = 0;

            while (rs.next()) {

                int id = rs.getInt("m_id");
                String name = rs.getString("movie_name");
                String rating = rs.getString("rated");
                String description = rs.getString("description");
                String showtime = rs.getString("show_time");
                byte[] posterBytes = rs.getBytes("poster");

                JPanel moviePanel = createNowShowingRow(
                        id, name, rating, description, showtime, posterBytes);

                container.add(moviePanel);
                movieCount++;
            }

            container.setPreferredSize(new Dimension(880, 440 * movieCount));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createNowShowingRow(int movieId,
            String title,
            String rating,
            String description,
            String showtime,
            byte[] posterBytes) {

        JPanel row = new JPanel();
        row.setLayout(null);
        row.setPreferredSize(new Dimension(880, 440));

        row.setBackground(new Color(30, 0, 0));
        row.setBorder(BorderFactory.createLineBorder(accentRed, 2));

        // ===== BIG POSTER =====
        JLabel posterLabel = new JLabel();
        posterLabel.setBounds(80, 60, 300, 320);

        if (posterBytes != null && posterBytes.length > 0) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(posterBytes));
                Image scaled = img.getScaledInstance(300, 320, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaled));
            } catch (Exception e) {
                posterLabel.setIcon(createPlaceholderImage());
            }
        } else {
            posterLabel.setIcon(createPlaceholderImage());
        }

        row.add(posterLabel);

        // ===== TITLE =====
        JLabel movieTitle = new JLabel(title);
        movieTitle.setBounds(420, 80, 400, 40);
        movieTitle.setForeground(softWhite);
        movieTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        row.add(movieTitle);

        // ===== RATING =====
        JLabel rate = new JLabel("Rated: " + rating);
        rate.setBounds(420, 130, 300, 25);
        rate.setForeground(Color.LIGHT_GRAY);
        row.add(rate);

        // ===== DESCRIPTION (BIGGER) =====
        JTextArea descArea = new JTextArea(description);
        descArea.setBounds(420, 170, 380, 120);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setForeground(softWhite);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        row.add(descArea);

        // ===== SHOWTIME =====
        JLabel showLabel = new JLabel("Showtime: " + showtime);
        showLabel.setBounds(420, 310, 300, 30);
        showLabel.setForeground(Color.WHITE);
        showLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        row.add(showLabel);

        // ===== BOOK BUTTON =====
        JButton bookBtn = new JButton("BOOK NOW");
        bookBtn.setBounds(420, 350, 200, 50);
        bookBtn.setBackground(accentRed);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFocusPainted(false);
        bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        bookBtn.addActionListener(e -> {

            // Open booking window
            new KiosksPages.Bookmovie(movieId, title, rating);

            // Optional: close Nowshowing page
            dispose();
        });

        row.add(bookBtn);

        return row;
    }

    private ImageIcon createPlaceholderImage() {

        BufferedImage placeholder = new BufferedImage(
                200, 110, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = placeholder.createGraphics();
        g2d.setColor(darkRed);
        g2d.fillRect(0, 0, 200, 110);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));

        FontMetrics fm = g2d.getFontMetrics();
        String text = "No Image";
        int textWidth = fm.stringWidth(text);

        g2d.drawString(text,
                (200 - textWidth) / 2,
                55);

        g2d.dispose();

        return new ImageIcon(placeholder);
    }
}
