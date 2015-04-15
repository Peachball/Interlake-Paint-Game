package game;

public class EndScreen {

    Frame window;

    public EndScreen(Frame frame, int winner) {
        window = frame;
        window.offscreen.clearRect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        window.offscreen.drawString("PLAYER " + winner + " WINS!!!!", 300, 300);
    }
    
    
    
}
