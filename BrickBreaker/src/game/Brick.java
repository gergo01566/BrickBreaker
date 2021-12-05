package game;

import java.awt.*;
import java.io.Serializable;


/**
 * Ez egy absztrakt osztaly, amit az egyes Brick tipusok valositanak meg
 */
public abstract class Brick implements Serializable {
    private int brickHeight, brickWidth;

    protected Color color;
    private int x, y;

    protected int code;

    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.brickHeight = height;
        this.brickWidth = width;
        this.color = color;
    }

    /**
     * Absztrakt fuggveny, amelyet az egyes Brick tipusok kulonbozo modon valositanak meg, annak fuggvenyeben,
     * hogy a labda milyen tipusu teglaval utkozott
     * @param l labda amely egy adott teglaval utkozik
     */
    public abstract boolean intersect(Labda l);

    public void Draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(x, y, brickWidth, brickHeight);

        g.setStroke(new BasicStroke(3));
        g.setColor(Color.black);
        g.drawRect(x, y, brickWidth, brickHeight);
    }

    public abstract int getCode();

    public void setCode(int code){
        this.code = code;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBrickHeight() {
        return brickHeight;
    }

    public int getBrickWidth() {
        return brickWidth;
    }
}
