package game;

import java.awt.*;

/**
 * StandardBrick osztaly reprezentalja a torheto teglakat, ez az osztaly megvalositja a Brick absztrakt osztalyt es annak
 * metodusait
 */
public class StandardBrick extends Brick{

    private boolean broken = false;

    private void Break() {
        InfoPanel.scores +=10;
        this.broken = true;
        color = Color.BLACK;
        code = 0;
    }

    public StandardBrick(int code, int x, int y, int width, int height, Color color) {
        super(x,y,width,height,color);
        this.code = code;
    }

    @Override
    public boolean intersect(Labda l) {
        if (l.intersects(this) && !broken) {
            this.Break();
            return true;
        }
        return false;
    }

    @Override
    public int getCode() {
        return code;
    }
}
