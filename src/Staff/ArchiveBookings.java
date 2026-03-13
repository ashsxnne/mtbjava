package Staff;

import Main.Login;
import config.config;
import design.BaseFrame;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
import net.proteanit.sql.DbUtils;

public class ArchiveBookings extends BaseFrame {

    private JTable archiveTable;
    private JTextField searchField;

    public ArchiveBookings() {
        super(); // use BaseFrame default size
        showArchiveTable();
    }

    public void showArchiveTable() {

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
        
        
        int btnHeight = 40;
        int startX = 240; // distance from left edge (after the title)
        int gap = 10;

// Button widths
        int archiveBtnWidth = 160;
        int canceledBtnWidth = 170;
        int bookingsBtnWidth = 120;
        int transactionsBtnWidth = 140;

// Archive Bookings
        JButton manageArchiveBtn = new JButton("Archive Bookings");
        manageArchiveBtn.setBounds(startX, 10, archiveBtnWidth, btnHeight);
        navbar.add(manageArchiveBtn);

// Canceled Bookings
        JButton manageCanceledBtn = new JButton("Canceled Bookings");
        manageCanceledBtn.setBounds(startX + archiveBtnWidth + gap, 10, canceledBtnWidth, btnHeight);
        navbar.add(manageCanceledBtn);

// Bookings
        JButton manageBookingsBtn = new JButton("Bookings");
        manageBookingsBtn.setBounds(startX + archiveBtnWidth + canceledBtnWidth + 2 * gap, 10, bookingsBtnWidth, btnHeight);
        navbar.add(manageBookingsBtn);

// Transactions
        JButton manageTransactionsBtn = new JButton("Transactions");
        manageTransactionsBtn.setBounds(startX + archiveBtnWidth + canceledBtnWidth + bookingsBtnWidth + 3 * gap, 10, transactionsBtnWidth, btnHeight);
        navbar.add(manageTransactionsBtn);

// LOGOUT (always right side)
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(860, 10, 80, btnHeight);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFocusPainted(false);
        navbar.add(logoutBtn);

// Style all buttons consistently
        for (JButton btn : new JButton[]{
            manageArchiveBtn,
            manageCanceledBtn,
            manageBookingsBtn,
            manageTransactionsBtn}) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
        }

        add(navbar);

        // ================= NAVIGATION =================
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
            new ArchiveBookings().showArchiveTable();
        });
        
        // CANCELED BOOKINGS
        manageCanceledBtn.addActionListener(e -> {
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

        // ================= TITLE =================
        JLabel archiveLabel = new JLabel("Completed / Archived Bookings");
        archiveLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        archiveLabel.setBounds(20, 70, 400, 30);
        add(archiveLabel);

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

        searchBtn.addActionListener(e -> {
            loadArchiveBookings(searchField.getText().trim());
        });

        // ================= TABLE =================
        archiveTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // table not editable
            }
        };

        archiveTable.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // table data size
        archiveTable.setRowHeight(25);

// center all table data
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        archiveTable.setDefaultRenderer(Object.class, centerRenderer);

// header style
        archiveTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        ((DefaultTableCellRenderer) archiveTable.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

// prevent column movement
        archiveTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(archiveTable);
        scrollPane.setBounds(20, 150, 900, 350);
        add(scrollPane);

        loadArchiveBookings("");

        setVisible(true);
    }

    // ================= LOAD ARCHIVE BOOKINGS =================
    private void loadArchiveBookings(String keyword) {

        String sql = "SELECT archive_id, b_id, m_id, total_price, booking_status, booking_date, archived_date FROM archive_bookings";

        if (!keyword.isEmpty()) {
            sql += " WHERE CAST(b_id AS TEXT) LIKE ? "
                    + "OR CAST(m_id AS TEXT) LIKE ? "
                    + "OR booking_status LIKE ?";
        }

        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            if (!keyword.isEmpty()) {
                String kw = "%" + keyword + "%";
                pst.setString(1, kw);
                pst.setString(2, kw);
                pst.setString(3, kw);
            }

            ResultSet rs = pst.executeQuery();
            archiveTable.setModel(DbUtils.resultSetToTableModel(rs));

            // rename column titles
            archiveTable.getColumnModel().getColumn(0).setHeaderValue("Archive ID");
            archiveTable.getColumnModel().getColumn(1).setHeaderValue("BID");
            archiveTable.getColumnModel().getColumn(2).setHeaderValue("MID");
            archiveTable.getColumnModel().getColumn(3).setHeaderValue("Total Price");
            archiveTable.getColumnModel().getColumn(4).setHeaderValue("Book Stat");
            archiveTable.getColumnModel().getColumn(5).setHeaderValue("Book Date");
            archiveTable.getColumnModel().getColumn(6).setHeaderValue("Archived Date");

            archiveTable.getTableHeader().repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading archive bookings: " + e.getMessage());
        }
    }
}
