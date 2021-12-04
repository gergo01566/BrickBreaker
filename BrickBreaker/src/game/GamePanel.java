package game;

import game.menu.ContinueDialog;
import game.menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Random;


public class GamePanel extends JPanel implements KeyListener, ActionListener {

    public static final int HEIGHT = 600;
    public static final int WIDTH = 714;

    public static State state = State.MENU;
    private final Timer timer;
    private Labda labda;
    private Paddle paddle;
    private final InfoPanel infos;
    private final Menu menu;
    private boolean running = false;
    private boolean palyatNyert = false;
    private Map map;
    private boolean gameOver = false;

    public GamePanel() throws IOException {
        infos = new InfoPanel();
        paddle = new Paddle();
        labda = new Labda();

        //MapGenerator(4, 6);
        map = new Map(1);
        menu = new Menu();

        addKeyListener(this);
        this.addMouseListener(new MouseInput());
        this.setFocusable(true);

        int keses = 1;
        timer = new Timer(keses, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //Háttér
        g.setColor(Color.black);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        if (state == State.GAME) {

            //Felső panel kirajzolása
            infos.draw(g);

            //Ütő kirajzolása
            paddle.Draw(g);

            //Téglák kirajzolása
            for (int i = 0; i < map.getBrow(); i++) {
                for (int j = 0; j < map.getBcol(); j++) {
                    map.getBrickByParam(i,j).Draw((Graphics2D) g);
                }
            }

            //Labda kirajzolása
            labda.Draw(g);


            if (running && checkWin() && map.getLevel() <= 4) {
                handleWin();
                running = false;
            }


            if (map.getLevel() == 5 && checkWin()){
                infos.drawWin(g);
                running = false;
                try {
                    endGame();
                    //resetGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (gameOver) {
                running = false;
                infos.drawGameOver(g);
                try {
                    endGame();
                    //resetGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            g.dispose();
        } else if (state == State.MENU) {
            menu.Rajzol(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (running && state == State.GAME) {
            labda.move();
            checkCollision();
            checkGameOver();
        }
        if (state == State.MENU) {
            running = false;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (state == State.GAME && !checkWin()) {
            if (key == KeyEvent.VK_RIGHT) {
                if (paddle.getPozX() >= GamePanel.WIDTH - paddle.getWidth()) {
                    paddle.setPozX(GamePanel.WIDTH - paddle.getWidth());
                } else {
                    paddle.moveRight();
                }
            } else if (key == KeyEvent.VK_LEFT) {
                if (paddle.getPozX() <= 0) {
                    paddle.setPozX(0);
                } else {
                    paddle.moveLeft();
                }

            } else if (key == KeyEvent.VK_ESCAPE) {
                state = State.MENU;
            } else if (key == KeyEvent.VK_SPACE) {
                running = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) { }

    private void handleWin() {
        var dialog = new ContinueDialog();
        dialog.okButton.addActionListener(e -> {
            try {
                nextLevel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dialog.dispose();
        });

        dialog.cancelButton.addActionListener(e -> {
            try {
                endGame();
                //resetGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dialog.dispose();

        });

        dialog.setVisible(true);
    }

    private void endGame() throws IOException {
        running = false;
        TextField t = new TextField();
        state = State.MENU;
    }

    private void nextLevel() throws IOException {
        int currentLevel = map.getLevel();
        map = new Map(currentLevel + 1);
        labda = new Labda();
        paddle = new Paddle();
//        labda.resetPosition();
//        paddle.resetPosition();
    }

    public boolean checkWin() {
        int remainingBricks = 0;
        for (int i = 0; i < map.getBrow(); i++) {
            for (int j = 0; j < map.getBcol(); j++) {
                if (map.getBrickByParam(i,j).getCode() == 1) {
                    remainingBricks += 1;
                }
            }
        }
        return remainingBricks == 0;
    }

    public void checkCollision() {
        // labda + uto
        if (labda.intersects(paddle)) {
            if (labda.getBallRect().y + labda.getBallRect().height < paddle.pozY) labda.changeDirX();
//            if(labda.getPozX() < paddle.getPozX() + paddle.getWidth() /4)
//                labda.setDirX(labda.getDirX() - 1);
//            if(labda.getPozX() <paddle.getPozX() + paddle.getWidth() && labda.getPozX() > paddle.getPozX() + paddle.getWidth())
//                labda.setDirX((labda.getDirX() + 1));
            else labda.setDirY(-Math.abs(labda.getDirY()));
        }

        // labda + kockak

        for (int ii = 0; ii < map.getBrow(); ii++) {
            for (int jj = 0; jj < map.getBcol(); jj++) {

                if (map.getBrickByParam(ii,jj).intersect(labda)) {

                    //alulrol
                    if (labda.getPozY() + 2 >= map.getBrickByParam(ii,jj).y + map.getBrickByParam(ii,jj).brickHeight) {
                        if (labda.getDirY() < 0) labda.changeDirY();
                    }

                    //felulrol
                    else if (labda.getPozY() + labda.getBallRect().height - 1 <= map.getBrickByParam(ii,jj).y) {
                        if (labda.getDirY() > 0) labda.changeDirY();
                    }

                    //balrol
                    else if (labda.getPozX() + labda.getSize() + 1 >= map.getBrickByParam(ii,jj).x) {
                        if (labda.getDirX() > 0) labda.changeDirX();

                            //jobbrol
                        else if (labda.getDirX() - 1 <= map.getBrickByParam(ii,jj).x + map.getBrickByParam(ii,jj).brickWidth)
                            if (labda.getDirX() < 0) labda.changeDirX();

                    }
                }
            }
        }
    }

    public void checkGameOver() {
        if (labda.getPozY() >= GamePanel.HEIGHT - labda.getSize()) {
            infos.setLives(infos.getLives() - 1);
            running = false;
            labda.setPozX(GamePanel.WIDTH / 2 - labda.getSize() / 2);
            labda.setPozY(GamePanel.HEIGHT - 115);
        }
        if (infos.getLives() == 0) {
            running = false;
            gameOver = true;
        }
    }





//    public void resetGame() throws IOException {
//        labda = new Labda();
//        paddle = new Paddle();
//        InfoPanel.scores = 0;
//        InfoPanel.lives = 0;
//        level = 1;
//        labda.resetPosition();
//        paddle.resetPosition();
//        bricks = loadBricks(2);
//    }



    public static void setState(State state) {
        GamePanel.state = state;
    }
}
