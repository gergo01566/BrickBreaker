package game;

import java.awt.*;

/**
 * EmptySpace osztaly, amely megvalositja a Brick absztrakt osztalyt es annak absztrakt fuggvenyeit
 */
public class EmptySpace extends Brick{
    public EmptySpace(int x, int y, int width, int height, Color color) {
        super(x,y,width,height,color);
        this.code = 0;
    }

    /**
     * @param l labda amely egy adott teglaval utkozik
     * @return mindig hamis logikai ertekkel ter vissza, mivel a labdat nem akarjuk utkoztetni egy ures hellyel
     */
    @Override
    public boolean intersect(Labda l) {
        return false;
    }

    @Override
    public int getCode() {
        return 0;
    }

}
