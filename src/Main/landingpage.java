
package Main;

import design.BaseFrame;

public class landingpage extends BaseFrame {

    public landingpage() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        movie1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        viewbutton = new javax.swing.JButton();
        movie2 = new javax.swing.JLabel();
        movie3 = new javax.swing.JLabel();
        movie4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        contactus2 = new javax.swing.JLabel();
        privacy2 = new javax.swing.JLabel();
        termofservice2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(961, 578));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        movie1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movies/1.jpg"))); // NOI18N
        jPanel1.add(movie1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 210, 270));

        jLabel6.setBackground(new java.awt.Color(216, 184, 86));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(207, 201, 234));
        jLabel6.setText("Now Showing");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 260, 50));

        viewbutton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        viewbutton.setForeground(new java.awt.Color(34, 61, 114));
        viewbutton.setText("View all");
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        jPanel1.add(viewbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 120, 130, 40));

        movie2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movies/2.png"))); // NOI18N
        movie2.setText("jLabel2");
        jPanel1.add(movie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 210, 270));

        movie3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movies/3.jpg"))); // NOI18N
        movie3.setText("jLabel2");
        jPanel1.add(movie3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 190, 270));

        movie4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movies/4.png"))); // NOI18N
        movie4.setText("jLabel2");
        jPanel1.add(movie4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, 210, 270));

        jPanel2.setBackground(new java.awt.Color(207, 201, 234));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/mtblogo3.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Home");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Movies");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setText("Sign in");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 30, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 99));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Buy Tickets");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 470, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Buy Tickets");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Buy Tickets");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 470, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Buy Tickets");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, -1, -1));

        jPanel6.setBackground(new java.awt.Color(207, 201, 234));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contactus2.setBackground(new java.awt.Color(34, 61, 114));
        contactus2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        contactus2.setText("Contact Us");
        jPanel6.add(contactus2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 100, 20));

        privacy2.setBackground(new java.awt.Color(55, 131, 245));
        privacy2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        privacy2.setText("Privacy Policy");
        jPanel6.add(privacy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 110, 20));

        termofservice2.setBackground(new java.awt.Color(34, 61, 114));
        termofservice2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        termofservice2.setText("Terms of Service");
        jPanel6.add(termofservice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 140, 20));

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("© 2026 MTB. All rights reserved.");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 20));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 960, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        String message = "To view all movies, please sign in or create an account.";
        String title = "Authentication Required";

        String[] options = {"Login", "Sign Up", "Cancel"};

        int choice = javax.swing.JOptionPane.showOptionDialog(
                this,
                message,
                title,
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                javax.swing.JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) { // Login
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            this.dispose();

        } else if (choice == 1) { // Sign Up
            SignUp signUpFrame = new SignUp();
            signUpFrame.setVisible(true);
            this.dispose();
        }
        // Cancel = do nothing
    }//GEN-LAST:event_viewbuttonActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

        String message = "To view all movies, please sign in or create an account.";
        String title = "Authentication Required";

        String[] options = {"Login", "Sign Up", "Cancel"};

        int choice = javax.swing.JOptionPane.showOptionDialog(
                this,
                message,
                title,
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                javax.swing.JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) { // Login
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            this.dispose();

        } else if (choice == 1) { // Sign Up
            SignUp signUpFrame = new SignUp();
            signUpFrame.setVisible(true);
            this.dispose();
        }
        // Cancel = do nothing
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        String message = "To view all movies, please sign in or create an account.";
        String title = "Authentication Required";

        String[] options = {"Login", "Sign Up", "Cancel"};

        int choice = javax.swing.JOptionPane.showOptionDialog(
                this,
                message,
                title,
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                javax.swing.JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) { // Login
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            this.dispose();

        } else if (choice == 1) { // Sign Up
            SignUp signUpFrame = new SignUp();
            signUpFrame.setVisible(true);
            this.dispose();
        }
        // Cancel = do nothing
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        String message = "To book tickets, please sign in or create an account.";
        String title = "Authentication Required";

        String[] options = {"Login", "Sign Up", "Cancel"};

        int choice = javax.swing.JOptionPane.showOptionDialog(
                this,
                message,
                title,
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                javax.swing.JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) { // Login
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            this.dispose();

        } else if (choice == 1) { // Sign Up
            SignUp signUpFrame = new SignUp();
            signUpFrame.setVisible(true);
            this.dispose();
        }
        // Cancel = do nothing

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked

        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
        this.dispose();

    }//GEN-LAST:event_jLabel12MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contactus2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel movie1;
    private javax.swing.JLabel movie2;
    private javax.swing.JLabel movie3;
    private javax.swing.JLabel movie4;
    private javax.swing.JLabel privacy2;
    private javax.swing.JLabel termofservice2;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
