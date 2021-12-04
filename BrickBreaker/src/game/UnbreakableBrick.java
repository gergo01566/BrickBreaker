package game;

import java.awt.*;

public class UnbreakableBrick extends Brick{
    public UnbreakableBrick(int code, int x, int y, int width, int height, Color color) {
        super(x,y,width,height,color);
        this.code = code;

    }

    @Override
    public boolean intersect(Labda l) {
        return l.intersects(this);
    }

    @Override
    public int getCode() {
        return code;
    }
}
