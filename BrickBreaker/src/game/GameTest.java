package game;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testGameStartScore() throws IOException {
        GamePanel game = new GamePanel();
        InfoPanel info = new InfoPanel();
        assertEquals(0, info.getScores());
    }

    @Test
    public void testGameStartLives() throws IOException {
        GamePanel game = new GamePanel();
        assertEquals(3, InfoPanel.lives);
    }

//    @Test
//    public void testWallCollision() throws IOException {
//        GamePanel game = new GamePanel();
//        Brick[][] b = game.loadBricks(game.getLevel());
//        Labda l = new Labda();
//        l.setPozX(GamePanel.WIDTH);
//        l.move();
//        assertEquals(-1, l.getDirX());
//        assertEquals(3, l.getDirY());
//        l.setPozY(-l.getSize());
//        l.move();
//        assertEquals(-3, l.getDirY());
//        l.setPozX(-l.getSize());
//        l.move();
//        assertEquals(1, l.getDirX());
//    }

}
