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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        viewbutton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 550));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(34, 61, 114));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        seachbutton.setText("Select genre");
        seachbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seachbuttonActionPerformed(evt);
            }
        });
        jPanel2.add(seachbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 120, 30));

        login.setBackground(new java.awt.Color(34, 61, 114));
        login.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        login.setForeground(new java.awt.Color(255, 255, 255));
        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        jPanel2.add(login, new org.netbeans.lib.awtextra.AbsoluteConstraints(637, 0, 70, -1));

        signup.setBackground(new java.awt.Color(34, 61, 114));
        signup.setForeground(new java.awt.Color(255, 255, 255));
        signup.setText("Sign Up");
        signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupActionPerformed(evt);
            }
        });
        jPanel2.add(signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, -1, 30));

        seachbutton1.setText("Search movies");
        seachbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seachbutton1ActionPerformed(evt);
            }
        });
        jPanel2.add(seachbutton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 120, 30));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("LOGO MTB");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 70));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("© 2026 MTB. All rights reserved.");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 20));

        contactus.setBackground(new java.awt.Color(34, 61, 114));
        contactus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        contactus.setForeground(new java.awt.Color(34, 61, 114));
        contactus.setText("Contact Us");
        jPanel3.add(contactus, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 70, 20));

        privacy.setBackground(new java.awt.Color(55, 131, 245));
        privacy.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        privacy.setForeground(new java.awt.Color(34, 61, 114));
        privacy.setText("Privacy Policy");
        jPanel3.add(privacy, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 80, 20));

        termofservice.setBackground(new java.awt.Color(34, 61, 114));
        termofservice.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        termofservice.setForeground(new java.awt.Color(34, 61, 114));
        termofservice.setText("Term of Service");
        jPanel3.add(termofservice, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 90, 20));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 800, 60));

        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 170, 280));

        jLabel3.setText("jLabel2");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 170, 280));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("jLabel2");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 170, 280));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(34, 61, 114));
        jLabel6.setText("Now Showing");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 170, 40));

        viewbutton.setForeground(new java.awt.Color(34, 61, 114));
        viewbutton.setText("VIewALL->");
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        jPanel1.add(viewbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void seachbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachbuttonActionPerformed
     
        
// TODO add your handling code here:
    }//GEN-LAST:event_seachbuttonActionPerformed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
     
        
// TODO add your handling code here:
    }//GEN-LAST:event_loginActionPerformed

    private void signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupActionPerformed
      

    }//GEN-LAST:event_signupActionPerformed

    private void seachbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachbutton1ActionPerformed
    
        

// TODO add your handling code here:
    }//GEN-LAST:event_seachbutton1ActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewbuttonActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contactus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton login;
    private javax.swing.JLabel privacy;
    private javax.swing.JTextField seachbutton;
    private javax.swing.JTextField seachbutton1;
    private javax.swing.JButton signup;
    private javax.swing.JLabel termofservice;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
