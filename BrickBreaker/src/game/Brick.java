package game;

import java.awt.*;
import java.io.Serializable;


public abstract class Brick implements Serializable {
    public int brickHeight, brickWidth;

    protected Color color;
    public int x, y;

    protected int code;

    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.brickHeight = height;
        this.brickWidth = width;
        this.color = color;
    }

    public abstract boolean intersect(Labda l);

    public void Draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(x, y, brickWidth, brickHeight);


        g.setStroke(new BasicStroke(3));
        g.setColor(Color.black);
        g.drawRect(x, y, brickWidth, brickHeight);
    }

    public abstract int getCode();


}
