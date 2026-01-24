/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

public class landingpage extends BaseFrame {

    public landingpage() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        seachbutton = new javax.swing.JTextField();
        login = new javax.swing.JButton();
        signup = new javax.swing.JButton();
        seachbutton1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        contactus = new javax.swing.JLabel();
        privacy = new javax.swing.JLabel();
        termofservice = new javax.swing.JLabel();
        movie1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        viewbutton = new javax.swing.JButton();
        movie2 = new javax.swing.JLabel();
        movie3 = new javax.swing.JLabel();
        movie4 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(961, 578));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(34, 61, 114));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        seachbutton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        seachbutton.setText("Select genre");
        seachbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seachbuttonActionPerformed(evt);
            }
        });
        jPanel2.add(seachbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 120, 40));

        login.setBackground(new java.awt.Color(34, 61, 114));
        login.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        login.setForeground(new java.awt.Color(255, 255, 255));
        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        jPanel2.add(login, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 110, 50));

        signup.setBackground(new java.awt.Color(34, 61, 114));
        signup.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        signup.setForeground(new java.awt.Color(255, 255, 255));
        signup.setText("Sign Up");
        signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupActionPerformed(evt);
            }
        });
        jPanel2.add(signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 30, 110, 50));

        seachbutton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        seachbutton1.setText("Search movies");
        seachbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seachbutton1ActionPerformed(evt);
            }
        });
        jPanel2.add(seachbutton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 160, 40));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/3.jpg"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 100));

        jPanel3.setBackground(new java.awt.Color(33, 60, 112));
        jPanel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(216, 184, 86));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(216, 184, 86));
        jLabel1.setText("© 2026 MTB. All rights reserved.");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 230, 20));

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

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 960, 60));

        movie1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movies/1.jpg"))); // NOI18N
        jPanel1.add(movie1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 210, 270));

        jLabel6.setBackground(new java.awt.Color(216, 184, 86));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(216, 184, 86));
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
        jPanel1.add(movie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 220, 270));

        movie3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movies/3.jpg"))); // NOI18N
        movie3.setText("jLabel2");
        jPanel1.add(movie3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 190, 270));

        movie4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movies/4.png"))); // NOI18N
        movie4.setText("jLabel2");
        jPanel1.add(movie4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, 210, 270));

        jToggleButton1.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jToggleButton1.setForeground(new java.awt.Color(34, 47, 73));
        jToggleButton1.setText("Buy Tickets");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 460, 140, 40));

        jToggleButton2.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(34, 47, 73));
        jToggleButton2.setText("Buy Tickets");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 460, 140, 40));

        jToggleButton3.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jToggleButton3.setForeground(new java.awt.Color(34, 47, 73));
        jToggleButton3.setText("Buy Tickets");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 140, 40));

        jToggleButton4.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jToggleButton4.setForeground(new java.awt.Color(34, 47, 73));
        jToggleButton4.setText("Buy Tickets");
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 460, 140, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed

        Login loginFrame = new Login();
        loginFrame.setVisible(true);
        this.dispose(); // close landing page
    }//GEN-LAST:event_loginActionPerformed

    private void signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupActionPerformed
        SignUp signUpFrame = new SignUp();
        signUpFrame.setVisible(true);
        this.dispose(); // 
    }//GEN-LAST:event_signupActionPerformed

    private void seachbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachbutton1ActionPerformed

// TODO add your handling code here:
    }//GEN-LAST:event_seachbutton1ActionPerformed

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

    private void seachbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachbuttonActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_seachbuttonActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
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
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
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
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
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
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
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
    }//GEN-LAST:event_jToggleButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contactus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JButton login;
    private javax.swing.JLabel movie1;
    private javax.swing.JLabel movie2;
    private javax.swing.JLabel movie3;
    private javax.swing.JLabel movie4;
    private javax.swing.JLabel privacy;
    private javax.swing.JTextField seachbutton;
    private javax.swing.JTextField seachbutton1;
    private javax.swing.JButton signup;
    private javax.swing.JLabel termofservice;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
