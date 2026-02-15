/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Main.Login;
import config.Session;
import design.BaseFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ph2tn
 */
public class bookings extends BaseFrame {

    /**
     * Creates new form bookings
     */
    public bookings() {

        // Check session first
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "You need to login first.");
            new Login().setVisible(true); // send user to login
            dispose(); // close this frame
            return;   // stop constructor
        }

        initComponents();

        setMenuIcon(jLabel8, "/imagesicons/over.png");       // Overview
        setMenuIcon(jLabel6, "/imagesicons/update.png");     // Update details
        setMenuIcon(jLabel10, "/imagesicons/booking.png");   // Bookings
        setMenuIcon(jLabel9, "/imagesicons/trans.png");      // Transactions
        setMenuIcon(jLabel12, "/imagesicons/watch.png");     // Watch lists
        setMenuIcon(jLabel11, "/imagesicons/log.png");    // Log out

        addHoverEffect(jLabel8);
        addHoverEffect(jLabel6);
        addHoverEffect(jLabel10);
        addHoverEffect(jLabel9);
        addHoverEffect(jLabel12);
        addHoverEffect(jLabel11);

        styleTable();

        bookedtable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "BookingID", "Movie Name", "Show Time", "Quantity",
                    "Seat No", "Canceled", "Actions"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // only Actions column editable
            }
        });

        bookedtable.setRowSelectionAllowed(true);
        bookedtable.setColumnSelectionAllowed(false);
        bookedtable.setCellSelectionEnabled(false);
        bookedtable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // THEN set renderer/editor
        bookedtable.getColumnModel().getColumn(0).setMinWidth(0);
        bookedtable.getColumnModel().getColumn(0).setMaxWidth(0);

        bookedtable.getColumnModel().getColumn(5).setMinWidth(0);
        bookedtable.getColumnModel().getColumn(5).setMaxWidth(0);

        // Set fixed width for Actions column
        bookedtable.getColumn("Actions").setPreferredWidth(180);
        bookedtable.getColumn("Actions").setMinWidth(180);
        bookedtable.getColumn("Actions").setMaxWidth(180);

        bookedtable.getColumn("Actions").setCellRenderer(new ActionRenderer());
        bookedtable.getColumn("Actions").setCellEditor(new ActionEditor(bookedtable));

        loadBookings();
    }

    class ActionRenderer extends javax.swing.JPanel
            implements javax.swing.table.TableCellRenderer {

        private final javax.swing.JButton cancelBtn = new javax.swing.JButton("Cancel");
        private final javax.swing.JButton watchedBtn = new javax.swing.JButton("Watched");

        public ActionRenderer() {

            setLayout(new java.awt.FlowLayout(
                    java.awt.FlowLayout.CENTER, 5, 5));

            setBackground(new java.awt.Color(245, 242, 255));

            cancelBtn.setFocusable(false);
            watchedBtn.setFocusable(false);

            cancelBtn.setBackground(new java.awt.Color(220, 53, 69));
            cancelBtn.setForeground(java.awt.Color.WHITE);

            watchedBtn.setBackground(new java.awt.Color(40, 167, 69));
            watchedBtn.setForeground(java.awt.Color.WHITE);

            add(cancelBtn);
            add(watchedBtn);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(new java.awt.Color(245, 242, 255));
            }

            return this;
        }
    }

    class ActionEditor extends javax.swing.AbstractCellEditor
            implements javax.swing.table.TableCellEditor {

        private final javax.swing.JPanel panel = new javax.swing.JPanel();
        private final javax.swing.JButton cancelBtn = new javax.swing.JButton("Cancel");
        private final javax.swing.JButton watchedBtn = new javax.swing.JButton("Watched");

        private int selectedRow;
        private javax.swing.JTable table;

        public ActionEditor(javax.swing.JTable table) {

            this.table = table;

            panel.setLayout(new java.awt.FlowLayout(
                    java.awt.FlowLayout.CENTER, 5, 5));

            panel.setBackground(new java.awt.Color(245, 242, 255));

            cancelBtn.setFocusable(false);
            watchedBtn.setFocusable(false);

            cancelBtn.setBackground(new java.awt.Color(220, 53, 69));
            cancelBtn.setForeground(java.awt.Color.WHITE);

            watchedBtn.setBackground(new java.awt.Color(40, 167, 69));
            watchedBtn.setForeground(java.awt.Color.WHITE);

            panel.add(cancelBtn);
            panel.add(watchedBtn);

            // CANCEL
            cancelBtn.addActionListener(e -> {
                int bookingId = (int) table.getValueAt(selectedRow, 0);

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Cancel this booking?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    updateBookingStatus(bookingId, 1, 0);
                    loadBookings();
                }

                fireEditingStopped();
            });

            // WATCHED
            watchedBtn.addActionListener(e -> {
                int bookingId = (int) table.getValueAt(selectedRow, 0);
                updateBookingStatus(bookingId, 0, 1);
                loadBookings();
                fireEditingStopped();
            });
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(
                javax.swing.JTable table, Object value,
                boolean isSelected, int row, int column) {

            selectedRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    private void loadBookings() {

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");

            String sql = "SELECT b.b_id, m.movie_name, m.showtime, "
                    + "b.quantity, b.seat_no, b.canceled "
                    + "FROM tbl_booking b "
                    + "JOIN tbl_movies m ON b.m_id = m.m_id "
                    + "JOIN tbl_user u ON b.u_id = u.u_id "
                    + "WHERE u.u_email = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Session.getEmail());

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model
                    = (DefaultTableModel) bookedtable.getModel();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("b_id"),
                    rs.getString("movie_name"),
                    rs.getString("showtime"),
                    rs.getInt("quantity"),
                    rs.getString("seat_no"),
                    rs.getInt("canceled"),
                    "Actions"
                });
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bookings.");
        }
    }

    private void addHoverEffect(javax.swing.JLabel label) {
        label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label.setForeground(new java.awt.Color(102, 0, 204)); // purple hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                label.setForeground(java.awt.Color.BLACK);
            }
        });
    }

    private void styleTable() {

        // ===== TABLE HEADER STYLE =====
        bookedtable.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        bookedtable.getTableHeader().setOpaque(false);
        bookedtable.getTableHeader().setBackground(new java.awt.Color(102, 0, 204)); // Purple header
        bookedtable.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); // White text
        bookedtable.getTableHeader().setBorder(null);

        // ===== TABLE BODY STYLE =====
        bookedtable.setRowHeight(40);
        bookedtable.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        bookedtable.setBackground(new java.awt.Color(245, 242, 255)); // Light purple background
        bookedtable.setForeground(new java.awt.Color(40, 40, 40));

        // Remove grid lines
        bookedtable.setShowGrid(false);
        bookedtable.setIntercellSpacing(new java.awt.Dimension(0, 0));

        // Selection color
        bookedtable.setSelectionBackground(new java.awt.Color(180, 150, 255));
        bookedtable.setSelectionForeground(java.awt.Color.BLACK);

        // Remove scroll border
        jScrollPane1.setBorder(null);
    }

    private void setMenuIcon(javax.swing.JLabel label, String path) {
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
        label.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        label.setIconTextGap(10);
    }

    private void updateBookingStatus(int bookingId, int canceled, int watched) {

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");

            String sql = "UPDATE tbl_booking SET canceled = ?, watched = ? WHERE b_id = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, canceled);
            pst.setInt(2, watched);
            pst.setInt(3, bookingId);

            pst.executeUpdate();

            pst.close();
            con.close();

            JOptionPane.showMessageDialog(this, "Booking updated!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating booking.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        contactus2 = new javax.swing.JLabel();
        privacy2 = new javax.swing.JLabel();
        termofservice2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookedtable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(207, 201, 234));
        jPanel8.setPreferredSize(new java.awt.Dimension(961, 578));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contactus2.setBackground(new java.awt.Color(34, 61, 114));
        contactus2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        contactus2.setText("Contact Us");
        jPanel8.add(contactus2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 100, 20));

        privacy2.setBackground(new java.awt.Color(55, 131, 245));
        privacy2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        privacy2.setText("Privacy Policy");
        jPanel8.add(privacy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 110, 20));

        termofservice2.setBackground(new java.awt.Color(34, 61, 114));
        termofservice2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        termofservice2.setText("Terms of Service");
        jPanel8.add(termofservice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 140, 20));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("© 2026 MTB. All rights reserved.");
        jPanel8.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 230, 20));

        jPanel5.setBackground(new java.awt.Color(207, 201, 234));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/mtblogo3.png"))); // NOI18N
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Profile");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Home");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Movies");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, -1, -1));

        jPanel3.setBackground(new java.awt.Color(207, 201, 234));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Update Details");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setText("Overview");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("Transactions");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("Bookings");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel11.setText("Log out");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel12.setText("Watch lists");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 37, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(37, 37, 37)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Your Booked Tickets");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Manage and view your upcoming movie experiences");

        bookedtable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bookedtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Movie Name", "Show Time", "Quantitiy", "Seat No", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        bookedtable.setColumnSelectionAllowed(true);
        bookedtable.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(bookedtable);
        bookedtable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 14, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked

        Profile Profile = new Profile();
        Profile.setVisible(true);
        Profile.pack();
        Profile.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

        Home Home = new Home();   // or your main frame
        Home.setVisible(true);
        Home.pack();
        Home.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        Movies Movies = new Movies();
        Movies.setVisible(true);
        Movies.pack();
        Movies.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

        updatedetails updatedetails = new updatedetails();
        updatedetails.setVisible(true);
        updatedetails.pack();
        updatedetails.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

        Profile Profile = new Profile();
        Profile.setVisible(true);
        Profile.pack();
        Profile.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            Session.logout(); // clear session

            JOptionPane.showMessageDialog(this, "Logged out successfully.");

            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

        transaction transaction = new transaction();
        transaction.setVisible(true);
        transaction.pack();
        transaction.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked

        bookings bookings = new bookings();
        bookings.setVisible(true);
        bookings.pack();
        bookings.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked

        watchlist watchlist = new watchlist();
        watchlist.setVisible(true);
        watchlist.pack();
        watchlist.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel12MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            if (!Session.isLoggedIn()) {
                JOptionPane.showMessageDialog(null, "You need to login first.");
                new Login().setVisible(true);
            } else {
                new bookings().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookedtable;
    private javax.swing.JLabel contactus2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel privacy2;
    private javax.swing.JLabel termofservice2;
    // End of variables declaration//GEN-END:variables
}
