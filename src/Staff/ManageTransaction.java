package Staff;

import Main.Login;
import config.Session;
import config.config;
import design.BaseFrame;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ManageTransaction extends BaseFrame {

    private JTable transactionTable;
    private JTextField searchField;

    public ManageTransaction() {
        super(); // BaseFrame default size 946x611
        showTransactionTable(); // calls your existing setup
    }

    public void showTransactionTable() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null); // absolute layout like your original

        // ================= NAVBAR =================
        JPanel navbar = new JPanel();
        navbar.setBounds(0, 0, getWidth(), 60);
        navbar.setBackground(new Color(200, 0, 0));
        navbar.setLayout(null); // same as ManageBooking

        JLabel title = new JLabel("Staff Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(20, 10, 300, 40);
        navbar.add(title);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(860, 10, 80, 40);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFocusPainted(false);
        navbar.add(logoutBtn);

        // Buttons
        JButton manageArchiveBtn = new JButton("Archive Transactions");
        manageArchiveBtn.setBounds(350, 10, 180, 40);

        JButton manageBookingsBtn = new JButton("Manage Bookings");
        manageBookingsBtn.setBounds(540, 10, 150, 40);

        JButton manageTransactionsBtn = new JButton("Manage Transactions");
        manageTransactionsBtn.setBounds(710, 10, 180, 40);

        for (JButton btn : new JButton[]{manageArchiveBtn, manageBookingsBtn, manageTransactionsBtn}) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            navbar.add(btn);
        }

        add(navbar);

        // ================= NAVBAR ACTIONS =================
        manageBookingsBtn.addActionListener(e -> {
            dispose();
            new ManageBooking().showBookingTable();
        });

        manageTransactionsBtn.addActionListener(e -> {
            dispose();
            new ManageTransaction().showTransactionTable();
        });

        manageArchiveBtn.addActionListener(e -> {
            dispose();
            new ArchiveTransactions().showArchiveTable();
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();      // Close ManageBooking
                new Login().setVisible(true);  // Open Login JFrame
            }
        });

        // ================= WELCOME =================
        JLabel welcomeLabel = new JLabel("Welcome, " + Session.getEmail());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBounds(20, 70, 400, 30);
        add(welcomeLabel);

        // ================= SEARCH =================
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(20, 110, 60, 25);
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(80, 110, 300, 25);
        add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(390, 110, 100, 25);
        searchBtn.setBackground(new Color(200, 0, 0));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        add(searchBtn);

        // ================= TABLE =================
        transactionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(20, 150, 900, 300);
        add(scrollPane);

        loadTransactions("");

        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            loadTransactions(keyword);
        });

        // ================= TOGGLE PAID/PENDING =================
        JButton toggleBtn = new JButton("Toggle PAID/PENDING");
        toggleBtn.setBounds(600, 110, 200, 25);
        toggleBtn.setBackground(new Color(200, 0, 0));
        toggleBtn.setForeground(Color.WHITE);
        toggleBtn.setFocusPainted(false);
        add(toggleBtn);

        toggleBtn.addActionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow >= 0) {
                int t_id = Integer.parseInt(transactionTable.getValueAt(selectedRow, 0).toString());
                String currentStatus = transactionTable.getValueAt(selectedRow, 3).toString();
                String newStatus = currentStatus.equalsIgnoreCase("PAID") ? "PENDING" : "PAID";
                updatePaymentStatus(t_id, newStatus);
                loadTransactions(searchField.getText().trim());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a transaction.");
            }
        });

        // ================= MARK AS COMPLETED =================
        JButton completeBtn = new JButton("Mark as COMPLETED");
        completeBtn.setBounds(600, 470, 200, 30);
        completeBtn.setBackground(new Color(0, 128, 0));
        completeBtn.setForeground(Color.WHITE);
        completeBtn.setFocusPainted(false);
        add(completeBtn);

        completeBtn.addActionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow >= 0) {
                int t_id = Integer.parseInt(transactionTable.getValueAt(selectedRow, 0).toString());
                String paymentStatus = transactionTable.getValueAt(selectedRow, 3).toString();
                if (paymentStatus.equalsIgnoreCase("PAID")) {
                    archiveTransaction(t_id);
                    loadTransactions(searchField.getText().trim());
                } else {
                    JOptionPane.showMessageDialog(this, "Only PAID transactions can be completed.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a transaction.");
            }
        });

        setVisible(true);
    }

    // ================= LOAD TRANSACTIONS =================
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
            JOptionPane.showMessageDialog(this, "Error loading transactions: " + e.getMessage());
        }
    }

    // ================= UPDATE PAYMENT STATUS =================
    private void updatePaymentStatus(int t_id, String status) {
        String sql = "UPDATE tbl_transaction SET payment_status = ? WHERE t_id = ?";
        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, status);
            pst.setInt(2, t_id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payment status updated!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage());
        }
    }

    // ================= ARCHIVE METHOD =================
    private void archiveTransaction(int t_id) {
        String insertSQL = "INSERT INTO tbl_archivetransaction "
                + "(t_id, b_id, booking_fee, payment_status, payment_date, cash, change, total_amount, completed_at) "
                + "SELECT t_id, b_id, booking_fee, payment_status, payment_date, cash, change, total_amount, datetime('now') "
                + "FROM tbl_transaction WHERE t_id = ? AND payment_status = 'PAID'";

        String deleteSQL = "DELETE FROM tbl_transaction WHERE t_id = ? AND payment_status = 'PAID'";

        try (Connection conn = config.connectDB()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertPst = conn.prepareStatement(insertSQL);
                    PreparedStatement deletePst = conn.prepareStatement(deleteSQL)) {

                insertPst.setInt(1, t_id);
                int inserted = insertPst.executeUpdate();

                if (inserted > 0) {
                    deletePst.setInt(1, t_id);
                    deletePst.executeUpdate();
                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Transaction archived successfully!");
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Transaction not archived.");
                }

            } catch (Exception ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }
}
