package game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Board {
    
    public static final int DEFAULT_PLAYER_WIDTH = 10;
    public static final Color color1 = Color.BLUE;
    public static final Color color2 = Color.GREEN;
    public static final Color color3 = Color.ORANGE;
    public static final Color color4 = Color.YELLOW;
    
    public Frame frame;
    byte[][] colors;
    ArrayList<Player> players;
    
    public Board(int x, int y) {
        colors = new byte[x][y];
        players = new ArrayList<Player>();
        frame = new Frame("Paint Game", x, y);
    }
    
    public void addPlayer(byte mode) {
        Player buffer = new Player(10, 10, mode, mode);
        buffer.color = 1;
        frame.addKeyListener(buffer);
        players.add(buffer);
    }
    
    public void removePlayer(short mode) {
        
    }
    
    public Board(Frame frame, int numofPlayers) {
        this.frame = frame;
        colors = new byte[frame.getWidth()][frame.getHeight()];
    }
    
    public short getColor(int x, int y) {
        return colors[x][y];
    }
    
    public void update() {
        Collections.sort(players, new PlayerComparator());
        for (Player i : players) {
            for (int counterX = i.xPos - i.width; counterX < i.xPos + i.width; counterX++) {
                for (int counterY = i.yPos - i.width; counterY < i.yPos + i.width; counterY++) {
                    if (counterX < 0 || counterY < 0) {
                        continue;
                    }
                    if (Math.sqrt(Math.pow(counterX - i.xPos, 2) + Math.pow(counterY - i.yPos, 2)) < i.width) {
                        colors[counterX][counterY] = i.color;
                    }
                }
            }
        }
    }
    
    public void draw() {
        frame.offscreen.setColor(Color.WHITE);
        frame.offscreen.fillRect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (int counterX = 0; counterX < colors.length; counterX++) {
            for (int counterY = 0; counterY < colors[0].length; counterY++) {
                frame.offscreen.setColor(Color.WHITE);
                switch (colors[counterX][counterY]) {
                    case 1:
                        frame.offscreen.setColor(color1);
                        break;
                    case 2:
                        frame.offscreen.setColor(color2);
                        break;
                    case 3:
                        frame.offscreen.setColor(color3);
                        break;
                    case 4:
                        frame.offscreen.setColor(color4);
                        break;
                }
                frame.drawPixel(counterX, counterY);
            }
        }
        frame.display();
    }
    
    class PlayerComparator implements Comparator<Player> {
        
        @Override
        public int compare(Player o1, Player o2) {
            return o1.score - o2.score;
        }
        
    }
    
    class Player implements KeyListener, MouseListener {
        
        int xPos;
        int yPos;
        byte color;
        int score;
        short mode;
        int width;
        int speed;
        
        public Player(int xPos, int yPos, byte color, short mode) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.color = color;
            this.mode = mode;
            speed = 5;
            width = 10;
            score = 0;
        }
        
        public void shift(int x, int y) {
            if (xPos + x < 0 || yPos + y < 0) {
                return;
            }
            if(xPos+x>colors.length||yPos+y>colors[0].length){
                return;
            }
            xPos += x;
            yPos += y;
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            switch (mode) {
                case 1:
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            shift(0, -speed);
                            break;
                        case KeyEvent.VK_RIGHT:
                            shift(speed, 0);
                            break;
                        case KeyEvent.VK_DOWN:
                            shift(0, speed);
                            break;
                        case KeyEvent.VK_LEFT:
                            shift(-speed, 0);
                        case KeyEvent.VK_M:
                    }
                    break;
                case 2:
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_E:
                        case KeyEvent.VK_F:
                        case KeyEvent.VK_D:
                        case KeyEvent.VK_S:
                        case KeyEvent.VK_Q:
                    }
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
}
