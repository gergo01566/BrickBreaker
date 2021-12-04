package game;


import game.menu.GameCourt;
import game.menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;



public class GamePanel extends JPanel implements KeyListener, ActionListener {
    public static final int HEIGHT = 600;
    public static final int WIDTH = 714;
    public static State state = State.MENU;
    private final Menu menu;



    private static GameCourt gameCourt;
    private Timer timer;


    public GamePanel() throws IOException {
        int keses = 1;
        timer = new Timer(keses, this);
        timer.start();

        //gameCourt = new GameCourt();
        menu = new Menu();

        addKeyListener(this);
        this.addMouseListener(new MouseInput());
        this.setFocusable(true);

    }

    public void paint(Graphics g){
        System.out.print("meghivtam1");
        if(state == State.MENU){
            menu.rajzol(g);
        } else if (state == State.GAME){
            gameCourt.rajzol(g);
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(state == State.GAME){
            if (!gameCourt.checkWin()) {
                if (key == KeyEvent.VK_RIGHT) {
                    if (gameCourt.getPaddle().getPozX() >= GamePanel.WIDTH - gameCourt.getPaddle().getWidth()) {
                        gameCourt.getPaddle().setPozX(GamePanel.WIDTH - gameCourt.getPaddle().getWidth());
                    } else {
                        gameCourt.getPaddle().moveRight();
                    }
                } else if (key == KeyEvent.VK_LEFT) {
                    if (gameCourt.getPaddle().getPozX() <= 0) {
                        gameCourt.getPaddle().setPozX(0);
                    } else {
                        gameCourt.getPaddle().moveLeft();
                    }

                } else if (key == KeyEvent.VK_ESCAPE) {
                    state = State.MENU;
                } else if (key == KeyEvent.VK_SPACE) {
                    gameCourt.setRunning(true);
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(state == State.GAME){
            timer.start();

            if (gameCourt.isRunning()) {
                gameCourt.getLabda().move();
                gameCourt.checkCollision();
                gameCourt.checkGameOver();
            }
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) { }


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

    public static void setGameCourt(GameCourt gameCourt) {
        GamePanel.gameCourt = gameCourt;
    }

    public static GameCourt getGameCourt() {
        return GamePanel.gameCourt;
    }

}
