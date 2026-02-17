package Main;

import KiosksPages.HomeK;

/**
 *
 * @author ph2tn
 */
public class app {
    
    // ================= APP START =================
    public static void main(String[] args) {
        HomeK HomeK = new HomeK();
        HomeK.setVisible(true);
        HomeK.pack();
        HomeK.setLocationRelativeTo(null);
    }
    
}
