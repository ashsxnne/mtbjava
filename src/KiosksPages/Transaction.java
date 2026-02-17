package KiosksPages;

import design.BaseFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Transaction extends BaseFrame {

    private int movieId;
    private String movieTitle;
    private String movieRating;
    private String seats;
    private int totalAmount;
    private byte[] posterBytes;

    private Color darkRed = new Color(120, 0, 0);
    private Color deepBlack = new Color(15, 15, 15);
    private Color accentRed = new Color(200, 0, 0);
    private Color softWhite = new Color(230, 230, 230);

    public Transaction(int movieId, String movieTitle,
            String movieRating, String seats,
            int totalAmount, byte[] posterBytes) {

        super(946, 611);

        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.posterBytes = posterBytes;

        initUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(softWhite);
        mainPanel.setLayout(null);

        // Title
        JLabel title = new JLabel("PROCESS PAYMENT");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(accentRed);
        title.setBounds(250, 50, 500, 50);
        mainPanel.add(title);

        // CASH / COUNTER Panel
        JPanel cashPanel = new JPanel();
        cashPanel.setLayout(new BorderLayout());
        cashPanel.setBounds(120, 180, 300, 200);
        cashPanel.setBackground(softWhite);
        cashPanel.setBorder(BorderFactory.createLineBorder(accentRed, 4));

        JLabel cashLabel = new JLabel("CASH / COUNTER", SwingConstants.CENTER);
        cashLabel.setFont(new Font("Arial", Font.BOLD, 22));
        cashLabel.setForeground(accentRed);
        cashPanel.add(cashLabel, BorderLayout.CENTER);

        // Click event
        cashPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cashPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PaymentCash(movieId, movieTitle, movieRating,
                        seats, totalAmount, posterBytes);

            }

        });

        //cancel button diay di back lol
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(400, 450, 150, 45);
        cancelBtn.setBackground(darkRed);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);

        cancelBtn.addActionListener(e -> {
            dispose();
            new Bookmovie(movieId, movieTitle, movieRating);
        });

        mainPanel.add(cancelBtn);

        mainPanel.add(cashPanel);

        // OR label
        JLabel orLabel = new JLabel("OR");
        orLabel.setFont(new Font("Arial", Font.BOLD, 25));
        orLabel.setForeground(darkRed);
        orLabel.setBounds(450, 250, 50, 40);
        mainPanel.add(orLabel);

        // PAY ONLINE Panel
        JPanel onlinePanel = new JPanel();
        onlinePanel.setLayout(new BorderLayout());
        onlinePanel.setBounds(520, 180, 300, 200);
        onlinePanel.setBackground(softWhite);
        onlinePanel.setBorder(BorderFactory.createLineBorder(accentRed, 4));

        JLabel onlineLabel = new JLabel("PAY ONLINE", SwingConstants.CENTER);
        onlineLabel.setFont(new Font("Arial", Font.BOLD, 22));
        onlineLabel.setForeground(accentRed);
        onlinePanel.add(onlineLabel, BorderLayout.CENTER);

        onlinePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        onlinePanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PaymentOnline(); // create this class
            }
        });

        mainPanel.add(onlinePanel);

        add(mainPanel);
    }
}
