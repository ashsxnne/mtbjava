package design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MovieSliderPanel extends JPanel {

    private JLabel imageLabel;
    private ArrayList<ImageIcon> images;
    private int index = 0;
    private int x = 0;

    public MovieSliderPanel() {
        setBounds(50, 50, 900, 350);
        setLayout(null);
        setBackground(Color.BLACK);

        imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, 900, 350);
        add(imageLabel);

        images = new ArrayList<>();
        images.add(load("1.jpg"));
        images.add(load("2.jpg"));
        images.add(load("3.jpg"));

        imageLabel.setIcon(images.get(0));

        startSlider();
    }

    private void startSlider() {
        Timer timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 4;
                imageLabel.setLocation(x, 0);

                if (x <= -900) {
                    x = 0;
                    index = (index + 1) % images.size();
                    imageLabel.setIcon(images.get(index));
                }
            }
        });
        timer.start();
    }

    private ImageIcon load(String name) {
        ImageIcon icon = new ImageIcon(
            getClass().getResource("/images/" + name)
        );
        Image img = icon.getImage().getScaledInstance(900, 350, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}