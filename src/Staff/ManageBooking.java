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

public class ManageBooking extends BaseFrame {

    private JTable bookingTable;
    private JTextField searchField;

    public ManageBooking() {
        super(951, 550);           // Use BaseFrame constructor with size
        setTitle("Manage Bookings");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
    }

    public void showBookingTable() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // ================= NAVBAR =================
        JPanel navbar = new JPanel();
        navbar.setBounds(0, 0, getWidth(), 60);
        navbar.setBackground(new Color(200, 0, 0));
        navbar.setLayout(null);

        JLabel title = new JLabel("Staff Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(20, 10, 300, 40);
        navbar.add(title);

        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.setBounds(150, 470, 200, 30);
        cancelBtn.setBackground(Color.DARK_GRAY);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        add(cancelBtn);

        cancelBtn.addActionListener(e -> cancelBooking());

        int btnHeight = 40;
        int startX = 240;
        int gap = 10;

        JButton manageArchiveBtn = new JButton("Archive Bookings");
        manageArchiveBtn.setBounds(startX, 10, 160, btnHeight);

        JButton manageCanceledBtn = new JButton("Canceled Bookings");
        manageCanceledBtn.setBounds(startX + 170, 10, 170, btnHeight);

        JButton manageBookingsBtn = new JButton("Bookings");
        manageBookingsBtn.setBounds(startX + 350, 10, 120, btnHeight);

        JButton manageTransactionsBtn = new JButton("Transactions");
        manageTransactionsBtn.setBounds(startX + 480, 10, 140, btnHeight);

        // LOGOUT (always right side)
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(860, 10, 80, 40);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFocusPainted(false);
        navbar.add(logoutBtn);

        for (JButton btn : new JButton[]{
            manageArchiveBtn,
            manageCanceledBtn,
            manageBookingsBtn,
            manageTransactionsBtn}) {

            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            navbar.add(btn);
        }

        add(navbar);

        // ================= NAVBAR ACTIONS =================
        // MANAGE BOOKINGS (refresh same page)
        manageBookingsBtn.addActionListener(e -> {
            dispose();
            new ManageBooking().showBookingTable();
        });

        // ARCHIVE BOOKINGS
        manageArchiveBtn.addActionListener(e -> {
            dispose();
            new ArchiveBookings().showArchiveTable();
        });

        // CANCELED BOOKINGS
        manageCanceledBtn.addActionListener(e -> {
            dispose();
            new CanceledBookings().showCanceledTable();
        });

        // MANAGE TRANSACTIONS
        manageTransactionsBtn.addActionListener(e -> {
            dispose();
            new ManageTransaction().showTransactionTable();
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Login().setVisible(true);
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
        bookingTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells non-editable
            }
        };
        bookingTable.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // data font size 14
        bookingTable.setRowHeight(25); // row height to fit font

// Center all table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        bookingTable.setDefaultRenderer(Object.class, centerRenderer);

// Put table inside scroll pane
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBounds(20, 150, 900, 300);
        add(scrollPane);

// Customize table header font and center text
        bookingTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        ((DefaultTableCellRenderer) bookingTable.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        loadBookings("");

        // Rename table columns
        bookingTable.getColumnModel().getColumn(0).setHeaderValue("BID");
        bookingTable.getColumnModel().getColumn(1).setHeaderValue("Movie Title");
        bookingTable.getColumnModel().getColumn(2).setHeaderValue("Seats");
        bookingTable.getColumnModel().getColumn(3).setHeaderValue("Total Price");
        bookingTable.getColumnModel().getColumn(4).setHeaderValue("Booking Stat");
        bookingTable.getColumnModel().getColumn(5).setHeaderValue("Book Date");

// Refresh header to apply changes
        bookingTable.getTableHeader().repaint();

        searchBtn.addActionListener(e -> {
            loadBookings(searchField.getText().trim());
        });

        // ================= BUTTONS =================
        JButton completedBtn = new JButton("Mark as COMPLETED");
        completedBtn.setBounds(600, 470, 200, 30);
        completedBtn.setBackground(new Color(0, 128, 0));
        completedBtn.setForeground(Color.WHITE);
        completedBtn.setFocusPainted(false);
        add(completedBtn);

        completedBtn.addActionListener(e -> markAsCompleted());

        JButton editBtn = new JButton("Edit Selected Booking");
        editBtn.setBounds(380, 470, 200, 30);
        editBtn.setBackground(new Color(200, 0, 0));
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);
        add(editBtn);

        editBtn.addActionListener(e -> editBooking());

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

    private void loadBookings(String keyword) {

        String sql = "SELECT b.b_id, m.movie_name, "
                + "GROUP_CONCAT(s.seat_no, ', ') AS seats, "
                + "b.total_price, b.booking_status, b.booking_date "
                + "FROM tbl_booking b "
                + "JOIN tbl_movies m ON b.m_id = m.m_id "
                + "LEFT JOIN booking_seats s ON b.b_id = s.b_id "
                + "GROUP BY b.b_id";

        if (!keyword.isEmpty()) {
            sql += " HAVING CAST(b.b_id AS TEXT) LIKE ? OR m.movie_name LIKE ?";
        }

        try (Connection conn = config.connectDB()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!");
                return;
            }

            PreparedStatement pst = conn.prepareStatement(sql);

            if (!keyword.isEmpty()) {
                String kw = "%" + keyword + "%";
                pst.setString(1, kw);
                pst.setString(2, kw);
            }

            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {   // check if empty
                JOptionPane.showMessageDialog(this, "No bookings found.");
            }

            bookingTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading bookings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= EDIT BOOKING =================
    private void editBooking() {

        int selectedRow = bookingTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select booking first.");
            return;
        }

        int b_id = Integer.parseInt(bookingTable.getValueAt(selectedRow, 0).toString());
        String currentPrice = bookingTable.getValueAt(selectedRow, 3).toString();

        JTextField priceField = new JTextField(currentPrice);

        Object[] message = {
            "Total Price:", priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message,
                "Edit Booking", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {

            int price = Integer.parseInt(priceField.getText());
            updateBooking(b_id, price);

            loadBookings(searchField.getText().trim());
        }
    }

    private void updateBooking(int b_id, int price) {

        String sql = "UPDATE tbl_booking SET total_price=? WHERE b_id=?";

        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, price);
            pst.setInt(2, b_id);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Booking updated!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ================= MARK AS COMPLETED =================
    private void markAsCompleted() {
        int selectedRow = bookingTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select booking first.");
            return;
        }

        int b_id = Integer.parseInt(bookingTable.getValueAt(selectedRow, 0).toString());

        try (Connection conn = config.connectDB()) {
            conn.setAutoCommit(false);

            // 1️⃣ Update status to COMPLETED
            String updateStatusSQL
                    = "UPDATE tbl_booking SET booking_status='COMPLETED' WHERE b_id=?";
            try (PreparedStatement pst = conn.prepareStatement(updateStatusSQL)) {
                pst.setInt(1, b_id);
                pst.executeUpdate();
            }

            // 2️⃣ Move to archive
            String insertSQL = "INSERT INTO archive_bookings "
                    + "(b_id, m_id, total_price, booking_status, booking_date, archived_date) "
                    + "SELECT b_id, m_id, total_price, booking_status, booking_date, datetime('now') "
                    + "FROM tbl_booking WHERE b_id = ?";
            String deleteSQL = "DELETE FROM tbl_booking WHERE b_id = ?";

            try (PreparedStatement insertPst = conn.prepareStatement(insertSQL);
                    PreparedStatement deletePst = conn.prepareStatement(deleteSQL)) {

                insertPst.setInt(1, b_id);
                int inserted = insertPst.executeUpdate();

                deletePst.setInt(1, b_id);
                deletePst.executeUpdate();

                if (inserted > 0) {
                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Booking marked as COMPLETED and moved to Archive!");
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Error moving booking to Archive.");
                }

                loadBookings(searchField.getText().trim());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void cancelBooking() {

        int selectedRow = bookingTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select booking first.");
            return;
        }

        int b_id = Integer.parseInt(
                bookingTable.getValueAt(selectedRow, 0).toString());

        String reason = JOptionPane.showInputDialog(
                this,
                "Enter cancellation reason:",
                "Cancel Booking",
                JOptionPane.PLAIN_MESSAGE
        );

        if (reason == null || reason.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cancellation reason required.");
            return;
        }

        config db = new config();
        boolean success = db.cancelBooking(b_id, reason);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Booking cancelled successfully!");
            loadBookings(searchField.getText().trim());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Cancellation failed.");
        }
    }
}
