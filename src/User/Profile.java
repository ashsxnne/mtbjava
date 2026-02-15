/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Main.Login;
import design.BaseFrame;
import Main.landingpage;
import config.Session;
import config.config;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ph2tn
 */
public class Profile extends BaseFrame {

    public Profile() {

        // Check session first
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "You need to login first.");
            new Login().setVisible(true); // send user to login
            dispose(); // close this frame
            return;   // stop constructor
        }
        initComponents();

        setupCard(cardUserBookings, "BOOKINGS", "🎟", userBookingsCount, new java.awt.Color(66, 133, 244));
        setupCard(cardUserWatchlist, "WATCHLIST", "🎬", userWatchlistCount, new java.awt.Color(255, 167, 38));
        setupCard(cardUserTransactions, "TRANSACTIONS", "💳", userTransactionsCount, new java.awt.Color(156, 39, 176));

        applyThemeToCards();
        loadUserStats();

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

        System.out.println("SESSION EMAIL = " + Session.userEmail);
        showUserName();
    }

    private void showUserName() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = config.connectDB(); // use your config class
            if (conn == null) {
                jLabel7.setText("User");
                return;
            }

            // Replace with the currently logged-in user's email or ID
            String userEmail = Session.userEmail;

            String sql = "SELECT u_name FROM tbl_user WHERE u_email = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userEmail);

            rs = pst.executeQuery();

            if (rs.next()) {
                String fullName = rs.getString("u_name");
                jLabel7.setText(fullName);
            } else {
                jLabel7.setText("User");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jLabel7.setText("Hello, User");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
    }

    private void setMenuIcon(javax.swing.JLabel label, String path) {
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
        label.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        label.setIconTextGap(10);
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

    private void applyThemeToCards() {

        jPanel4.setBackground(new Color(207, 201, 235));

        styleCard(cardUserBookings, new java.awt.Color(66, 133, 244));   // Blue
        styleCard(cardUserWatchlist, new java.awt.Color(255, 167, 38));  // Orange
        styleCard(cardUserTransactions, new java.awt.Color(156, 39, 176)); // Purple

    }

    private void styleCard(JPanel card, java.awt.Color accentColor) {

        card.setBackground(Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createLineBorder(accentColor, 2));

        // Adjusted size to fit 961x578 frame perfectly
        card.setPreferredSize(new java.awt.Dimension(210, 140));
        card.setMinimumSize(new java.awt.Dimension(210, 140));
        card.setMaximumSize(new java.awt.Dimension(210, 140));
    }

    private void setupCard(
            JPanel card,
            String title,
            String iconText,
            JLabel countLabel,
            java.awt.Color accent
    ) {
        card.removeAll();
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createLineBorder(accent, 2));

        // Icon
        JLabel icon = new JLabel(iconText);
        icon.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 20));
        icon.setBounds(15, 10, 40, 30);
        icon.setForeground(accent);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        titleLabel.setForeground(new java.awt.Color(120, 120, 120));
        titleLabel.setBounds(15, 40, 200, 20);

        // Big Number
        countLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        countLabel.setForeground(accent);
        countLabel.setBounds(15, 65, 180, 35);


        card.add(icon);
        card.add(titleLabel);
        card.add(countLabel);

        card.repaint();
    }

    private void loadUserStats() {

        try (Connection conn = config.connectDB()) {

            String email = Session.userEmail;

            // Get user ID first
            String userSQL = "SELECT u_id FROM tbl_user WHERE u_email = ?";
            PreparedStatement psUser = conn.prepareStatement(userSQL);
            psUser.setString(1, email);
            ResultSet rsUser = psUser.executeQuery();

            if (rsUser.next()) {

                int userId = rsUser.getInt("u_id");

                // 1️⃣ Bookings count
                String bookingSQL = "SELECT COUNT(*) FROM tbl_booking WHERE u_id = ?";
                PreparedStatement psBooking = conn.prepareStatement(bookingSQL);
                psBooking.setInt(1, userId);
                ResultSet rsBooking = psBooking.executeQuery();
                if (rsBooking.next()) {
                    userBookingsCount.setText(String.valueOf(rsBooking.getInt(1)));
                }

                // 2️⃣ Watchlist count
                String watchSQL = "SELECT COUNT(*) FROM tbl_watchlist WHERE u_id = ?";
                PreparedStatement psWatch = conn.prepareStatement(watchSQL);
                psWatch.setInt(1, userId);
                ResultSet rsWatch = psWatch.executeQuery();
                if (rsWatch.next()) {
                    userWatchlistCount.setText(String.valueOf(rsWatch.getInt(1)));
                }

                // 3️⃣ Transactions count
                // 3️⃣ Transactions count
                String transSQL
                        = "SELECT COUNT(*) "
                        + "FROM tbl_transaction t "
                        + "JOIN tbl_booking b ON t.b_id = b.b_id "
                        + "WHERE b.u_id = ?";

                PreparedStatement psTrans = conn.prepareStatement(transSQL);
                psTrans.setInt(1, userId);
                ResultSet rsTrans = psTrans.executeQuery();

                if (rsTrans.next()) {
                    userTransactionsCount.setText(String.valueOf(rsTrans.getInt(1)));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        contactus2 = new javax.swing.JLabel();
        privacy2 = new javax.swing.JLabel();
        termofservice2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cardUserBookings = new javax.swing.JPanel();
        userBookingsCount = new javax.swing.JLabel();
        cardUserWatchlist = new javax.swing.JPanel();
        userWatchlistCount = new javax.swing.JLabel();
        cardUserTransactions = new javax.swing.JPanel();
        userTransactionsCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(961, 578));
        jPanel1.setVerifyInputWhenFocusTarget(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(961, 578));

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

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("Bookings");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel11.setText("Log out");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel12.setText("Watch lists");

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

        jPanel4.setBackground(new java.awt.Color(204, 199, 232));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/user.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel7.setText("User name");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel14.setText("Hello,");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("ACTIVE");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel15)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel15))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        userBookingsCount.setText("jLabel16");

        javax.swing.GroupLayout cardUserBookingsLayout = new javax.swing.GroupLayout(cardUserBookings);
        cardUserBookings.setLayout(cardUserBookingsLayout);
        cardUserBookingsLayout.setHorizontalGroup(
            cardUserBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardUserBookingsLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(userBookingsCount)
                .addGap(54, 54, 54))
        );
        cardUserBookingsLayout.setVerticalGroup(
            cardUserBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardUserBookingsLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(userBookingsCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        userWatchlistCount.setText("jLabel17");

        javax.swing.GroupLayout cardUserWatchlistLayout = new javax.swing.GroupLayout(cardUserWatchlist);
        cardUserWatchlist.setLayout(cardUserWatchlistLayout);
        cardUserWatchlistLayout.setHorizontalGroup(
            cardUserWatchlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardUserWatchlistLayout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addComponent(userWatchlistCount)
                .addGap(52, 52, 52))
        );
        cardUserWatchlistLayout.setVerticalGroup(
            cardUserWatchlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardUserWatchlistLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(userWatchlistCount)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        userTransactionsCount.setText("jLabel18");

        javax.swing.GroupLayout cardUserTransactionsLayout = new javax.swing.GroupLayout(cardUserTransactions);
        cardUserTransactions.setLayout(cardUserTransactionsLayout);
        cardUserTransactionsLayout.setHorizontalGroup(
            cardUserTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardUserTransactionsLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(userTransactionsCount)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        cardUserTransactionsLayout.setVerticalGroup(
            cardUserTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardUserTransactionsLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(userTransactionsCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cardUserBookings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cardUserWatchlist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cardUserTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cardUserBookings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cardUserWatchlist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cardUserTransactions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
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

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

        Profile Profile = new Profile();
        Profile.setVisible(true);
        Profile.pack();
        Profile.setLocationRelativeTo(null);
        this.dispose();


    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked


    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

        updatedetails updatedetails = new updatedetails();
        updatedetails.setVisible(true);
        updatedetails.pack();
        updatedetails.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            if (!Session.isLoggedIn()) {
                JOptionPane.showMessageDialog(null, "You need to login first.");
                new Login().setVisible(true);
            } else {
                new Profile().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardUserBookings;
    private javax.swing.JPanel cardUserTransactions;
    private javax.swing.JPanel cardUserWatchlist;
    private javax.swing.JLabel contactus2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel privacy2;
    private javax.swing.JLabel termofservice2;
    private javax.swing.JLabel userBookingsCount;
    private javax.swing.JLabel userTransactionsCount;
    private javax.swing.JLabel userWatchlistCount;
    // End of variables declaration//GEN-END:variables
}
