package design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class customecursor extends JComponent
        implements MouseMotionListener, MouseListener {

    private Point mouse = new Point(0, 0);
    private final ArrayList<Ripple> ripples = new ArrayList<>();
    private final JFrame frame;

    // 🎨 Your palette
    private static final Color LIGHT_LAVENDER = new Color(0xCFC9EB);
    private static final Color MUTED_LAVENDER = new Color(0xA5A0C1);
    private static final Color DUSTY_PURPLE = new Color(0x938EAB);
    private static final Color DEEP_PURPLE  = new Color(0x6F6A87);

    public customecursor(JFrame frame) {
        this.frame = frame;

        setOpaque(false);
        frame.setGlassPane(this);
        setVisible(true);

        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);

        hideSystemCursor(frame);

        new Timer(16, e -> {
            updateRipples();
            repaint();
        }).start();
    }

    private void hideSystemCursor(JFrame frame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage(new byte[0]);
        Cursor invisible = toolkit.createCustomCursor(image, new Point(0, 0), "blank");
        frame.setCursor(invisible);
    }

    // 🔑 THIS IS THE MAGIC
    @Override
    public boolean contains(int x, int y) {
        return false; // allows mouse events to pass through
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // 🎬 Ripple pulse
        for (Ripple r : ripples) {
            g2.setColor(new Color(
                    DUSTY_PURPLE.getRed(),
                    DUSTY_PURPLE.getGreen(),
                    DUSTY_PURPLE.getBlue(),
                    r.alpha
            ));
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(mouse.x - r.size / 2, mouse.y - r.size / 2, r.size, r.size);
        }

        // ✨ Glow
        g2.setColor(new Color(
                MUTED_LAVENDER.getRed(),
                MUTED_LAVENDER.getGreen(),
                MUTED_LAVENDER.getBlue(),
                90
        ));
        g2.fillOval(mouse.x - 18, mouse.y - 18, 36, 36);

        // 🎟 Ticket cursor
        g2.setColor(LIGHT_LAVENDER);
        g2.fillOval(mouse.x - 8, mouse.y - 8, 16, 16);

        g2.setColor(Color.WHITE);
        g2.fillOval(mouse.x - 3, mouse.y - 3, 6, 6);

        g2.setStroke(new BasicStroke(2));
        g2.setColor(DUSTY_PURPLE);
        g2.drawOval(mouse.x - 10, mouse.y - 10, 20, 20);

        g2.setColor(DEEP_PURPLE);
        g2.drawOval(mouse.x - 11, mouse.y - 11, 22, 22);
    }

    private void updateRipples() {
        ripples.removeIf(r -> {
            r.size += 3;
            r.alpha -= 10;
            return r.alpha <= 0;
        });
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ripples.add(new Ripple());
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private static class Ripple {
        int size = 12;
        int alpha = 160;
    }
}