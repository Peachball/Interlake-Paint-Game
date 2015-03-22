package game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;

public class Board {
    
    public static final int DEFAULT_PLAYER_WIDTH = 10;
    public static final Color color1 = Color.BLUE;
    public static final Color color2 = Color.GREEN;
    public static final Color color3 = Color.ORANGE;
    public static final Color color4 = Color.YELLOW;
    public int SPEED = 10;
    
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
        new Thread(buffer).start();
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
//        Collections.sort(players, new PlayerComparator());
//        for (Player i : players) {
//            i.nextIteration(1);
//        }
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
    
    class Player implements KeyListener, MouseListener, Runnable {
        
        int xPos;
        int yPos;
        byte color;
        int score;
        short mode;
        int width;
        int speedX;
        int speedY;
        int speed;
        boolean UP;
        boolean RIGHT;
        boolean DOWN;
        boolean LEFT;
        
        public Player(int xPos, int yPos, byte color, short mode) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.color = color;
            this.mode = mode;
            speed = 1;
            width = 10;
            score = 0;
        }
        
        public void shift(int x, int y) {
            if (xPos + x < 0 || yPos + y < 0) {
                return;
            }
            if (xPos + x > colors.length || yPos + y > colors[0].length) {
                return;
            }
            xPos += x;
            yPos += y;
        }
        
        public void addVelocity(int x, int y) {
            speedX += x;
            speedY += y;
        }
        
        public void nextIteration(int time) {
            if (UP) {
                addVelocity(0, -speed);
            }
            if (RIGHT) {
                addVelocity(speed, 0);
            }
            if (LEFT) {
                addVelocity(-speed, 0);
            }
            if (DOWN) {
                addVelocity(0, speed);
            }
            for (int counter = 0; counter < time; counter++) {
                shift(speedX, speedY);
                for (int counterX = xPos - width; counterX < xPos + width; counterX++) {
                    for (int counterY = yPos - width; counterY < yPos + width; counterY++) {
                        if (counterX > colors.length || counterY > colors[0].length || counterX < 0 || counterY < 0) {
                            continue;
                        }
                        if (Math.sqrt(Math.pow(counterX - xPos, 2) + Math.pow(counterY - yPos, 2)) < width) {
                            colors[counterX][counterY] = color;
                        }
                    }
                }
            }
            speedX = 0;
            speedY = 0;
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
                            UP = true;
                            break;
                        case KeyEvent.VK_RIGHT:
                            RIGHT = true;
                            break;
                        case KeyEvent.VK_DOWN:
                            DOWN = true;
                            break;
                        case KeyEvent.VK_LEFT:
                            LEFT = true;
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
            switch (mode) {
                case 1:
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            UP = false;
                            break;
                        case KeyEvent.VK_RIGHT:
                            RIGHT = false;
                            break;
                        case KeyEvent.VK_DOWN:
                            DOWN = false;
                            break;
                        case KeyEvent.VK_LEFT:
                            LEFT = false;
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
        
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    nextIteration(1);
                    Thread.sleep(SPEED);
                } catch (InterruptedException ex) {
                }
            }
        }
        
    }
}
