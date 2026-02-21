package Staff;

import Main.Login;
import config.config;
import design.BaseFrame;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

public class CanceledBookings extends BaseFrame {

    private JTable cancelledTable;
    private JTextField searchField;

    public CanceledBookings() {
        super(); // Use BaseFrame default size
        showCanceledTable();
    }

    public void showCanceledTable() {

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

        JButton archiveBtn = new JButton("Archive Bookings");
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
            new ArchiveBookings().showArchiveTable();
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
        JLabel pageTitle = new JLabel("Canceled & Refunded Bookings");
        pageTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        pageTitle.setBounds(20, 70, 400, 30);
        add(pageTitle);

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
        cancelledTable = new JTable();
        cancelledTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(cancelledTable);
        scrollPane.setBounds(20, 150, 900, 330);
        add(scrollPane);

        // ================= LOAD DATA =================
        Runnable loadData = () -> {

            String keyword = searchField.getText().trim();
            String sql = "SELECT * FROM tbl_canceled_booking";

            if (!keyword.isEmpty()) {
                sql += " WHERE CAST(b_id AS TEXT) LIKE ? OR CAST(m_id AS TEXT) LIKE ?";
            }

            try (Connection conn = config.connectDB();
                    PreparedStatement pst = conn.prepareStatement(sql)) {

                if (!keyword.isEmpty()) {
                    String kw = "%" + keyword + "%";
                    pst.setString(1, kw);
                    pst.setString(2, kw);
                }

                ResultSet rs = pst.executeQuery();
                cancelledTable.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error loading cancelled bookings: " + e.getMessage());
            }
        };

        searchBtn.addActionListener(e -> loadData.run());

        loadData.run();

        setVisible(true);
    }
}
