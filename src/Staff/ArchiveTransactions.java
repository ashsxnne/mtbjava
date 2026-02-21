package Staff;

import Main.Login;
import config.Session;
import config.config;
import design.BaseFrame;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
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

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(860, 10, 80, 40);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFocusPainted(false);
        navbar.add(logoutBtn);

        JButton archiveBtn = new JButton("Archive Transactions");
        archiveBtn.setBounds(350, 10, 180, 40);

        JButton manageBookingsBtn = new JButton("Manage Bookings");
        manageBookingsBtn.setBounds(540, 10, 150, 40);

        JButton manageTransactionsBtn = new JButton("Manage Transactions");
        manageTransactionsBtn.setBounds(710, 10, 180, 40);

        for (JButton btn : new JButton[]{
            archiveBtn,
            manageBookingsBtn,
            manageTransactionsBtn}) {

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

        archiveBtn.addActionListener(e -> {
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

        // ================= WELCOME =================
        JLabel welcomeLabel = new JLabel("Welcome, " + Session.getEmail());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setBounds(20, 70, 400, 30);
        add(welcomeLabel);

        // ================= TABLE =================
        archiveTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(archiveTable);
        scrollPane.setBounds(20, 120, 900, 350);
        add(scrollPane);

        loadArchive();

        setVisible(true);
    }

    // ================= LOAD ARCHIVE =================
    private void loadArchive() {

        String sql = "SELECT * FROM tbl_archivetransaction";

        try (Connection conn = config.connectDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            archiveTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading archive: " + e.getMessage());
        }
    }
}
