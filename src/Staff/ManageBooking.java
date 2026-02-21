package Staff;

import Main.Login;
import config.config;
import design.BaseFrame;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
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

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(860, 10, 80, 40);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFocusPainted(false);
        navbar.add(logoutBtn);

        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.setBounds(150, 470, 200, 30);
        cancelBtn.setBackground(Color.DARK_GRAY);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        add(cancelBtn);

        cancelBtn.addActionListener(e -> cancelBooking());

        // SAME STYLE AS ManageTransaction
        JButton manageArchiveBtn = new JButton("Archive Bookings");
        manageArchiveBtn.setBounds(350, 10, 180, 40);

        JButton manageBookingsBtn = new JButton("Manage Bookings");
        manageBookingsBtn.setBounds(540, 10, 150, 40);

        JButton manageTransactionsBtn = new JButton("Manage Transactions");
        manageTransactionsBtn.setBounds(710, 10, 180, 40);

        for (JButton btn : new JButton[]{
            manageArchiveBtn,
            manageBookingsBtn,
            manageTransactionsBtn}) {

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
            new CanceledBookings().showCanceledTable();
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

        // ================= WELCOME =================
        JLabel welcomeLabel = new JLabel("Welcome Staff");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
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
        bookingTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBounds(20, 150, 900, 300);
        add(scrollPane);

        loadBookings("");

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

    private void loadBookings(String keyword) {

        String sql = "SELECT b_id, m_id, seat_no, booking_fee, status FROM tbl_booking";

        if (!keyword.isEmpty()) {
            sql += " WHERE CAST(b_id AS TEXT) LIKE ? OR CAST(m_id AS TEXT) LIKE ?";
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
        String currentSeat = bookingTable.getValueAt(selectedRow, 2).toString();
        String currentFee = bookingTable.getValueAt(selectedRow, 3).toString();

        JTextField seatField = new JTextField(currentSeat);
        JTextField feeField = new JTextField(currentFee);

        Object[] message = {
            "Seat No:", seatField,
            "Booking Fee:", feeField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Booking",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            updateBooking(b_id, seatField.getText(),
                    Integer.parseInt(feeField.getText()));
            loadBookings(searchField.getText().trim());
        }
    }

    private void updateBooking(int b_id, String seatNo, int fee) {

        String sql = "UPDATE tbl_booking SET seat_no=?, booking_fee=? WHERE b_id=?";

        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, seatNo);
            pst.setInt(2, fee);
            pst.setInt(3, b_id);
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
            String updateStatusSQL = "UPDATE tbl_booking SET status = 'COMPLETED' WHERE b_id = ?";
            try (PreparedStatement pst = conn.prepareStatement(updateStatusSQL)) {
                pst.setInt(1, b_id);
                pst.executeUpdate();
            }

            // 2️⃣ Move to archive
            String insertSQL = "INSERT INTO tbl_archivebooking "
                    + "(b_id, m_id, seat_no, booking_fee, poster, status, booking_completed_at) "
                    + "SELECT b_id, m_id, seat_no, booking_fee, poster, status, datetime('now') "
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
