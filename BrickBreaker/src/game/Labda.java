package game;


import java.awt.*;

/**
 * Labda osztaly, aminek van merete, pozicioja, iranya es elore meghatarozott szine
 * A labda visszapattan az utorol, a palya szeleirol es a teglakrol
 */
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

    /**
     * Labda poziciojat es iranyat visszaallitja a kezdopozicioba, ha a jatekos uj palyara lep
     */
    public void resetPosition() {
        pozX = GamePanel.WIDTH / 2 - size / 2;
        pozY = GamePanel.HEIGHT -115;
        dirX = 1;
        dirY = 3;
    }

    /**
     * Ez a fuggveny a labdyt folyamatosan mozgatja es ellenorzi, hogy a labda utkozott-e
     * a palya szelevel, ha igen, akkor az iranyat megvaltoztatja
     */
    public void move(){
        pozX+=dirX;
        pozY+=dirY;
        if(pozX < 0) dirX = -dirX;
        else if(pozY < 0) dirY = -dirY;
        else if(pozX > GamePanel.WIDTH - size) dirX = -dirX;
    }

    /**
     * @param b adott Brick-kel valo utkozest vizsgalja
     * @return hamis vagy igaz ertekkel ter vissza
     */
    public boolean intersects(Brick b){
        return (this.pozX + this.size >= b.getX() && this.pozY + this.size >= b.getY()
                && b.getX() + b.getBrickWidth() >= this.pozX && b.getY() + b.getBrickHeight() >= this.pozY);
    }

    /**
     * @param p A labda utovel vala utkozeset vizsgalja
     * @return
     */
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

    public int getSize() {
        return size;
    }

    public Rectangle getBallRect() {
        return new Rectangle(pozX, pozY, size, size);
    }

    //Setterek

    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    public void setPozY(int pozY) {
        this.pozY = pozY;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }


    /**
     * Kirajzolja a labdát
     */
    public void Draw(Graphics g){
        g.setColor(color);
        g.fillOval(pozX, pozY, size, size);
    }
}
