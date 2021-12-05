package game;

import game.menu.ContinueDialog;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Ez az osztaly tartalmazza a jatekot megvalosito legfontosabb fugvenyeket (a jatek logikajat), mint peldaul,
 * hogy mi tortenik, amikor a labda egy bizonyos teglaval utkozik
 */
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
    }

    public GameCourt(int level) throws IOException {
        labda = new Labda();
        paddle = new Paddle();
        map = new Map();
        infos = new InfoPanel();
    }

    /**
     * Ez a fuggveny kirajzolja a jatekban talalhato dolgokat
     */
    public void rajzol(Graphics g) {

        /**
         * Kirajzolja a hatteret
         */
        g.setColor(Color.black);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        /**
         * Felso panel kirajzolasa (pontszam és eletek szama)
         */
        infos.draw(g);

        /**
         * Uto kirajzolasa
         */
        paddle.Draw(g);

        /**
         * Teglak kirajzolasa
         */
        for (int i = 0; i < map.getBrow(); i++) {
            for (int j = 0; j < map.getBcol(); j++) {
                map.getBrickByParam(i,j).Draw((Graphics2D) g);
            }
        }

        /**
         * Labda kirajzolasa
         */
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (gameOver) {
            infos.drawGameOver(g);
            try {
                endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        g.dispose();
    }

    /**
     * Ez a fuggveny kezeli azt a tortenest, ha a jatekos az adott palyat megnyerte
     * ket opcio kozul valaszthat: Continue vagy Exit
     */
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dialog.dispose();
        });
        dialog.setVisible(true);
    }

    /**
     * Ez a fuggveny akkor hivodik meg, hogy ha a jatekos mindharom eletet elvesztette
     * A felhasznalo ilyenkor megadhatja a nevet, hogy bekeruljon a dicsoseglistaba
     */
    private void endGame() throws IOException {
        running = false;
        TextField t = new TextField();
        GamePanel.setState(State.MENU);
    }

    /**
     * Ez a fuggveny hivodik meg, hogy ha a felhasznalo teljesitett egy adott palyat,
     * majd a labdat es az utot alaphelyzetbe allitja
     */
    private void nextLevel() throws IOException {
        int currentLevel = map.getLevel();
        map = new Map(currentLevel + 1);
        labda = new Labda();
        paddle = new Paddle();
    }

    /**
     * @return true vagy false, annak fuggvenyeben, hogy a jatekos osszetorte-e az osszes torheto teglat vagy nem
     */
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

    /**
     * Ez a fuggveny ellenorzi, hogy a labda utkozott-e utovel, ha igen megvaltoztatja a Y iranyat
     * Ha a labda teglaval utkozott, akkor az utkozesi pont szerint valtoztatja meg a labda iranyat
     */
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
                    if (labda.getPozY() + 2 >= map.getBrickByParam(ii,jj).getY() + map.getBrickByParam(ii,jj).getBrickHeight()) {
                        if (labda.getDirY() < 0) labda.changeDirY();
                    }

                    //felulrol
                    else if (labda.getPozY() + labda.getBallRect().height - 2 <= map.getBrickByParam(ii,jj).getY()) {
                        if (labda.getDirY() > 0) labda.changeDirY();
                    }

                    //balrol
                    else if (labda.getPozX() + labda.getSize() + 1 >= map.getBrickByParam(ii,jj).getX()) {
                        if (labda.getDirX() > 0) labda.changeDirX();

                    //jobbrol
                    else if (labda.getDirX() - 1 <= map.getBrickByParam(ii,jj).getX() + map.getBrickByParam(ii,jj).getBrickWidth())
                        if (labda.getDirX() < 0) labda.changeDirX();

                    }
                }
            }
        }
    }

    /**
     * Ellenorzi, hogy veget ert-e a jatek (az eletek szama nullara csokkent)
     * Ha a labdat nem sikerul visszautni, akkor a felhasznalo veszt egy eletet
     */
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

    //Getterek

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

    public Map getMap() {
        return map;
    }

    public InfoPanel getInfos() {
        return infos;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
