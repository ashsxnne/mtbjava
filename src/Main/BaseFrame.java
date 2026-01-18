package Main;

import javax.swing.JFrame;

public class BaseFrame extends JFrame {

    public BaseFrame() {
        setSize(800, 550);             // fixed width x height
        setResizable(false);           // disable resizing
        setLocationRelativeTo(null);   // center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close app when closed
    }
}
