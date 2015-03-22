package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame extends JFrame {

    private Graphics2D onscreen;
    public Graphics2D offscreen;
    private BufferedImage onscreenImage;
    private BufferedImage offscreenImage;
    private JLabel draw;

    public Frame(String title, int width, int height) {
        super(title);
        onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreen = onscreenImage.createGraphics();
        offscreen = offscreenImage.createGraphics();
        ImageIcon icon = new ImageIcon(onscreenImage);
        draw = new JLabel(icon);
        setContentPane(draw);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void setPenColor(Color color) {
        offscreen.setColor(color);
    }

    public void drawPixel(int x, int y) {
        offscreen.draw(new Rectangle2D.Double(x, y, 1, 1));
    }

    public void display() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        repaint();
    }
}
