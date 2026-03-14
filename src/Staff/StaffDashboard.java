package Staff;

import Main.Login;
import config.Session;
import config.config;
import design.BaseFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.sql.*;

public class StaffDashboard {

    public void startStaffSession() {
        // Main Frame
        BaseFrame frame = new BaseFrame(951, 550); // uses your BaseFrame design
        frame.setTitle("Staff Dashboard");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(null);

        // NAVBAR
        JPanel navbar = new JPanel();
        navbar.setBounds(0, 0, 951, 60);
        navbar.setBackground(new Color(200, 0, 0)); // red
        navbar.setLayout(null);

        // TITLE
        JLabel title = new JLabel("Staff Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(20, 10, 300, 40);
        navbar.add(title);


        int btnHeight = 40;
        int gap = 10;

// Log Out button (far right)
        JButton logoutBtn = new JButton("Log Out");
        int logoutBtnWidth = 80;
        logoutBtn.setBounds(frame.getWidth() - logoutBtnWidth - 20, 10, logoutBtnWidth, btnHeight); // 20px padding from right
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                Session.logout();
                frame.dispose();
                Login login = new Login();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
            }
        });
        navbar.add(logoutBtn);

// Bookings button (to the left of Transactions)
        JButton manageBookingsBtn = new JButton("Bookings");
        int bookingsBtnWidth = 150;
        manageBookingsBtn.setBackground(Color.WHITE);
        manageBookingsBtn.setForeground(Color.BLACK);
        manageBookingsBtn.setFocusPainted(false);
        manageBookingsBtn.addActionListener(e -> {
            ManageBooking booking = new ManageBooking();
            booking.showBookingTable();
            frame.dispose();
        });

// Transactions button (to the left of Log Out)
        JButton manageTransactionsBtn = new JButton("Transactions");
        int transactionsBtnWidth = 170;
        manageTransactionsBtn.setBackground(Color.WHITE);
        manageTransactionsBtn.setForeground(Color.BLACK);
        manageTransactionsBtn.setFocusPainted(false);
        manageTransactionsBtn.addActionListener(e -> {
            ManageTransaction transaction = new ManageTransaction();
            transaction.showTransactionTable();
            frame.dispose();
        });

// Dynamic positioning: Transactions right beside Log Out, Bookings left of Transactions
        manageTransactionsBtn.setBounds(
                logoutBtn.getX() - transactionsBtnWidth - gap, // X = left of logout
                10,
                transactionsBtnWidth,
                btnHeight
        );
        manageBookingsBtn.setBounds(
                manageTransactionsBtn.getX() - bookingsBtnWidth - gap, // X = left of transactions
                10,
                bookingsBtnWidth,
                btnHeight
        );

        navbar.add(manageBookingsBtn);
        navbar.add(manageTransactionsBtn);

        frame.add(navbar);

        // WELCOME LABEL
        String userName = getUserName(Session.getEmail()); // fetch the name from DB
        JLabel welcomeLabel = new JLabel("Welcome, " + userName); // keep original casing
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

    // Get user's name from email
    private String getUserName(String email) {
        String name = "User"; // default fallback
        String sql = "SELECT u_name FROM tbl_user WHERE u_email = ?";
        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("u_name");
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching user name: " + e.getMessage());
        }
        return name;
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
        String sql = "SELECT COUNT(*) AS total FROM tbl_transactions";
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
