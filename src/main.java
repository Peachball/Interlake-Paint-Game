
import game.Board;
import game.Frame;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class main {

    public static void main(String[] args) throws IOException {
        Frame frame = new Frame("PAINT GAME", 600, 600);
        while (true) {
            BufferedImage gui = ImageIO.read(ClassLoader.getSystemResource("res/StartScreenImage.png"));
            frame.offscreen.setColor(Color.WHITE);
            frame.offscreen.fillRect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

            frame.offscreen.setColor(Color.BLUE);
            frame.offscreen.fillRect(10, 320, 350, 100);

            frame.offscreen.setColor(Color.GREEN);
            frame.offscreen.fillRect(420, 320, 350, 100);

            frame.offscreen.setColor(Color.ORANGE);
            frame.offscreen.fillRect(10, 470, 350, 100);

            frame.offscreen.setColor(Color.YELLOW);
            frame.offscreen.fillRect(430, 480, 350, 100);

            frame.offscreen.drawImage(gui, 0, 0, frame);
            frame.display();
            class GUIMouseListener implements MouseListener {

                public volatile int num;

                public GUIMouseListener() {
                    num = 0;
                }

                @Override
                public void mouseClicked(MouseEvent me) {
                    if (me.getXOnScreen() < 360 && me.getXOnScreen() > 10 && me.getYOnScreen() < 420 && me.getYOnScreen() > 320) {
                        num = 1;
                    }
                    if (me.getXOnScreen() < 770 && me.getXOnScreen() > 420 && me.getYOnScreen() < 420 && me.getYOnScreen() > 320) {
                        num = 2;
                    }
                    if (me.getXOnScreen() < 360 && me.getXOnScreen() > 10 && me.getYOnScreen() < 570 && me.getYOnScreen() > 470) {
                        num = 3;
                    }
                    if (me.getXOnScreen() < 780 && me.getXOnScreen() > 430 && me.getYOnScreen() < 580 && me.getYOnScreen() > 480) {
                        num = 4;
                    }
                    System.out.println(num);
                }

                @Override
                public void mousePressed(MouseEvent me) {
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                }

                @Override
                public void mouseExited(MouseEvent me) {
                }

            }
            GUIMouseListener direction = new GUIMouseListener();
            frame.addMouseListener(direction);
            while (direction.num == 0);
            Board board = new Board(frame);
            if (direction.num >= 1) {
                board.addPlayer((byte) 1);
            }
            if (direction.num >= 2) {
                board.addPlayer((byte) 2);
            }
            if (direction.num >= 3) {
                board.addPlayer((byte) 3);
            }
            if (direction.num >= 4) {
                board.addPlayer((byte) 4);
            }
            while (!board.isDone()) {
                board.updateScoreboard();
                board.draw();
            }
        }
    }
}
