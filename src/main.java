
import game.Board;
import game.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class main  {
    public static void main(String[] args) throws IOException{
        Frame frame = new Frame("PAINT GAME",600,600);
        BufferedImage gui = ImageIO.read(ClassLoader.getSystemResource("res/StartScreenImage.png"));
        class GUIMouseListener implements MouseListener{

            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        }
        Board board = new Board(frame);
        board.addPlayer((byte)1);
        board.addPlayer((byte)2);
        while(true){
            board.updateScoreboard();
            board.draw();
        }
    }
}
