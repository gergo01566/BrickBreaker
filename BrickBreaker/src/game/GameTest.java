package game;

import game.menu.LeaderBoard;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testWinCondition() throws IOException {
        GameCourt gameCourt = new GameCourt();
        gameCourt.getLabda().setDirX(0);
        gameCourt.getLabda().setDirY(0);
        for(int row = 0; row < gameCourt.getMap().getBrow(); row++) {
            for(int col = 0; col < gameCourt.getMap().getBcol(); col++) {
                gameCourt.getMap().getBrickByParam(row, col).setCode(0);
            }
        }
        assertTrue(gameCourt.checkWin());
    }

    @Test
    public void testLoseCondition() throws IOException {
        GameCourt gameCourt = new GameCourt();
        for (int i = 0; i <=2; i++){
            gameCourt.getLabda().setPozY(GamePanel.WIDTH + gameCourt.getLabda().getSize());
            gameCourt.checkGameOver();
        }
        assertTrue(gameCourt.isGameOver());
        assertEquals(0, gameCourt.getInfos().getLives());
    }

    @Test
    public void testStandardBrickCollision() throws IOException {
        GameCourt gameCourt = new GameCourt();
        assertEquals(1, gameCourt.getMap().getBrickByParam(2,4).getCode());
        gameCourt.getLabda().setPozY(gameCourt.getMap().getBrickByParam(2,4).getY());
        gameCourt.getLabda().setPozX(gameCourt.getMap().getBrickByParam(2,4).getX()-gameCourt.getLabda().getSize());
        gameCourt.getMap().getBrickByParam(2,4).intersect(gameCourt.getLabda());
        gameCourt.checkCollision();
        assertEquals(0,gameCourt.getMap().getBrickByParam(2,4).getCode());
    }

    @Test
    public void testChangeBallDirX() throws IOException {
        GameCourt gameCourt = new GameCourt();
        assertEquals(1,gameCourt.getLabda().getDirX());
        gameCourt.getLabda().setPozY(gameCourt.getMap().getBrickByParam(2,4).getY());
        gameCourt.getLabda().setPozX(gameCourt.getMap().getBrickByParam(2,4).getX()-gameCourt.getLabda().getSize());
        gameCourt.getMap().getBrickByParam(2,4).intersect(gameCourt.getLabda());
        gameCourt.checkCollision();
        assertEquals(-1,gameCourt.getLabda().getDirX());
    }

    @Test
    public void testChangeBallDirY() throws IOException {
        GameCourt gameCourt = new GameCourt();
        assertEquals(3,gameCourt.getLabda().getDirY());
        gameCourt.getLabda().setPozY(gameCourt.getMap().getBrickByParam(2,4).getY() - gameCourt.getLabda().getSize());
        gameCourt.getLabda().setPozX(gameCourt.getMap().getBrickByParam(2,4).getX());
        gameCourt.getMap().getBrickByParam(2,4).intersect(gameCourt.getLabda());
        gameCourt.checkCollision();
        assertEquals(-3,gameCourt.getLabda().getDirY());
    }

    @Test
    public void testUnbreakableBrickCollision() throws IOException {
        GameCourt gameCourt = new GameCourt();
        assertEquals(2, gameCourt.getMap().getBrickByParam(2,1).getCode());
        gameCourt.getLabda().setPozY(gameCourt.getMap().getBrickByParam(2,1).getY());
        gameCourt.getLabda().setPozX(gameCourt.getMap().getBrickByParam(2,1).getX()-gameCourt.getLabda().getSize());
        gameCourt.getMap().getBrickByParam(2,1).intersect(gameCourt.getLabda());
        gameCourt.checkCollision();
        assertEquals(2,gameCourt.getMap().getBrickByParam(2,1).getCode());
    }

    @Test
    public void testEmptySpaceCollision() throws IOException {
        GameCourt gameCourt = new GameCourt();
        assertEquals(0, gameCourt.getMap().getBrickByParam(0,1).getCode());
        assertEquals(1, gameCourt.getLabda().getDirX());
        assertEquals(3, gameCourt.getLabda().getDirY());
        gameCourt.getLabda().setPozY(gameCourt.getMap().getBrickByParam(2,2).getY());
        gameCourt.getLabda().setPozX(gameCourt.getMap().getBrickByParam(2,2).getX()-gameCourt.getLabda().getSize());
        gameCourt.getMap().getBrickByParam(2,2).intersect(gameCourt.getLabda());
        gameCourt.checkCollision();
        assertEquals(0,gameCourt.getMap().getBrickByParam(0,1).getCode());
        assertEquals(1, gameCourt.getLabda().getDirX());
        assertEquals(3, gameCourt.getLabda().getDirY());
    }

    @Test
    public void testWallCollision() {
        Labda l = new Labda();
        l.setPozX(-l.getSize());
        l.move();
        assertEquals(-1, l.getDirX());
    }

    @Test
    public void testGameStartLives() throws IOException {
        GameCourt gameCourt = new GameCourt();
        assertEquals(3, InfoPanel.lives);
    }
    
    @Test
    public void testGameStartScore() throws IOException {
        GameCourt gameCourt = new GameCourt();
        assertEquals(0, InfoPanel.scores);
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadBrick() throws IOException {
        Map map = new Map(0);
    }

    @Test
    public void testHighScores() throws IOException {
        LeaderBoard highscores = new LeaderBoard();
        assertTrue(highscores.getTopScores().get(0).getScore() > highscores.getTopScores().get(1).getScore());
        assertTrue(highscores.getTopScores().get(1).getScore() > highscores.getTopScores().get(2).getScore());
        assertTrue(highscores.getTopScores().get(2).getScore() > highscores.getTopScores().get(3).getScore());
        assertTrue(highscores.getTopScores().get(3).getScore() > highscores.getTopScores().get(4).getScore());
    }

    @Test
    public void testNewBeaters() throws IOException {
        LeaderBoard highscores = new LeaderBoard();
        highscores.getTopScores().add(new PlayerScore(300, "winner"));
        highscores.getTopScores().add(new PlayerScore(250, "uj"));
        Collections.sort(highscores.getTopScores());
        assertEquals(highscores.getTopScores().get(0).getName(), "winner");
    }

    @Test
    public void testLoser() throws IOException {
        LeaderBoard highscores = new LeaderBoard();
        highscores.getTopScores().add(new PlayerScore(-10, "loser"));
        Collections.sort(highscores.getTopScores());
        assertEquals(highscores.getTopScores().get(highscores.getTopScores().size()-1).getName(), "loser");
    }

}
