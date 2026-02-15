package Admin;

import Main.landingpage;
import CRUD.ActionButtonEditor;
import CRUD.ActionButtonRenderer;
import Main.Login;
import User.Profile;
import config.Session;
import design.BaseFrame;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ph2tn
 */
public class usermanagement extends BaseFrame {

    /**
     * Creates new form usermanagement
     */
    public usermanagement() {

        /// Check session first
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "You need to login first.");
            new Login().setVisible(true); // send user to login
            dispose(); // close this frame
            return;   // stop constructor
        }

        initComponents();
        loadUsers();
        styleUserTable();

        usertable.getColumn("Action")
                .setCellRenderer(new ActionButtonRenderer());

        usertable.getColumn("Action")
                .setCellEditor(new ActionButtonEditor(usertable));

        usertable.setRowHeight(35);

        // ===== FIXED: Use jTextField1 instead of textField1 =====
        jTextField1.setText("Search users...");
        jTextField1.setForeground(Color.GRAY);
        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().equals("Search users...")) {
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().isEmpty()) {
                    jTextField1.setText("Search users...");
                    jTextField1.setForeground(Color.GRAY);
                }
            }
        });

        // Styling
        jTextField1.setBackground(Color.WHITE);
        jTextField1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Key listener for searching users
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String search = jTextField1.getText().trim();
                filterUsers(search);
            }
        });
    }

    private void filterUsers(String keyword) {
        DefaultTableModel model = (DefaultTableModel) usertable.getModel();
        model.setRowCount(0); // Clear table

        String url = "jdbc:sqlite:mtb.db";
        String currentUserEmail = Session.getEmail();

        String sql = "SELECT * FROM tbl_user WHERE u_email != ? AND "
                + "(u_id LIKE ? OR u_name LIKE ? OR u_email LIKE ? OR u_type LIKE ? OR u_status LIKE ?)";

        try (Connection con = DriverManager.getConnection(url);
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, currentUserEmail);
            pst.setString(2, "%" + keyword + "%");
            pst.setString(3, "%" + keyword + "%");
            pst.setString(4, "%" + keyword + "%");
            pst.setString(5, "%" + keyword + "%");
            pst.setString(6, "%" + keyword + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("u_id"),
                        rs.getString("u_name"),
                        rs.getString("u_email"),
                        rs.getString("u_pass"),
                        rs.getString("u_type"),
                        rs.getInt("u_status"),
                        "Edit"
                    };
                    model.addRow(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void styleUserTable() {

        usertable.setShowHorizontalLines(true);
        usertable.setShowVerticalLines(false);
        usertable.setRowHeight(32);

        usertable.setSelectionBackground(new Color(230, 240, 255));
        usertable.setSelectionForeground(Color.BLACK);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        usertable.getColumnModel().getColumn(0).setCellRenderer(center);
        usertable.getColumnModel().getColumn(5).setCellRenderer(center);

        usertable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        usertable.getTableHeader().setBackground(
                new Color(45, 52, 54));

        usertable.getTableHeader().setForeground(Color.WHITE);
    }

    public void loadUsers() {
        DefaultTableModel model = (DefaultTableModel) usertable.getModel();
        model.setRowCount(0); // clear table first

        String url = "jdbc:sqlite:mtb.db";
        String currentUserEmail = Session.getEmail(); // get logged-in user's email

        String sql = "SELECT * FROM tbl_user WHERE u_email != ?"; // exclude current user

        usertable.getColumnModel().getColumn(3).setMinWidth(0);
        usertable.getColumnModel().getColumn(3).setMaxWidth(0);

        try (Connection con = DriverManager.getConnection(url);
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, currentUserEmail);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("u_id"),
                        rs.getString("u_name"),
                        rs.getString("u_email"),
                        rs.getString("u_pass"),
                        rs.getString("u_type"),
                        rs.getInt("u_status"),
                        "Edit"
                    };
                    model.addRow(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        usertable = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        usertable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        usertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "User Id", "User FullName", "User Email", "User Pass", "Type", "Status", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        usertable.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(usertable);
        if (usertable.getColumnModel().getColumnCount() > 0) {
            usertable.getColumnModel().getColumn(6).setHeaderValue("Action");
        }

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(207, 201, 234));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/mtblogo3.png"))); // NOI18N

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setText("Dashboard");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setText("Log out");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setText("Movies");
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setText("Bookings ");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setText("Users");
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setText("Transactions");
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addGap(44, 44, 44)
                .addComponent(jLabel29)
                .addGap(35, 35, 35)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jLabel27)
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked

        AdminDashboard AdminDashboard = new AdminDashboard();   // or your main frame
        AdminDashboard.setVisible(true);
        AdminDashboard.pack();
        AdminDashboard.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked

        // Show confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // User confirmed logout
            landingpage landingpage = new landingpage();
            landingpage.setVisible(true);
            landingpage.pack();
            landingpage.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked

        moviemanagement moviemanagement = new moviemanagement();   // or your main frame
        moviemanagement.setVisible(true);
        moviemanagement.pack();
        moviemanagement.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked

        bookingmanagement bookingmanagement = new bookingmanagement();   // or your main frame
        bookingmanagement.setVisible(true);
        bookingmanagement.pack();
        bookingmanagement.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked

        usermanagement usermanagement = new usermanagement();
        usermanagement.setVisible(true);
        usermanagement.pack();
        usermanagement.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked

        transactionmanagement transactionmanagement = new transactionmanagement();   // or your main frame
        transactionmanagement.setVisible(true);
        transactionmanagement.pack();
        transactionmanagement.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel31MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            if (!Session.isLoggedIn()) {
                JOptionPane.showMessageDialog(null, "You need to login first.");
                new Login().setVisible(true);
            } else {
                new usermanagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable usertable;
    // End of variables declaration//GEN-END:variables
}
