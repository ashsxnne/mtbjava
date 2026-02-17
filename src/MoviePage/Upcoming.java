package MoviePage;

import KiosksPages.Bookmovie;
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

public class Upcoming extends BaseFrame {

    private Color darkRed = new Color(120, 0, 0);
    private Color deepBlack = new Color(15, 15, 15);
    private Color accentRed = new Color(200, 0, 0);
    private Color softWhite = new Color(230, 230, 230);

    public Upcoming() {
        super(946, 611);  // Same window size as Nowshowing
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

        // Full screen upcoming movies
        createUpcomingMoviesSection(mainPanel);

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

        // Button click action: Open the Now Showing section
        nowBtn.addActionListener(e -> {
            new Nowshowing();  // Open the Nowshowing class
        });
    }

    private void createUpcomingMoviesSection(JPanel mainPanel) {

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0, 1)); // Maintain consistent layout
        container.setBackground(new Color(0, 0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBounds(30, 80, 880, 440); // Same size as Nowshowing

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        mainPanel.add(scrollPane);

        // Load upcoming movies from the database
        try (Connection conn = config.connectDB();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM tbl_movies WHERE status = 'UPCOMING'")) {

            ResultSet rs = ps.executeQuery();
            int movieCount = 0;

            while (rs.next()) {

                int id = rs.getInt("m_id");
                String name = rs.getString("movie_name");
                String rating = rs.getString("rated");
                String description = rs.getString("description");
                String showtime = rs.getString("show_time");
                byte[] posterBytes = rs.getBytes("poster");

                JPanel moviePanel = createUpcomingMovieRow(id, name, rating, description, showtime, posterBytes);
                container.add(moviePanel);
                movieCount++;
            }

            // Dynamically adjust the height of the container to fit all movies
            container.setPreferredSize(new Dimension(880, 440 * movieCount));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createUpcomingMovieRow(int movieId, String title, String rating, String description, String showtime, byte[] posterBytes) {

        JPanel row = new JPanel();
        row.setLayout(null);
        row.setPreferredSize(new Dimension(880, 440));
        row.setBackground(new Color(30, 0, 0));
        row.setBorder(BorderFactory.createLineBorder(accentRed, 2));

        // BIG POSTER
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

        // Title
        JLabel movieTitle = new JLabel(title);
        movieTitle.setBounds(420, 80, 400, 40);
        movieTitle.setForeground(softWhite);
        movieTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        row.add(movieTitle);

        // Rating
        JLabel rate = new JLabel("Rated: " + rating);
        rate.setBounds(420, 130, 300, 25);
        rate.setForeground(Color.LIGHT_GRAY);
        row.add(rate);

        // Description
        JTextArea descArea = new JTextArea(description);
        descArea.setBounds(420, 170, 380, 120);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setForeground(softWhite);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        row.add(descArea);

        // Showtime
        JLabel showLabel = new JLabel("Showtime: " + showtime);
        showLabel.setBounds(420, 310, 300, 30);
        showLabel.setForeground(Color.WHITE);
        showLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        row.add(showLabel);

        // Book Button
        JButton bookBtn = new JButton("BOOK NOW");
        bookBtn.setBounds(420, 350, 200, 50);
        bookBtn.setBackground(accentRed);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFocusPainted(false);
        bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Pass the movieId, title, and rating to Bookmovie constructor
        bookBtn.addActionListener(e -> {
            new Bookmovie(movieId, title, rating);  // Call the Bookmovie class with the movie details
        });

        row.add(bookBtn);

        return row;
    }

    private ImageIcon createPlaceholderImage() {

        BufferedImage placeholder = new BufferedImage(200, 110, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = placeholder.createGraphics();
        g2d.setColor(darkRed);
        g2d.fillRect(0, 0, 200, 110);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));

        FontMetrics fm = g2d.getFontMetrics();
        String text = "No Image";
        int textWidth = fm.stringWidth(text);

        g2d.drawString(text, (200 - textWidth) / 2, 55);
        g2d.dispose();

        return new ImageIcon(placeholder);
    }
}
