/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Main.Login;
import design.BaseFrame;
import User.Home;
import config.Session;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author ph2tn
 */
public class Movies extends BaseFrame {

    /**
     * Creates new form Movies
     */
    public Movies() {

        // Check session first
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "You need to login first.");
            new Login().setVisible(true); // send user to login
            dispose(); // close this frame
            return;   // stop constructor
        }

        initComponents();

        jPanel5.setLayout(new java.awt.GridLayout(0, 4, 20, 20));
        jPanel5.setBackground(new Color(207, 201, 235)); // light lavender

        jPanel5.add(createMovieCard("Movie 1", "/movies/1.jpg"));
        jPanel5.add(createMovieCard("Movie 2", "/movies/2.png"));
        jPanel5.add(createMovieCard("Movie 3", "/movies/3.jpg"));
        jPanel5.add(createMovieCard("Movie 4", "/movies/4.png"));
        

    }

    private JPanel createMovieCard(String title, String imagePath) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(180, 260));
        card.setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213)));

        JLabel poster = new JLabel();
        java.net.URL imgURL = getClass().getResource(imagePath);

        if (imgURL != null) {
            poster.setIcon(new ImageIcon(imgURL));
        } else {
            poster.setText("No Image");
            System.out.println("Image not found: " + imagePath);
        }

        poster.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel movieTitle = new JLabel(title);
        movieTitle.setHorizontalAlignment(SwingConstants.CENTER);
        movieTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        movieTitle.setForeground(new Color(158, 158, 158));

        JButton buyBtn = new JButton("Buy Tickets");
        buyBtn.setBackground(new Color(111, 106, 135));
        buyBtn.setForeground(Color.WHITE);
        buyBtn.setFocusPainted(false);

        buyBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Buying ticket for " + title);
        });

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);
        bottom.add(movieTitle, BorderLayout.NORTH);
        bottom.add(buyBtn, BorderLayout.SOUTH);

        card.add(poster, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        contactus = new javax.swing.JLabel();
        privacy = new javax.swing.JLabel();
        termofservice = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        contactus2 = new javax.swing.JLabel();
        privacy2 = new javax.swing.JLabel();
        termofservice2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jPanel3.setBackground(new java.awt.Color(33, 60, 112));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contactus.setBackground(new java.awt.Color(34, 61, 114));
        contactus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        contactus.setForeground(new java.awt.Color(216, 184, 86));
        contactus.setText("Contact Us");
        jPanel3.add(contactus, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 100, 20));

        privacy.setBackground(new java.awt.Color(55, 131, 245));
        privacy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        privacy.setForeground(new java.awt.Color(216, 184, 86));
        privacy.setText("Privacy Policy");
        jPanel3.add(privacy, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 110, 20));

        termofservice.setBackground(new java.awt.Color(34, 61, 114));
        termofservice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        termofservice.setForeground(new java.awt.Color(216, 184, 86));
        termofservice.setText("Terms of Service");
        jPanel3.add(termofservice, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 140, 20));

        jLabel2.setBackground(new java.awt.Color(216, 184, 86));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(216, 184, 86));
        jLabel2.setText("© 2026 MTB. All rights reserved.");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 230, 20));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(207, 201, 234));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/mtblogo3.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Profile");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 30, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Home");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Movies");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(207, 201, 234));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contactus2.setBackground(new java.awt.Color(34, 61, 114));
        contactus2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        contactus2.setText("Contact Us");
        jPanel6.add(contactus2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 100, 20));

        privacy2.setBackground(new java.awt.Color(55, 131, 245));
        privacy2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        privacy2.setText("Privacy Policy");
        jPanel6.add(privacy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 110, 20));

        termofservice2.setBackground(new java.awt.Color(34, 61, 114));
        termofservice2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        termofservice2.setText("Terms of Service");
        jPanel6.add(termofservice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 140, 20));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("© 2026 MTB. All rights reserved.");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 230, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        Profile Profile = new Profile();
        Profile.setVisible(true);
        Profile.pack();
        Profile.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

        Home Home = new Home();   // or your main frame
        Home.setVisible(true);
        Home.pack();
        Home.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        Movies Movies = new Movies();
        Movies.setVisible(true);
        Movies.pack();
        Movies.setLocationRelativeTo(null);
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
                new Movies().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contactus;
    private javax.swing.JLabel contactus2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel privacy;
    private javax.swing.JLabel privacy2;
    private javax.swing.JLabel termofservice;
    private javax.swing.JLabel termofservice2;
    // End of variables declaration//GEN-END:variables
}
