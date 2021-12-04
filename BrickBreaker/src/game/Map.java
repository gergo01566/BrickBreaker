package game;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Map {

    private Brick[][] bricks;
    private int level;
    private int brow = 0;
    private int bcol = 0;

    public Map() throws IOException {
        level = 2;
        bricks = loadBricks(level);
    }

    public Map(int level) throws IOException {
        this.level = level;
        bricks = loadBricks(level);
    }

    public Brick[][] getBricks() {
        return bricks;
    }

    public Brick getBrickByParam(int col, int row) {
        return bricks[col][row];
    }

    public void setBricks(Brick[][] bricks) {
        this.bricks = bricks;
    }

    public int getBrow() {
        return brow;
    }

    public void setBrow(int brow) {
        this.brow = brow;
    }

    public int getBcol() {
        return bcol;
    }

    public void setBcol(int bcol) {
        this.bcol = bcol;
    }

    public int getLevel() {return level;}


//    public void mapGenerator(int col, int row) {
//        bricks = new Brick[col][row];
//        Random r = new Random();
//        int code;
//        int brickWidth = (GamePanel.WIDTH - 100) / col;
//        int brickHeight = (GamePanel.HEIGHT / 2 - 50) / row;
//
//        for (int i = 0; i < col; i++) {
//            for (int j = 0; j < row; j++) {
//                code = r.nextInt(3);
//                bricks[i][j] = makeBrick(i, j, code, brickWidth, brickHeight);
//            }
//        }
//
//        for (int j = 0; j < col; j++) {
//            for (var i = 0; i < row; i++) {
//                System.out.print(bricks[j][i].code + " ");
//            }
//            System.out.println();
//        }
//    }

    public Brick[][] loadBricks(int level) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("level_" + level + ".txt"));
        String line;
        line = reader.readLine();
        String[] cols = line.split(",");
        setBrow(Integer.parseInt(cols[0]));
        setBcol(Integer.parseInt(cols[1]));
        int brickWidth = (GamePanel.WIDTH - 100) / getBrow();
        int brickHeight = (GamePanel.HEIGHT / 2 - 50) / getBcol();

        Brick[][] b = new Brick[getBrow()][getBcol()];
        System.out.println(getBrow() + " " + getBcol());
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


    public void saveBricks() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(getBrow()).append(",").append(getBcol()).append("\n");
        for (int i = 0; i < getBrow(); i++) {
            for (int j = 0; j < getBcol(); j++) {
                builder.append(bricks[i][j]);
                if (j < getBcol() - 1) {
                    builder.append(",");
                }
            }
            builder.append("\n");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("level_2.txt"));
        writer.write(builder.toString());
        writer.close();
    }


}
