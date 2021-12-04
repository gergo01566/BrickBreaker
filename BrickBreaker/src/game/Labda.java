package game;


import java.awt.*;

public class Labda {
    private int pozX;
    private int pozY;
    private int dirX;
    private int dirY;
    private final int size = 15;
    private final Color color = Color.magenta;

    public Labda(){
        pozX = GamePanel.WIDTH / 2 - size / 2;
        pozY = GamePanel.HEIGHT -115;
        dirX = 1;
        dirY = 3;
    }

    public void resetPosition() {
        pozX = GamePanel.WIDTH / 2 - size / 2;
        pozY = GamePanel.HEIGHT -115;
        dirX = 1;
        dirY = 3;
    }

    public void move(){
        pozX+=dirX;
        pozY+=dirY;
        if(pozX < 0) dirX = -dirX;
        else if(pozY < 0) dirY = -dirY;
        else if(pozX > GamePanel.WIDTH - size) dirX = -dirX;
    }

    public boolean intersects(Brick b){
        return (this.pozX + 15 >= b.x && this.pozY + 15 >= b.y
                && b.x + b.brickWidth >= this.pozX && b.y + b.brickHeight >= this.pozY);
    }

    public boolean intersects(Paddle p) {
        return this.getBallRect().intersects(p.getPaddleRect());
    }

    public void changeDirY() {
        this.dirY *= -1;
    }

    public void changeDirX() {
        this.dirX *= -1;
    }

    //Getterek

    public int getPozX() {
        return pozX;
    }

    public int getPozY() {
        return pozY;
    }

    public int getDirX() {
        return dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public Rectangle getBallRect() {
        return new Rectangle(pozX, pozY, size, size);
    }

    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    public void setPozY(int pozY) {
        this.pozY = pozY;
    }

    public int getSize() {
        return size;
    }

    //Setterek

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    //Kirajzol

    public void Draw(Graphics g){
        g.setColor(color);
        g.fillOval(pozX, pozY, size, size);
    }
}
