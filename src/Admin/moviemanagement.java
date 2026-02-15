/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Main.Login;
import Main.landingpage;
import User.Profile;
import config.Session;
import config.config;
import design.BaseFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ph2tn
 */
public class moviemanagement extends BaseFrame {

    private config db = new config(); // make db available to all methods

    public moviemanagement() {

        // Check session first
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "You need to login first.");
            new Login().setVisible(true); // send user to login
            dispose(); // close this frame
            return;   // stop constructor
        }

        initComponents();

        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                search();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                search();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                search();
            }

            private void search() {
                String text = jTextField1.getText().trim();
                if (text.isEmpty()) {
                    loadMovies();
                } else {
                    filterMovies(text);
                }
            }
        });

        // ✅ SET MODEL FIRST
        movietable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Movie ID", "Movie Name", "Genre",
                    "Showtime", "Available Seats",
                    "Run Time"
                }
        ) {
            Class[] types = new Class[]{
                Integer.class, String.class, String.class,
                String.class, Integer.class, String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false; // table is read-only
            }

        });

        loadMovies();
        stylemovietable();
        fixTableLayout();

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
                filterMovies(search);
            }
        });

    }

    private boolean confirmAdminPassword() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Your Password:");
        JPasswordField passField = new JPasswordField(15);

        panel.add(label);
        panel.add(passField);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Admin Verification",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {

            String enteredPassword = new String(passField.getPassword());
            String loggedInEmail = Session.getEmail();

            String sql = "SELECT * FROM tbl_user WHERE u_email = ? AND u_pass = ? AND u_type = 'Admin'";

            try (Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");
                    PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setString(1, loggedInEmail);
                pst.setString(2, enteredPassword);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    return true; // correct password AND is admin
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password or not Admin!");
                    return false;
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
                return false;
            }
        }

        return false;
    }

    private void filterMovies(String keyword) {

        DefaultTableModel model = (DefaultTableModel) movietable.getModel();
        model.setRowCount(0);

        String url = "jdbc:sqlite:mtb.db";

        String sql = "SELECT * FROM tbl_movies WHERE "
                + "m_id LIKE ? OR movie_name LIKE ? OR genre LIKE ? "
                + "OR showtime LIKE ? OR available_seats LIKE ? OR run_time LIKE ?";

        try (Connection con = DriverManager.getConnection(url);
                PreparedStatement pst = con.prepareStatement(sql)) {

            // We have 6 LIKE conditions → set 6 parameters
            for (int i = 1; i <= 6; i++) {
                pst.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("m_id"),
                    rs.getString("movie_name"),
                    rs.getString("genre"),
                    rs.getString("showtime"),
                    rs.getInt("available_seats"),
                    rs.getString("run_time"),};
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void stylemovietable() {

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        movietable.getColumnModel().getColumn(0).setCellRenderer(center); // ID
        movietable.getColumnModel().getColumn(4).setCellRenderer(center); // Seats
        movietable.getColumnModel().getColumn(5).setCellRenderer(center); // Runtime

        movietable.setShowHorizontalLines(true);
        movietable.setShowVerticalLines(false);
        movietable.setRowHeight(32);

        movietable.setSelectionBackground(new Color(230, 240, 255));
        movietable.setSelectionForeground(Color.BLACK);
        center.setHorizontalAlignment(JLabel.CENTER);

        movietable.getColumnModel().getColumn(0).setCellRenderer(center);
        movietable.getColumnModel().getColumn(5).setCellRenderer(center);

        movietable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        movietable.getTableHeader().setBackground(
                new Color(45, 52, 54));

        movietable.getTableHeader().setForeground(Color.WHITE);
    }

    private void fixTableLayout() {

        // Let table stretch to fill the scrollpane width
        movietable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Optional: adjust relative widths (cleaner proportions)
        movietable.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
        movietable.getColumnModel().getColumn(1).setPreferredWidth(200);  // Name
        movietable.getColumnModel().getColumn(2).setPreferredWidth(150);  // Genre
        movietable.getColumnModel().getColumn(3).setPreferredWidth(120);  // Showtime
        movietable.getColumnModel().getColumn(4).setPreferredWidth(120);  // Seats
        movietable.getColumnModel().getColumn(5).setPreferredWidth(100);  // Runtime
    }

    public void loadMovies() {

        DefaultTableModel model = (DefaultTableModel) movietable.getModel();
        model.setRowCount(0);

        String sql = "SELECT * FROM tbl_movies";

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("m_id"),
                    rs.getString("movie_name"),
                    rs.getString("genre"),
                    rs.getString("showtime"),
                    rs.getInt("available_seats"),
                    rs.getString("run_time"),});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading movies: " + e.getMessage());
        }
    }

    private void addMovieAction() {

        MovieFormDialog dialog = new MovieFormDialog(this, "Add Movie");
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {

            if (db.addMovie(
                    dialog.getNameValue(),
                    dialog.getGenre(),
                    dialog.getShowtime(),
                    dialog.getSeats(),
                    dialog.getRuntime())) {

                JOptionPane.showMessageDialog(this, "Movie added successfully!");
                loadMovies();
            } else {
                JOptionPane.showMessageDialog(this, "Add failed!");
            }
        }
    }

    public void editMovieAction(int row) {

        int movieId = Integer.parseInt(movietable.getValueAt(row, 0).toString());
        String name = movietable.getValueAt(row, 1).toString();
        String genre = movietable.getValueAt(row, 2).toString();
        String showtime = movietable.getValueAt(row, 3).toString();
        int seats = Integer.parseInt(movietable.getValueAt(row, 4).toString());
        int runtime = Integer.parseInt(movietable.getValueAt(row, 5).toString());

        MovieFormDialog dialog = new MovieFormDialog(this, "Update Movie");
        dialog.setValues(name, genre, showtime, seats, runtime);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {

            if (db.updateMovie(
                    movieId,
                    dialog.getNameValue(),
                    dialog.getGenre(),
                    dialog.getShowtime(),
                    dialog.getSeats(),
                    dialog.getRuntime())) {

                JOptionPane.showMessageDialog(this, "Movie updated!");
                loadMovies();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed!");
            }
        }
    }

    public void deleteMovieAction(int row) {

        int movieId = Integer.parseInt(movietable.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this movie?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (db.deleteMovie(movieId)) {
                JOptionPane.showMessageDialog(this, "Movie deleted!");
                loadMovies();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed!");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        movietable = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        deletemovie = new javax.swing.JLabel();
        addmovie = new javax.swing.JLabel();
        updatemovie = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(961, 571));

        movietable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        movietable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Movie ID", "Movie Name", "Genre", "Showtime", "Available seats", "Run time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        movietable.setColumnSelectionAllowed(true);
        movietable.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(movietable);
        movietable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        deletemovie.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        deletemovie.setText("Delete Movie");
        deletemovie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletemovieMouseClicked(evt);
            }
        });

        addmovie.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        addmovie.setText("Add Movie");
        addmovie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addmovieMouseClicked(evt);
            }
        });

        updatemovie.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        updatemovie.setText("Update Movie");
        updatemovie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updatemovieMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(207, 201, 234));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/mtblogo3.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Dashboard");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Log out");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Movies");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Bookings ");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Users");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
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
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(44, 44, 44)
                .addComponent(jLabel5)
                .addGap(35, 35, 35)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(addmovie, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(updatemovie, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(deletemovie, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updatemovie, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(addmovie, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(deletemovie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

        String keyword = jTextField1.getText().trim();

        if (keyword.isEmpty()) {
            loadMovies();  // reload all
        } else {
            filterMovies(keyword);
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void addmovieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addmovieMouseClicked

        if (!confirmAdminPassword()) {
            return;
        }

        addMovieAction();
    }//GEN-LAST:event_addmovieMouseClicked

    private void updatemovieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatemovieMouseClicked

        int selectedRow = movietable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie first.");
            return;
        }

        if (!confirmAdminPassword()) {
            return;
        }

        editMovieAction(selectedRow);
    }//GEN-LAST:event_updatemovieMouseClicked

    private void deletemovieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletemovieMouseClicked

        int selectedRow = movietable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie first.");
            return;
        }

        if (!confirmAdminPassword()) {
            return;
        }

        deleteMovieAction(selectedRow);
    }//GEN-LAST:event_deletemovieMouseClicked

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
            // User confirmed logout
            landingpage landingpage = new landingpage();
            landingpage.setVisible(true);
            landingpage.pack();
            landingpage.setLocationRelativeTo(null);
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            if (!Session.isLoggedIn()) {
                JOptionPane.showMessageDialog(null, "You need to login first.");
                new Login().setVisible(true);
            } else {
                new moviemanagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addmovie;
    private javax.swing.JLabel deletemovie;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTable movietable;
    private javax.swing.JLabel updatemovie;
    // End of variables declaration//GEN-END:variables
}
