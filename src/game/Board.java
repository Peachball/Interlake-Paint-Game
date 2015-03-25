package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class Board {

    public static final Color color1 = Color.BLUE;
    public static final Color color2 = Color.GREEN;
    public static final Color color3 = Color.ORANGE;
    public static final Color color4 = Color.YELLOW;
    public int SPEED = 10;

    protected Object renderer = new Object();

    public Frame frame;
    byte[][] colors;
    ArrayList<Player> players;
    boolean gameOn;

    public Board(int x, int y) {
        colors = new byte[x][y];
        players = new ArrayList<Player>();
        frame = new Frame("Paint Game", x, y);
        frame.offscreenGame.setColor(Color.WHITE);
        frame.offscreenGame.fillRect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        frame.scoreboard.setColor(Color.BLACK);
        frame.scoreboard.drawString("BLUE SCORE:", 0, 10);
        gameOn = true;
    }

    public void addPlayer(byte mode) {
        Player buffer = new Player(300, 300, mode, mode);
        buffer.color = mode;
        frame.addKeyListener(buffer);
        new Thread(buffer).start();
        players.add(buffer);

    }

    public Board(Frame frame) {
        this.frame = frame;
    }

    public void updateScoreboard() {
        BufferedImage currentImage = new BufferedImage(frame.onscreenImage.getWidth(), frame.onscreenImage.getHeight(), frame.onscreenImage.getType());
        Graphics g = currentImage.getGraphics();
        g.drawImage(frame.onscreenImage, 0, 0, null);
        for (Player i : players) {
            i.score = 0;
        }
        for (int counterX = 0; counterX < currentImage.getWidth(); counterX++) {
            for (int counterY = 0; counterY < currentImage.getHeight(); counterY++) {
                if (currentImage.getRGB(counterX, counterY) == color1.getRGB()) {
                    players.get(0).score++;
                } else if (currentImage.getRGB(counterX, counterY) == color2.getRGB()) {
                    players.get(1).score++;
                } else if (currentImage.getRGB(counterX, counterY) == color3.getRGB()) {
                    players.get(2).score++;
                } else if (currentImage.getRGB(counterX, counterY) == color4.getRGB()) {
                    players.get(3).score++;
                }
            }
        }

        //Display scoreboard:
        frame.scoreboard.setColor(Color.WHITE);
        frame.scoreboard.fillRect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        frame.scoreboard.setColor(Color.BLACK);

        if (players.size() > 0) {
            frame.scoreboard.drawString("BLUE SCORE:" + players.get(0).score, 0, 10);
        }
        if (players.size() > 1) {
            frame.scoreboard.drawString("GREEN SCORE:" + players.get(1).score, 0, 20);
        }
        if (players.size() > 2) {
            frame.scoreboard.drawString("GREEN SCORE:" + players.get(2).score, 0, 30);
        }
        if (players.size() > 3) {
            frame.scoreboard.drawString("GREEN SCORE:" + players.get(3).score, 0, 40);
        }
    }

    public void draw() {
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
            width = 25;
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
                if (xPos + speedX - width < 0 || yPos + speedY - width < 0) {
                    continue;
                }
                shift(speedX, speedY);
                synchronized (renderer) {
                    switch (color) {
                        case 1:
                            frame.offscreenGame.setColor(color1);
                            break;
                        case 2:
                            frame.offscreenGame.setColor(color2);
                            break;
                        case 3:
                            frame.offscreenGame.setColor(color3);
                            break;
                        case 4:
                            frame.offscreenGame.setColor(color4);
                            break;
                    }

                    frame.offscreenGame.fillOval(xPos - width, yPos - width, width, width);
                    frame.offscreenGame.setColor(Color.BLACK);
                    frame.offscreenGame.fillOval(xPos - width / 2 - 5, yPos - width / 2 - 5, 10, 10);
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
                            break;
                        case KeyEvent.VK_M:
                    }
                    break;
                case 2:
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_W:
                            UP = true;
                            break;
                        case KeyEvent.VK_A:
                            LEFT = true;
                            break;
                        case KeyEvent.VK_S:
                            DOWN = true;
                            break;
                        case KeyEvent.VK_D:
                            RIGHT = true;
                            break;
                        case KeyEvent.VK_Q:
                            break;
                    }
                    break;
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
                            break;
                        case KeyEvent.VK_M:
                    }
                    break;
                case 2:
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_W:
                            UP = false;
                            break;
                        case KeyEvent.VK_A:
                            LEFT = false;
                            break;
                        case KeyEvent.VK_S:
                            DOWN = false;
                            break;
                        case KeyEvent.VK_D:
                            RIGHT = false;
                            break;
                        case KeyEvent.VK_Q:
                            break;
                    }
                    break;
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
            long time = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    nextIteration(1);
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                }
            }
        }

    }
}
