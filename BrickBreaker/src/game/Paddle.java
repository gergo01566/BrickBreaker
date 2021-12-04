package game;

import java.awt.*;

public class Paddle {
    private int pozX;
    private final int width;
    private final int height;

    public final int pozY = GamePanel.HEIGHT -100;

    public Paddle(){
        width = 90;
        height = 10;

        pozX = GamePanel.WIDTH / 2 - width / 2;
    }

    public void resetPosition(){
        pozX = GamePanel.WIDTH / 2 - width / 2;
    }

    public void Draw(Graphics g){
        g.setColor(Color.white);
        g.fillRect(pozX, pozY, width, height);
    }

    public void moveRight(){
            pozX +=20;
    }

    public void moveLeft() {
        pozX -= 20;
    }

    public int getPozX() {
        return pozX;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getPaddleRect() {
        return new Rectangle(pozX, pozY, width, height);
    }

    public void setPozX(int pozX) {
        this.pozX = pozX;
    }
}

