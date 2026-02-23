/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import KiosksPages.HomeK;
import Main.Login;
import config.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ph2tn
 */
public class transactionmanagement extends javax.swing.JFrame {

    /**
     * Creates new form transactionmanagement1
     */
    public transactionmanagement() {
        initComponents();
        styleButtons();

        toggle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                togglePaymentStatus();
            }
        });

        markascompleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                markCompleted();
            }
        });
        loadTransactions();
        setupSearch();
    }

    private void loadTransactions() {

        DefaultTableModel model = (DefaultTableModel) transactiontable.getModel();
        model.setRowCount(0);

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");
                PreparedStatement pst = con.prepareStatement("SELECT * FROM tbl_transaction");
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("t_id"),
                    rs.getInt("b_id"),
                    rs.getInt("booking_fee"),
                    rs.getString("payment_status"),
                    rs.getString("payment_date"),
                    rs.getInt("cash"),
                    rs.getInt("change"),
                    rs.getInt("total_amount")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading transactions: " + e.getMessage());
        }
    }

    private void setupSearch() {

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                search();
            }

            public void removeUpdate(DocumentEvent e) {
                search();
            }

            public void changedUpdate(DocumentEvent e) {
                search();
            }

            private void search() {

                String keyword = jTextField1.getText().trim();

                DefaultTableModel model = (DefaultTableModel) transactiontable.getModel();
                model.setRowCount(0);

                String sql = "SELECT * FROM tbl_transaction WHERE "
                        + "t_id LIKE ? OR b_id LIKE ? OR payment_status LIKE ? "
                        + "OR payment_date LIKE ? OR total_amount LIKE ?";

                try (Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");
                        PreparedStatement pst = con.prepareStatement(sql)) {

                    for (int i = 1; i <= 5; i++) {
                        pst.setString(i, "%" + keyword + "%");
                    }

                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getInt("t_id"),
                            rs.getInt("b_id"),
                            rs.getInt("booking_fee"),
                            rs.getString("payment_status"),
                            rs.getString("payment_date"),
                            rs.getInt("cash"),
                            rs.getInt("change"),
                            rs.getInt("total_amount")
                        });
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void togglePaymentStatus() {

        int row = transactiontable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select transaction first!");
            return;
        }

        int id = Integer.parseInt(transactiontable.getValueAt(row, 0).toString());
        String currentStatus = transactiontable.getValueAt(row, 3).toString();

        String newStatus = currentStatus.equalsIgnoreCase("PAID") ? "PENDING" : "PAID";

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");
                PreparedStatement pst = con.prepareStatement(
                        "UPDATE tbl_transaction SET payment_status=? WHERE t_id=?")) {

            pst.setString(1, newStatus);
            pst.setInt(2, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Status updated to " + newStatus);
            loadTransactions();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void markCompleted() {

        int row = transactiontable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select transaction first!");
            return;
        }

        int id = Integer.parseInt(transactiontable.getValueAt(row, 0).toString());

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");
                PreparedStatement pst = con.prepareStatement(
                        "UPDATE tbl_transaction SET payment_status='COMPLETED' WHERE t_id=?")) {

            pst.setInt(1, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Transaction marked as COMPLETED");
            loadTransactions();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void styleButtons() {

        // ===== TOGGLE (RED) =====
        toggle.setOpaque(true);
        toggle.setBackground(new java.awt.Color(200, 0, 0)); // dark red
        toggle.setForeground(java.awt.Color.WHITE);
        toggle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toggle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        toggle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toggle.setBackground(new java.awt.Color(255, 0, 0)); // lighter red
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                toggle.setBackground(new java.awt.Color(200, 0, 0)); // normal red
            }
        });

        // ===== MARK AS COMPLETED (GREEN) =====
        markascompleted.setOpaque(true);
        markascompleted.setBackground(new java.awt.Color(0, 153, 51)); // dark green
        markascompleted.setForeground(java.awt.Color.WHITE);
        markascompleted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        markascompleted.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        markascompleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                markascompleted.setBackground(new java.awt.Color(0, 200, 0)); // lighter green
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                markascompleted.setBackground(new java.awt.Color(0, 153, 51)); // normal green
            }

        });

        // ===== ARCHIVE (BLUE) =====
        jLabel10.setOpaque(true);
        jLabel10.setBackground(new java.awt.Color(0, 123, 255)); // professional blue
        jLabel10.setForeground(java.awt.Color.WHITE);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10.setBackground(new java.awt.Color(0, 150, 255)); // lighter blue
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10.setBackground(new java.awt.Color(0, 123, 255)); // normal blue
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        archivetransaction = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactiontable = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        toggle = new javax.swing.JLabel();
        markascompleted = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        archivetransaction.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(200, 0, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Dashboard");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Log out");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Movies");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bookings ");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Users");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Transactions");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(39, 39, 39)
                .addComponent(jLabel5)
                .addGap(35, 35, 35)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(84, 84, 84))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transactiontable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        transactiontable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Transaction ID", "Booking ID", "Booking Fee", "Payment Status", "Payment Date", "Cash", "Change", "Total Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        transactiontable.setColumnSelectionAllowed(true);
        transactiontable.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(transactiontable);
        transactiontable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        toggle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        toggle.setText("Toggle PAID/PENDING");

        markascompleted.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        markascompleted.setText("Mark as COMPLETED");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Archive Transactions");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout archivetransactionLayout = new javax.swing.GroupLayout(archivetransaction);
        archivetransaction.setLayout(archivetransactionLayout);
        archivetransactionLayout.setHorizontalGroup(
            archivetransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(archivetransactionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(archivetransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(archivetransactionLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(toggle, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(markascompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        archivetransactionLayout.setVerticalGroup(
            archivetransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(archivetransactionLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(archivetransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toggle, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(markascompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(archivetransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(archivetransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked

        AdminDashboard AdminDashboard = new AdminDashboard();   // or your main frame
        AdminDashboard.setVisible(true);
        AdminDashboard.pack();
        AdminDashboard.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

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
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        moviemanagement moviemanagement = new moviemanagement();   // or your main frame
        moviemanagement.setVisible(true);
        moviemanagement.pack();
        moviemanagement.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked

        bookingmanagement bookingmanagement = new bookingmanagement();   // or your main frame
        bookingmanagement.setVisible(true);
        bookingmanagement.pack();
        bookingmanagement.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

        usermanagement usermanagement = new usermanagement();
        usermanagement.setVisible(true);
        usermanagement.pack();
        usermanagement.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        transactionmanagement transactionmanagement = new transactionmanagement();   // or your main frame
        transactionmanagement.setVisible(true);
        transactionmanagement.pack();
        transactionmanagement.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked

        archivetransaction archivetransaction = new archivetransaction();
        archivetransaction.setVisible(true);
        archivetransaction.pack();
        archivetransaction.setLocationRelativeTo(null);
        this.dispose();

    }//GEN-LAST:event_jLabel10MouseClicked

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel archivetransaction;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel markascompleted;
    private javax.swing.JLabel toggle;
    private javax.swing.JTable transactiontable;
    // End of variables declaration//GEN-END:variables
}
