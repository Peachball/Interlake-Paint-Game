
import game.Board;

public class main {
    public static void main(String[] args){
        Board board = new Board(600,600);
        board.addPlayer((byte)1);
        board.addPlayer((byte)2);
        while(true){
            board.updateScoreboard();
            board.draw();
        }
    }
}
