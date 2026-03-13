package Main;

import KiosksPages.HomeK;
import design.BaseFrame;

public class mainlanding extends BaseFrame {

    public mainlanding() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        adminlogin = new javax.swing.JButton();
        kiosk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesicons/mtblogor.png"))); // NOI18N
        logo.setToolTipText("");

        adminlogin.setBackground(new java.awt.Color(255, 255, 255));
        adminlogin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        adminlogin.setForeground(new java.awt.Color(200, 0, 0));
        adminlogin.setText("Login");
        adminlogin.setBorderPainted(false);
        adminlogin.setFocusPainted(false);
        adminlogin.setRolloverEnabled(false);
        adminlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminloginActionPerformed(evt);
            }
        });

        kiosk.setBackground(new java.awt.Color(255, 255, 255));
        kiosk.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        kiosk.setForeground(new java.awt.Color(200, 0, 0));
        kiosk.setText("Kiosk Mode");
        kiosk.setBorderPainted(false);
        kiosk.setFocusPainted(false);
        kiosk.setRolloverEnabled(false);
        kiosk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kioskActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(kiosk, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(adminlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logo))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kiosk, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kioskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kioskActionPerformed
        HomeK HomekFrame = new HomeK();
        HomekFrame.setVisible(true);
        HomekFrame.pack();
        HomekFrame.setLocationRelativeTo(null);
        this.dispose();

    }//GEN-LAST:event_kioskActionPerformed

    private void adminloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminloginActionPerformed
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
        this.dispose();

    }//GEN-LAST:event_adminloginActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminlogin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton kiosk;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
