package Staff;

import config.Session;
import config.config;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.sql.*;

public class StaffDash {

    public void startStaffSession() {
        // Main Frame
        JFrame frame = new JFrame("Staff Dashboard");
        frame.setSize(951, 550);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(null);

        // NAVBAR
        JPanel navbar = new JPanel();
        navbar.setBounds(0, 0, 951, 60);
        navbar.setBackground(new Color(200, 0, 0)); // red
        navbar.setLayout(null);

        JLabel title = new JLabel("Staff Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(20, 10, 300, 40);
        navbar.add(title);

        // MANAGE BOOKINGS BUTTON
        JButton manageBookingsBtn = new JButton("Manage Bookings");
        manageBookingsBtn.setBounds(600, 10, 150, 40);
        manageBookingsBtn.setBackground(Color.WHITE);
        manageBookingsBtn.setForeground(Color.BLACK);
        manageBookingsBtn.setFocusPainted(false);
        manageBookingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ManageBooking class
                ManageBooking booking = new ManageBooking();
                booking.showBookingTable(); // method in ManageBooking to show UI
                frame.dispose(); // close dashboard if needed
            }
        });
        navbar.add(manageBookingsBtn);

        // MANAGE TRANSACTIONS BUTTON
        JButton manageTransactionsBtn = new JButton("Manage Transactions");
        manageTransactionsBtn.setBounds(760, 10, 170, 40);
        manageTransactionsBtn.setBackground(Color.WHITE);
        manageTransactionsBtn.setForeground(Color.BLACK);
        manageTransactionsBtn.setFocusPainted(false);
        manageTransactionsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ManageTransaction class
                ManageTransaction transaction = new ManageTransaction();
                transaction.showTransactionTable(); // method in ManageTransaction to show UI
                frame.dispose(); // close dashboard if needed
            }
        });
        navbar.add(manageTransactionsBtn);

        frame.add(navbar);

        // WELCOME LABEL
        JLabel welcomeLabel = new JLabel("Welcome, " + Session.getEmail());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBounds(20, 80, 400, 30);
        frame.add(welcomeLabel);

        // CARDS PANEL
        JPanel cardsPanel = new JPanel();
        cardsPanel.setBounds(20, 130, 911, 380);
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setLayout(null);

        // DATABASE QUERIES
        int totalBookings = getTotalBookings();
        int totalTransactions = getTotalTransactions();

        // Bookings Card
        JPanel bookingsCard = new JPanel();
        bookingsCard.setBounds(0, 0, 440, 180);
        bookingsCard.setBackground(Color.WHITE);
        bookingsCard.setBorder(new LineBorder(new Color(200, 0, 0), 2));
        bookingsCard.setLayout(null);

        JLabel bookingsTitle = new JLabel("Bookings");
        bookingsTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        bookingsTitle.setForeground(Color.BLACK);
        bookingsTitle.setBounds(20, 20, 200, 30);
        bookingsCard.add(bookingsTitle);

        JLabel bookingsCount = new JLabel(String.valueOf(totalBookings));
        bookingsCount.setFont(new Font("Segoe UI", Font.BOLD, 50));
        bookingsCount.setForeground(new Color(200, 0, 0));
        bookingsCount.setBounds(20, 60, 100, 60);
        bookingsCard.add(bookingsCount);

        cardsPanel.add(bookingsCard);

        // Transactions Card
        JPanel transactionsCard = new JPanel();
        transactionsCard.setBounds(460, 0, 440, 180);
        transactionsCard.setBackground(Color.WHITE);
        transactionsCard.setBorder(new LineBorder(new Color(200, 0, 0), 2));
        transactionsCard.setLayout(null);

        JLabel transactionsTitle = new JLabel("Transactions");
        transactionsTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        transactionsTitle.setForeground(Color.BLACK);
        transactionsTitle.setBounds(20, 20, 200, 30);
        transactionsCard.add(transactionsTitle);

        JLabel transactionsCount = new JLabel(String.valueOf(totalTransactions));
        transactionsCount.setFont(new Font("Segoe UI", Font.BOLD, 50));
        transactionsCount.setForeground(new Color(200, 0, 0));
        transactionsCount.setBounds(20, 60, 100, 60);
        transactionsCard.add(transactionsCount);

        cardsPanel.add(transactionsCard);

        frame.add(cardsPanel);

        frame.setVisible(true);
    }

    // Fetch total bookings from tbl_booking
    private int getTotalBookings() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM tbl_booking";
        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error fetching total bookings: " + e.getMessage());
        }
        return count;
    }

    // Fetch total transactions from tbl_transaction
    private int getTotalTransactions() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM tbl_transaction";
        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error fetching total transactions: " + e.getMessage());
        }
        return count;
    }
}
