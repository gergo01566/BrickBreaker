package game.menu;

import game.*;
import game.TextField;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameCourt extends JPanel{
    private Labda labda;
    private Paddle paddle;
    private Map map;
    private boolean gameOver = false;
    private boolean running = false;
    private InfoPanel infos;

    public GameCourt() throws IOException {
        labda = new Labda();
        paddle = new Paddle();
        map = new Map();
        infos = new InfoPanel();
//        addKeyListener(this);
//        this.addMouseListener(new MouseInput());
//        this.setFocusable(true);
    }

    public void rajzol(Graphics g) {
        System.out.print("mee");
        //Háttér
        g.setColor(Color.black);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

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
    }

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
            labda.resetPosition();
        }
        if (infos.getLives() == 0) {
            running = false;
            gameOver = true;
        }
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Labda getLabda() {
        return labda;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

}
