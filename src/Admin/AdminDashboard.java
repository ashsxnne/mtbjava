package Admin;

import Main.landingpage;
import design.BaseFrame;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author PC14
 */
public class AdminDashboard extends BaseFrame {

    public AdminDashboard() {
        initComponents();

        setupStatCard(cardUsers, new Color(120, 190, 255), "Customers", "👥", usersCount);
        setupStatCard(cardBookings, new Color(255, 170, 120), "Bookings", "🎟️", bookingsCount);
        setupStatCard(cardMovies, new Color(207, 201, 234), "Movies", "🎬", moviesCount);

        loadDashboardCounts();

    }

    private void setupStatCard(
            JPanel card,
            Color accent,
            String title,
            String iconText,
            JLabel countLabel
    ) {
        card.removeAll();

        // ✅ RESTORE SIZE (THIS FIXES MISSING USERS CARD)
        card.setPreferredSize(new java.awt.Dimension(260, 323));

        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(accent, 2));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(iconText);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        icon.setBounds(20, 15, 40, 40);
        icon.setForeground(accent);

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(new Color(120, 120, 120));
        titleLabel.setBounds(20, 55, 200, 20);

        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        countLabel.setForeground(accent);
        countLabel.setBounds(20, 80, 200, 50);
        countLabel.setText("0");

        card.add(icon);
        card.add(titleLabel);
        card.add(countLabel);

        card.revalidate();
        card.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cardBookings = new javax.swing.JPanel();
        bookingsCount = new javax.swing.JLabel();
        cardUsers = new javax.swing.JPanel();
        usersCount = new javax.swing.JLabel();
        cardMovies = new javax.swing.JPanel();
        moviesCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(961, 578));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(207, 201, 234));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/mtblogo3.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Admin Dashboard");
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
        jLabel4.setText("Movie management");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Booking management");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("User management");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
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
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(28, Short.MAX_VALUE))
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
                    .addComponent(jLabel6))
                .addContainerGap(26, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        cardBookings.setPreferredSize(new java.awt.Dimension(260, 323));

        bookingsCount.setText("bookingsCount");

        javax.swing.GroupLayout cardBookingsLayout = new javax.swing.GroupLayout(cardBookings);
        cardBookings.setLayout(cardBookingsLayout);
        cardBookingsLayout.setHorizontalGroup(
            cardBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardBookingsLayout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(bookingsCount)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        cardBookingsLayout.setVerticalGroup(
            cardBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardBookingsLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(bookingsCount)
                .addContainerGap(198, Short.MAX_VALUE))
        );

        usersCount.setText("usersCount");

        javax.swing.GroupLayout cardUsersLayout = new javax.swing.GroupLayout(cardUsers);
        cardUsers.setLayout(cardUsersLayout);
        cardUsersLayout.setHorizontalGroup(
            cardUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardUsersLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(usersCount)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        cardUsersLayout.setVerticalGroup(
            cardUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardUsersLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(usersCount)
                .addContainerGap(192, Short.MAX_VALUE))
        );

        cardMovies.setPreferredSize(new java.awt.Dimension(260, 323));

        moviesCount.setText("moviesCount");

        javax.swing.GroupLayout cardMoviesLayout = new javax.swing.GroupLayout(cardMovies);
        cardMovies.setLayout(cardMoviesLayout);
        cardMoviesLayout.setHorizontalGroup(
            cardMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardMoviesLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(moviesCount)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        cardMoviesLayout.setVerticalGroup(
            cardMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardMoviesLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(moviesCount)
                .addContainerGap(219, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(cardUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(cardBookings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cardMovies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardBookings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardMovies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadDashboardCounts() {
        try (Connection conn = config.connectDB()) {

            String userSQL = "SELECT COUNT(*) FROM tbl_user WHERE u_type='customer'";
            PreparedStatement psUser = conn.prepareStatement(userSQL);
            ResultSet rsUser = psUser.executeQuery();
            if (rsUser.next()) {
                animateCount(usersCount, rsUser.getInt(1));
            }

            String bookingSQL = "SELECT COUNT(*) FROM tbl_booking";
            PreparedStatement psBooking = conn.prepareStatement(bookingSQL);
            ResultSet rsBooking = psBooking.executeQuery();
            if (rsBooking.next()) {
                animateCount(bookingsCount, rsBooking.getInt(1));
            }

            String movieSQL = "SELECT COUNT(*) FROM tbl_movies";
            PreparedStatement psMovie = conn.prepareStatement(movieSQL);
            ResultSet rsMovie = psMovie.executeQuery();
            if (rsMovie.next()) {
                animateCount(moviesCount, rsMovie.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createStatCard(
            Color accent,
            String title,
            String iconText,
            JLabel countLabel
    ) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(accent, 2));
        card.setPreferredSize(new java.awt.Dimension(240, 160));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(iconText);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        icon.setBounds(20, 15, 40, 40);
        icon.setForeground(accent);

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(new Color(120, 120, 120));
        titleLabel.setBounds(20, 55, 200, 20);

        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        countLabel.setForeground(accent);
        countLabel.setBounds(20, 80, 200, 50);
        countLabel.setText("0");

        card.add(icon);
        card.add(titleLabel);
        card.add(countLabel);

        // 🔥 Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(245, 243, 255));
            }

            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }
        });

        return card;
    }

    private void animateCount(JLabel label, int target) {
        new Thread(() -> {
            int count = 0;
            while (count <= target) {
                final int value = count;
                javax.swing.SwingUtilities.invokeLater(()
                        -> label.setText(String.valueOf(value))
                );
                count++;
                try {
                    Thread.sleep(15);
                } catch (Exception e) {
                }
            }
        }).start();
    }


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

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

        usermanagement usermanagement = new usermanagement();
        usermanagement.setVisible(true);
        usermanagement.pack();
        usermanagement.setLocationRelativeTo(null);
        this.dispose();

    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        
        
        AdminDashboard AdminDashboard = new AdminDashboard();   // or your main frame
            AdminDashboard.setVisible(true);
            AdminDashboard.pack();
            AdminDashboard.setLocationRelativeTo(null);
            this.dispose();

    }//GEN-LAST:event_jLabel2MouseClicked

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
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bookingsCount;
    private javax.swing.JPanel cardBookings;
    private javax.swing.JPanel cardMovies;
    private javax.swing.JPanel cardUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel moviesCount;
    private javax.swing.JLabel usersCount;
    // End of variables declaration//GEN-END:variables
}
