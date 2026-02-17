package Staff;

import config.Session;
import config.config;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ManageTransaction {

    private JFrame frame;
    private JTable transactionTable;
    private JTextField searchField;

    public void showTransactionTable() {
        frame = new JFrame("Manage Transactions");
        frame.setSize(951, 550);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(null);

        // NAVBAR (same as StaffDash)
        JPanel navbar = new JPanel();
        navbar.setBounds(0, 0, 951, 60);
        navbar.setBackground(new Color(200, 0, 0));
        navbar.setLayout(null);

        JLabel title = new JLabel("Staff Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(20, 10, 300, 40);
        navbar.add(title);

        JButton manageBookingsBtn = new JButton("Manage Bookings");
        manageBookingsBtn.setBounds(600, 10, 150, 40);
        manageBookingsBtn.setBackground(Color.WHITE);
        manageBookingsBtn.setForeground(Color.BLACK);
        manageBookingsBtn.setFocusPainted(false);
        navbar.add(manageBookingsBtn);

        JButton manageTransactionsBtn = new JButton("Manage Transactions");
        manageTransactionsBtn.setBounds(760, 10, 170, 40);
        manageTransactionsBtn.setBackground(Color.WHITE);
        manageTransactionsBtn.setForeground(Color.BLACK);
        manageTransactionsBtn.setFocusPainted(false);
        navbar.add(manageTransactionsBtn);

        frame.add(navbar);

        // NAVBAR BUTTON ACTIONS
        manageBookingsBtn.addActionListener(e -> {
            frame.dispose();
            new ManageBooking().showBookingTable();
        });

        manageTransactionsBtn.addActionListener(e -> {
            loadTransactions(searchField.getText().trim()); // refresh table
        });

        // WELCOME LABEL
        JLabel welcomeLabel = new JLabel("Welcome, " + Session.getEmail());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBounds(20, 70, 400, 30);
        frame.add(welcomeLabel);

        // SEARCH FIELD
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(20, 110, 60, 25);
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        frame.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(80, 110, 300, 25);
        frame.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(390, 110, 100, 25);
        searchBtn.setBackground(new Color(200, 0, 0));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        frame.add(searchBtn);

        // TABLE
        transactionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(20, 150, 900, 320);
        frame.add(scrollPane);

        loadTransactions(""); // load all initially

        // SEARCH BUTTON ACTION
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            loadTransactions(keyword);
        });

        // EDIT PAYMENT STATUS BUTTON
        JButton editBtn = new JButton("Toggle PAID/PENDING");
        editBtn.setBounds(600, 110, 200, 25);
        editBtn.setBackground(new Color(200, 0, 0));
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);
        frame.add(editBtn);

        editBtn.addActionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow >= 0) {
                int t_id = Integer.parseInt(transactionTable.getValueAt(selectedRow, 0).toString());
                String currentStatus = transactionTable.getValueAt(selectedRow, 3).toString();
                String newStatus = currentStatus.equalsIgnoreCase("PAID") ? "PENDING" : "PAID";
                updatePaymentStatus(t_id, newStatus);
                loadTransactions(searchField.getText().trim()); // reload table
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a transaction to edit.");
            }
        });

        frame.setVisible(true);
    }

    // LOAD TRANSACTIONS WITH OPTIONAL SEARCH
    private void loadTransactions(String keyword) {
        String sql = "SELECT t_id, b_id, booking_fee, payment_status, payment_date, cash, change, total_amount FROM tbl_transaction";

        if (!keyword.isEmpty()) {
            sql += " WHERE CAST(t_id AS TEXT) LIKE ? OR CAST(b_id AS TEXT) LIKE ? OR payment_status LIKE ?";
        }

        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            if (!keyword.isEmpty()) {
                String kw = "%" + keyword + "%";
                pst.setString(1, kw);
                pst.setString(2, kw);
                pst.setString(3, kw);
            }

            try (ResultSet rs = pst.executeQuery()) {
                transactionTable.setModel(DbUtils.resultSetToTableModel(rs));
            }

        } catch (Exception e) {
            System.out.println("Error loading transactions: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error loading transactions: " + e.getMessage());
        }
    }

    // UPDATE PAYMENT STATUS
    private void updatePaymentStatus(int t_id, String status) {
        String sql = "UPDATE tbl_transaction SET payment_status = ? WHERE t_id = ?";

        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, status);
            pst.setInt(2, t_id);
            int updated = pst.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(frame, "Payment status updated successfully!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error updating payment status: " + e.getMessage());
        }
    }
}
