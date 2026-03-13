package Staff;

import Main.Login;
import config.Session;
import config.config;
import design.BaseFrame;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
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

        int btnHeight = 40;

        JButton manageArchiveBtn = new JButton("Archive Transactions");
        manageArchiveBtn.setBounds(240, 10, 180, btnHeight);

        JButton manageBookingsBtn = new JButton("Bookings");
        manageBookingsBtn.setBounds(430, 10, 120, btnHeight);

        JButton manageTransactionsBtn = new JButton("Transactions");
        manageTransactionsBtn.setBounds(560, 10, 160, btnHeight);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(740, 10, 100, 40); // ✅ moved slightly right
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFocusPainted(false);
        navbar.add(logoutBtn);

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

        // WELCOME LABEL
        String userName = getUserName(Session.getEmail()); // fetch the name from DB
        JLabel welcomeLabel = new JLabel("Welcome, " + userName); // keep original casing
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBounds(20, 80, 400, 30);
        super.add(welcomeLabel);

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
// Make table non-editable
        transactionTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells non-editable
            }
        };
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // data font
        transactionTable.setRowHeight(30); // row height to fit font

// Put table inside scroll pane
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(20, 150, 900, 300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

// Customize table header font and center text
        transactionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        ((DefaultTableCellRenderer) transactionTable.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

// Center all table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        transactionTable.setDefaultRenderer(Object.class, centerRenderer);

// Load data
        loadTransactions(""); // your existing method

// Rename columns after loading
        transactionTable.getColumnModel().getColumn(0).setHeaderValue("TID");
        transactionTable.getColumnModel().getColumn(1).setHeaderValue("BID");
        transactionTable.getColumnModel().getColumn(2).setHeaderValue("Pay Method");
        transactionTable.getColumnModel().getColumn(3).setHeaderValue("Total Amount");
        transactionTable.getColumnModel().getColumn(4).setHeaderValue("Transaction Date");
        transactionTable.getColumnModel().getColumn(5).setHeaderValue("Pay Status");

// Refresh header to apply changes
        transactionTable.getTableHeader().repaint();

        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            loadTransactions(keyword);
        });

        // ================= TOGGLE PAID/PENDING =================
        JButton toggleBtn = new JButton("Toggle PAID/PENDING");
        toggleBtn.setFont(new Font("Segoe UI", Font.PLAIN, 24)); // 24pt font
        toggleBtn.setBackground(new Color(200, 0, 0));
        toggleBtn.setForeground(Color.WHITE);
        toggleBtn.setFocusPainted(false);

// Dynamically center-left: 1/4th of frame width minus half button width
        int toggleWidth = 300;
        int toggleHeight = 55;
        int frameWidth = getWidth();
        int toggleX = (frameWidth / 4) - (toggleWidth / 2);
        toggleBtn.setBounds(toggleX, 470, toggleWidth, toggleHeight);
        add(toggleBtn);

        toggleBtn.addActionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow >= 0) {
                int t_id = Integer.parseInt(transactionTable.getValueAt(selectedRow, 0).toString());
                String currentStatus = transactionTable.getValueAt(selectedRow, 5).toString();
                String newStatus = currentStatus.equalsIgnoreCase("PAID") ? "PENDING" : "PAID";
                updatePaymentStatus(t_id, newStatus);
                loadTransactions(searchField.getText().trim());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a transaction.");
            }
        });

// ================= MARK AS COMPLETED =================
        JButton completeBtn = new JButton("Mark as COMPLETED");
        completeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 24)); // 24pt font
        completeBtn.setBackground(new Color(0, 128, 0));
        completeBtn.setForeground(Color.WHITE);
        completeBtn.setFocusPainted(false);

// Dynamically center-right: 3/4th of frame width minus half button width
        int completeWidth = 300;
        int completeHeight = 55;
        int completeX = (frameWidth * 3 / 4) - (completeWidth / 2);
        completeBtn.setBounds(completeX, 470, completeWidth, completeHeight);
        add(completeBtn);

        completeBtn.addActionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow >= 0) {
                int t_id = Integer.parseInt(transactionTable.getValueAt(selectedRow, 0).toString());
                String paymentStatus = transactionTable.getValueAt(selectedRow, 5).toString();
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

    // ================= LOAD TRANSACTIONS =================
    private void loadTransactions(String keyword) {
        String sql = "SELECT t_id, b_id, payment_method, amount, transaction_date, payment_status FROM tbl_transactions";
        if (!keyword.isEmpty()) {
            sql += " WHERE CAST(t_id AS TEXT) LIKE ? "
                    + "OR CAST(b_id AS TEXT) LIKE ? "
                    + "OR payment_status LIKE ?";
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
        String sql = "UPDATE tbl_transactions SET payment_status = ? WHERE t_id = ?";
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
        String insertSQL = "INSERT INTO archive_transactions "
                + "(t_id, b_id, payment_method, amount, transaction_date, payment_status, archived_date) "
                + "SELECT t_id, b_id, payment_method, amount, transaction_date, payment_status, datetime('now') "
                + "FROM tbl_transactions WHERE t_id = ? AND payment_status = 'PAID'";

        String deleteSQL = "DELETE FROM tbl_transactions WHERE t_id = ? AND payment_status = 'PAID'";

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
