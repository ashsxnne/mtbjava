package Staff;

import config.config;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ManageBooking {

    private JFrame frame;
    private JTable bookingTable;
    private JTextField searchField;

    public void showBookingTable() {
        frame = new JFrame("Manage Bookings");
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

        JLabel title = new JLabel("Manage Bookings");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(20, 10, 400, 40);
        navbar.add(title);

        frame.add(navbar);

        // SEARCH FIELD
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(20, 80, 60, 25);
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        frame.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(80, 80, 300, 25);
        frame.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(390, 80, 100, 25);
        searchBtn.setBackground(new Color(200, 0, 0));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        frame.add(searchBtn);

        // TABLE
        bookingTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBounds(20, 120, 900, 350);
        frame.add(scrollPane);

        loadBookings(""); // load all initially

        // SEARCH BUTTON ACTION
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            loadBookings(keyword);
        });

        // EDIT BUTTON (for seat_no or booking_fee)
        JButton editBtn = new JButton("Edit Selected Booking");
        editBtn.setBounds(600, 80, 200, 25);
        editBtn.setBackground(new Color(200, 0, 0));
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);
        frame.add(editBtn);

        editBtn.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow >= 0) {
                int b_id = Integer.parseInt(bookingTable.getValueAt(selectedRow, 0).toString());
                String currentSeat = bookingTable.getValueAt(selectedRow, 2).toString();
                String currentFee = bookingTable.getValueAt(selectedRow, 3).toString();

                JTextField seatField = new JTextField(currentSeat);
                JTextField feeField = new JTextField(currentFee);
                Object[] message = {
                        "Seat No:", seatField,
                        "Booking Fee:", feeField
                };

                int option = JOptionPane.showConfirmDialog(frame, message, "Edit Booking", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    updateBooking(b_id, seatField.getText(), Integer.parseInt(feeField.getText()));
                    loadBookings(searchField.getText().trim()); // reload table
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a booking to edit.");
            }
        });

        frame.setVisible(true);
    }

    // LOAD BOOKINGS FROM DB WITH OPTIONAL SEARCH
    private void loadBookings(String keyword) {
        String sql = "SELECT b_id, m_id, seat_no, booking_fee FROM tbl_booking";

        if (!keyword.isEmpty()) {
            sql += " WHERE b_id LIKE ? OR m_id LIKE ?";
        }

        try (Connection conn = config.connectDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            if (!keyword.isEmpty()) {
                String kw = "%" + keyword + "%";
                pst.setString(1, kw);
                pst.setString(2, kw);
            }

            try (ResultSet rs = pst.executeQuery()) {
                bookingTable.setModel(DbUtils.resultSetToTableModel(rs));
            }

        } catch (Exception e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }

    // UPDATE BOOKING
    private void updateBooking(int b_id, String seatNo, int fee) {
        String sql = "UPDATE tbl_booking SET seat_no = ?, booking_fee = ? WHERE b_id = ?";

        try (Connection conn = config.connectDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, seatNo);
            pst.setInt(2, fee);
            pst.setInt(3, b_id);

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(frame, "Booking updated successfully!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error updating booking: " + e.getMessage());
        }
    }
}
