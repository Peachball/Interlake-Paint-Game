
import game.Board;
import game.Frame;

public class main {
    public static void main(String[] args){
        Board board = new Board(800,600);
        board.addPlayer((byte)1);
        while(true){
            board.update();
            board.draw();
        }
    }
}
