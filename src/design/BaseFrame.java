package design;

import javax.swing.JFrame;

public class BaseFrame extends JFrame {

    public BaseFrame() {
        setSize(961, 578);
        setResizable(false);              // no resizing
        setLocationRelativeTo(null);      // center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);            
    }
}
