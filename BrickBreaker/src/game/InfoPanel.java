package game;

import java.awt.*;

/**
 * InfoPanel osztaly, ami tarolja es megjeleniti a jatekos eletet es pontszamat
 */
public class InfoPanel {
    protected static int scores;
    protected static int lives;

    public InfoPanel() {
        scores = 0;
        lives = 3;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.drawString("SCORE: " + scores, 10,25);
        g.drawString("LIVES: " + lives, 630,25);
    }

    public int getScores(){
        return scores;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        InfoPanel.lives = lives;
    }

    public void drawWin(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("arial", Font.BOLD, 50));
        FontMetrics fm = g.getFontMetrics();
        g.drawString("Winner!", (GamePanel.WIDTH-fm.stringWidth("Winner!"))/2, 100);
    }

    public void drawGameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("arial", Font.BOLD, 50));
        FontMetrics fm = g.getFontMetrics();
        g.drawString("GAME OVER", (GamePanel.WIDTH-fm.stringWidth("GAME OVER"))/2, 100);
    }
}
