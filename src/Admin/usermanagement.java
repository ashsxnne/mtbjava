/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import CRUD.ActionButtonEditor;
import CRUD.ActionButtonRenderer;
import KiosksPages.HomeK;
import Main.Login;
import config.Session;
import design.BaseFrame;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ph2tn
 */
public class usermanagement extends BaseFrame {

    /**
     * Creates new form usermanagement1
     */
    public usermanagement() {
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
        jPanel7 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        usertable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(200, 0, 0));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Dashboard");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Log out");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Movies");
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Bookings ");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Users");
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
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
                .addContainerGap(213, Short.MAX_VALUE))
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

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

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

        jButton1.setBackground(new java.awt.Color(200, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Add Users");
        jButton1.setBorderPainted(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

            Session.logout();   // IMPORTANT

            Login login = new Login();
            login.setVisible(true);
            login.pack();
            login.setLocationRelativeTo(null);
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

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

        // add tag user ngari
        JDialog addDialog = new JDialog(this, "Add New User", true);
        addDialog.setSize(400, 300);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(null);
        addDialog.getContentPane().setBackground(Color.WHITE);

        // Labels and Fields
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 30, 100, 25);
        addDialog.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(140, 30, 200, 25);
        addDialog.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 100, 25);
        addDialog.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(140, 70, 200, 25);
        addDialog.add(passField);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(30, 110, 100, 25);
        addDialog.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(140, 110, 200, 25);
        addDialog.add(nameField);

        // Add User Button
        JButton submitBtn = new JButton("Add User");
        submitBtn.setBounds(140, 160, 120, 30);
        submitBtn.setBackground(new Color(200, 0, 0));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        addDialog.add(submitBtn);

        // Action listener for Add User
        submitBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String name = nameField.getText().trim();

            if (email.isEmpty() || pass.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(addDialog, "Please fill all fields!");
                return;
            }

            String url = "jdbc:sqlite:mtb.db";

            // Check if email exists and insert
            try (Connection con = DriverManager.getConnection(url)) {
                // Check existing email
                String checkSql = "SELECT * FROM tbl_user WHERE u_email = ?";
                PreparedStatement pstCheck = con.prepareStatement(checkSql);
                pstCheck.setString(1, email);
                ResultSet rs = pstCheck.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(addDialog, "Email already exists!");
                    return;
                }

                // Insert new user with default type and status
                String sql = "INSERT INTO tbl_user(u_email, u_pass, u_name, u_type, u_status) VALUES(?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, pass);
                pst.setString(3, name);
                pst.setString(4, "staff"); // default type
                pst.setInt(5, 0);          // status 0 = pending
                pst.executeUpdate();

                JOptionPane.showMessageDialog(addDialog, "User added successfully!");
                addDialog.dispose();
                loadUsers(); // refresh table
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Error: " + ex.getMessage());
            }
        });

        addDialog.setVisible(true);

    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new usermanagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable usertable;
    // End of variables declaration//GEN-END:variables
}
