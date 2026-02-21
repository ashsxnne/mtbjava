package KiosksPages;

import MoviePage.Nowshowing;
import design.BaseFrame;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

public class Bookmovie extends BaseFrame {

    private int movieId;
    private String movieTitle;
    private String movieRating;

    private byte[] moviePosterBytes;

    private List<String> selectedSeats = new ArrayList<>();
    private List<String> takenSeats = new ArrayList<>();
    private List<String> wheelchairSeats = Arrays.asList("A3", "A4", "B7", "C1");
    private int seatPrice = 250;

    private JLabel totalLabel;

    private Color darkRed = new Color(120, 0, 0);
    private Color deepBlack = new Color(15, 15, 15);
    private Color accentRed = new Color(200, 0, 0);
    private Color softWhite = new Color(230, 230, 230);

    public Bookmovie(int movieId, String title, String rating) {

        super(946, 611);

        this.movieId = movieId;
        this.movieTitle = title;
        this.movieRating = rating;

        loadMoviePoster(); // load poster from tbl_movies       
        loadTakenSeats();
        initUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ================= LOAD POSTER FROM tbl_movies =================
    private void loadMoviePoster() {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");

            PreparedStatement pst = con.prepareStatement(
                    "SELECT poster FROM tbl_movies WHERE m_id = ?");
            pst.setInt(1, movieId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                moviePosterBytes = rs.getBytes("poster");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {

        setLayout(new BorderLayout());
        getContentPane().setBackground(deepBlack);

        JLabel header = new JLabel("BOOKING NOW - " + movieTitle, SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setForeground(softWhite);
        header.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(deepBlack);

        mainPanel.add(createLeftPanel());
        mainPanel.add(createRightPanel());

        add(mainPanel, BorderLayout.CENTER);
    }

    // ================= LEFT PANEL =================
    private JPanel createLeftPanel() {

        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(darkRed);
        left.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel posterLabel = new JLabel();
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);

        if (moviePosterBytes != null) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(moviePosterBytes));
                Image scaled = img.getScaledInstance(300, 400, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaled));
            } catch (Exception e) {
                posterLabel.setText("No Image");
                posterLabel.setForeground(Color.WHITE);
            }
        }

        left.add(posterLabel, BorderLayout.CENTER);

        totalLabel = new JLabel("Total: ₱0");
        totalLabel.setForeground(Color.YELLOW);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel bottom = new JPanel();
        bottom.setBackground(darkRed);
        bottom.add(totalLabel);

        left.add(bottom, BorderLayout.SOUTH);

        return left;
    }

    // ================= RIGHT PANEL =================
    private JPanel createRightPanel() {

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(new Color(80, 0, 0));
        right.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Screen label
        JLabel screen = new JLabel("SCREEN", SwingConstants.CENTER);
        screen.setForeground(softWhite);
        screen.setBorder(new MatteBorder(0, 0, 2, 0, softWhite));
        right.add(screen, BorderLayout.NORTH);

        // Seat panel → 5 rows x 10 columns
        JPanel seatPanel = new JPanel(new GridLayout(5, 10, 8, 8));
        seatPanel.setBackground(new Color(80, 0, 0));

        // Define wheelchair/PWD seats (can also fetch from DB)
        List<String> wheelchairSeats = Arrays.asList("A3", "A4", "B7", "C1");

        int totalRows = 5;
        int seatsPerRow = 10;
        char rowChar = 'A';

        for (int row = 0; row < totalRows; row++) {
            for (int col = 1; col <= seatsPerRow; col++) {
                String seatName = rowChar + String.valueOf(col);
                JButton seat = new JButton(seatName);

                seat.setFont(new Font("Segoe UI", Font.BOLD, 14));
                seat.setFocusPainted(false);
                seat.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                if (takenSeats.contains(seatName)) {
                    seat.setBackground(Color.GRAY);
                    seat.setForeground(Color.WHITE);
                    seat.setEnabled(false);
                } else if (wheelchairSeats.contains(seatName)) {
                    seat.setBackground(new Color(255, 165, 0)); // Orange for PWD
                    seat.setForeground(Color.BLACK);
                    seat.setToolTipText("PWD/Wheelchair seat");
                    styleSeat(seat); // keep it selectable if needed
                } else {
                    seat.setBackground(new Color(0, 150, 170));
                    seat.setForeground(Color.BLACK);
                    styleSeat(seat);
                }

                seatPanel.add(seat);
            }
            rowChar++; // Next row
        }

        right.add(seatPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(80, 0, 0));

        JButton clearBtn = new JButton("Clear");
        JButton bookBtn = new JButton("Book Now");
        JButton backBtn = new JButton("Back");

        styleButton(clearBtn);
        styleButton(bookBtn);
        styleButton(backBtn);

        backBtn.addActionListener(e -> {
            dispose();
            new Nowshowing();
        });

        clearBtn.addActionListener(e -> clearSeats());
        bookBtn.addActionListener(e -> bookNow());

        buttons.add(backBtn);
        buttons.add(clearBtn);
        buttons.add(bookBtn);

        right.add(buttons, BorderLayout.SOUTH);

        return right;
    }

    private void styleSeat(JButton seat) {

        seat.addActionListener(e -> {

            String seatName = seat.getText();

            boolean isWheelchair = wheelchairSeats.contains(seatName);

            if (selectedSeats.contains(seatName)) {
                // Deselect seat
                selectedSeats.remove(seatName);

                if (isWheelchair) {
                    // Revert to orange for PWD seats when deselected
                    seat.setBackground(new Color(255, 165, 0)); // Orange
                    seat.setForeground(Color.BLACK);
                } else {
                    // Revert to normal blue for regular seats when deselected
                    seat.setBackground(new Color(0, 150, 170));
                    seat.setForeground(Color.BLACK);
                }

            } else {
                // Selecting seat
                if (isWheelchair) {
                    JOptionPane.showMessageDialog(
                            this,
                            "This seat is reserved to accommodate persons with disabilities, and we kindly ask for your understanding.",
                            "Seat Selection Notice",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

                selectedSeats.add(seatName);
                seat.setBackground(Color.YELLOW);
                seat.setForeground(Color.BLACK);
            }

            updateTotal();
        });
    }

    private void styleButton(JButton btn) {
        btn.setBackground(accentRed);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 40));
    }

    private void updateTotal() {
        int total = selectedSeats.size() * seatPrice;
        totalLabel.setText("Total: ₱" + total);
    }

    private void clearSeats() {
        selectedSeats.clear();
        dispose();
        new Bookmovie(movieId, movieTitle, movieRating);
    }

    // ================= SAVE BOOKING =================
    private void bookNow() {

        if (selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select seat(s).");
            return;
        }

        // 🔥 SAFETY CHECK
        loadTakenSeats();

        for (String s : selectedSeats) {
            if (takenSeats.contains(s)) {
                JOptionPane.showMessageDialog(this,
                        "Seat " + s + " was just booked by another customer!");
                return;
            }
        }

        String seats = String.join(",", selectedSeats);
        int total = selectedSeats.size() * seatPrice;

        try {

            new Transaction(movieId, movieTitle, movieRating,
                    seats, total, moviePosterBytes);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadTakenSeats() {

        takenSeats.clear();

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");

            PreparedStatement pst = con.prepareStatement(
                    "SELECT seat_no FROM tbl_booking WHERE m_id = ?");
            pst.setInt(1, movieId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String seats = rs.getString("seat_no");

                if (seats != null) {

                    String[] seatArray = seats.split(",");

                    for (String s : seatArray) {
                        takenSeats.add(s.trim());
                    }
                }
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
