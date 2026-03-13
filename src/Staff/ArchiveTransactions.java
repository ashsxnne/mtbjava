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

public class ArchiveTransactions extends BaseFrame {

    private JTable archiveTable;

    public ArchiveTransactions() {
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

        // ================= TABLE =================
        archiveTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table completely non-editable
            }
        };
        archiveTable.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // data font size 14
        archiveTable.setRowHeight(25); // row height to fit font

// Center all table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        archiveTable.setDefaultRenderer(Object.class, centerRenderer);

// Lock columns so they cannot be moved or resized
        archiveTable.getTableHeader().setReorderingAllowed(false);
        archiveTable.getTableHeader().setResizingAllowed(false);

// Put table inside scroll pane
        JScrollPane scrollPane = new JScrollPane(archiveTable);
        scrollPane.setBounds(20, 120, 900, 350);
        add(scrollPane);

// Customize table header font and center text
        archiveTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        ((DefaultTableCellRenderer) archiveTable.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        loadArchive();

        // Rename table columns
        if (archiveTable.getColumnModel().getColumnCount() >= 7) { // ensure columns exist
            archiveTable.getColumnModel().getColumn(0).setHeaderValue("Archive Transaction ID");
            archiveTable.getColumnModel().getColumn(1).setHeaderValue("TID");
            archiveTable.getColumnModel().getColumn(2).setHeaderValue("BID");
            archiveTable.getColumnModel().getColumn(3).setHeaderValue("Amount");
            archiveTable.getColumnModel().getColumn(4).setHeaderValue("Pay Method");
            archiveTable.getColumnModel().getColumn(5).setHeaderValue("Transaction Method");
            archiveTable.getColumnModel().getColumn(6).setHeaderValue("Archived Date");

            // Refresh header to apply changes
            archiveTable.getTableHeader().repaint();
        }

        setVisible(true);
    }

    // ================= LOAD ARCHIVE =================
    private void loadArchive() {

        String sql = "SELECT * FROM archive_transactions";

        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            archiveTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading archive: " + e.getMessage());
        }
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

}
