package game;

import java.awt.*;

public class EmptySpace extends Brick{
    public EmptySpace(int x, int y, int width, int height, Color color) {
        super(x,y,width,height,color);
        this.code = 0;
    }

    @Override
    public boolean intersect(Labda l) {
        return false;
    }

    @Override
    public int getCode() {
        return 0;
    }


}
