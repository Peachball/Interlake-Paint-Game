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
    public Graphics2D offscreenGame;
    public Graphics2D scoreboard;
    public Graphics2D offscreen;
    public BufferedImage onscreenImage;
    private BufferedImage offscreenImage;
    private JLabel draw;

    public Frame(String title, int width, int height) {
        super(title);
        onscreenImage = new BufferedImage(width + 200, height, BufferedImage.TYPE_INT_ARGB);
        offscreenImage = new BufferedImage(width + 200, height, BufferedImage.TYPE_INT_ARGB);
        onscreen = onscreenImage.createGraphics();
        offscreen = offscreenImage.createGraphics();
        offscreenGame = offscreenImage.getSubimage(200, 0, width, height).createGraphics();
        scoreboard = offscreenImage.getSubimage(0, 0, 200, height).createGraphics();

        ImageIcon icon = new ImageIcon(onscreenImage);
        draw = new JLabel(icon);
        add(draw);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void setPenColor(Color color) {
        offscreenGame.setColor(color);
    }

    @Deprecated
    public void drawPixel(int x, int y) {
        offscreenGame.draw(new Rectangle2D.Double(x, y, 1, 1));
    }

    public void display() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        repaint();
    }
}
