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
    private boolean gameOver = false;
    private Brick[][] bricks;
    private int level;

    private boolean running = false;
    private boolean palyatNyert = false;

    public GamePanel() throws IOException {
        level = 2;
        infos = new InfoPanel();
        paddle = new Paddle();
        labda = new Labda();

        //MapGenerator(4, 6);
        bricks = loadBricks(level);
        menu = new Menu();

        addKeyListener(this);
        this.addMouseListener(new MouseInput());
        this.setFocusable(true);

        int keses = 8;
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
            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[0].length; j++) {
                    bricks[i][j].Draw((Graphics2D) g);
                }
            }

            //Labda kirajzolása
            labda.Draw(g);


            if (running && checkWin() && level <= 4) {
                handleWin();
                running = false;
            }


            if (level == 5 && checkWin()){
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
        level += 1;
        bricks = loadBricks(level);
        labda.resetPosition();
        paddle.resetPosition();
    }

    public boolean checkWin() {
        int remainingBricks = 0;
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j].getCode() == 1) {
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

        for (int ii = 0; ii < bricks.length; ii++) {
            for (int jj = 0; jj < bricks[ii].length; jj++) {

                if (bricks[ii][jj].intersect(labda)) {

                    //alulrol
                    if (labda.getPozY() + 2 >= bricks[ii][jj].y + bricks[ii][jj].brickHeight) {
                        if (labda.getDirY() < 0) labda.changeDirY();
                    }

                    //felulrol
                    else if (labda.getPozY() + labda.getBallRect().height - 1 <= bricks[ii][jj].y) {
                        if (labda.getDirY() > 0) labda.changeDirY();
                    }

                    //balrol
                    else if (labda.getPozX() + labda.getSize() + 1 >= bricks[ii][jj].x) {
                        if (labda.getDirX() > 0) labda.changeDirX();

                            //jobbrol
                        else if (labda.getDirX() - 1 <= bricks[ii][jj].x + bricks[ii][jj].brickWidth)
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

    /**
     * Ez a függvény egy Bricket csinál a megadott paraméterekkel
     *
     * @param x      a Brick x koordinátája
     * @param y      a Brick y koordinátája
     * @param code   Brick beazonosítására használandó
     * @param width  Brick szélessége
     * @param height Brick magassága
     * @return létrehoz egy brick-et és visszaadja azt
     */
    private Brick makeBrick(int x, int y, int code, int width, int height) {
        return switch (code) {
            case 2 -> new UnbreakableBrick(2, x * width + 50, y * height + 50, width, height, Color.GRAY);
            case 1 -> new StandardBrick(1, x * width + 50, y * height + 50, width, height, Color.WHITE);
            case 0 -> new EmptySpace(x * width + 50, y * height + 50, width, height, Color.BLACK);
            default -> null;
        };
    }

    public Brick[][] loadBricks(int level) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("level_" + level + ".txt"));
        String line;
        int brow, bcol;
        line = reader.readLine();
        String[] cols = line.split(",");

        brow = Integer.parseInt(cols[0]);
        bcol = Integer.parseInt(cols[1]);

        int brickWidth = (GamePanel.WIDTH - 100) / brow;
        int brickHeight = (GamePanel.HEIGHT / 2 - 50) / bcol;

        Brick[][] b = new Brick[brow][bcol];
        System.out.println(brow + " " + bcol);
        int row = 0;
        while ((line = reader.readLine()) != null) {
            cols = line.split(",");
            int col = 0;
            for (String c : cols) {
                System.out.print(Integer.parseInt(c) + " ");
                b[row][col] = makeBrick(row, col, Integer.parseInt(c), brickWidth, brickHeight);
                col++;
            }
            System.out.println();
            row++;
        }
        reader.close();
        return b;
    }

    public void resetGame() throws IOException {
        labda = new Labda();
        paddle = new Paddle();
        InfoPanel.scores = 0;
        InfoPanel.lives = 0;
        level = 1;
        labda.resetPosition();
        paddle.resetPosition();
        bricks = loadBricks(2);
    }

    public int getLevel() {return level;}

    public static void setState(State state) {
        GamePanel.state = state;
    }

    public void saveBricks() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(bricks.length).append(",").append(bricks[0].length).append("\n");
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                builder.append(bricks[i][j]);
                if (j < bricks[0].length - 1) {
                    builder.append(",");
                }
            }
            builder.append("\n");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("level_2.txt"));
        writer.write(builder.toString());
        writer.close();
    }

    public void MapGenerator(int col, int row) {
        bricks = new Brick[col][row];
        Random r = new Random();
        int code;
        int brickWidth = (GamePanel.WIDTH - 100) / col;
        int brickHeight = (GamePanel.HEIGHT / 2 - 50) / row;

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                code = r.nextInt(3);
                bricks[i][j] = makeBrick(i, j, code, brickWidth, brickHeight);
            }
        }

        for (int j = 0; j < col; j++) {
            for (var i = 0; i < row; i++) {
                System.out.print(bricks[j][i].code + " ");
            }
            System.out.println();
        }
    }

}
