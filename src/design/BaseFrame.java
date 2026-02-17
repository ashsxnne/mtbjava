package design;

import javax.swing.JFrame;

public class BaseFrame extends JFrame {

    public BaseFrame() {
        this(946, 611);
    }

    public BaseFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);   // center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(false);         // true = no title bar
    }
}
