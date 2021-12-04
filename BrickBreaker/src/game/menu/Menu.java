package game.menu;

import game.GamePanel;

import java.awt.*;

public class Menu {
    public Rectangle playButton = new Rectangle(((GamePanel.WIDTH - 100)/2), 150, 100 ,50 );
    public Rectangle scoresButton = new Rectangle(((GamePanel.WIDTH - 250)/2), 250, 250 ,50 );
    public Rectangle helpButton = new Rectangle(((GamePanel.WIDTH - 100)/2), 350, 100 ,50 );
    public Rectangle exitButton = new Rectangle(((GamePanel.WIDTH - 100)/2), 450, 100 ,50 );

    public void Rajzol(Graphics g){
        Graphics2D gg = (Graphics2D) g;
        Font fnt = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt);
        g.setColor(Color.white);

        String title = "BRICK BREAKER";
        FontMetrics fm = gg.getFontMetrics();

        g.drawString("BRICK BREAKER",(GamePanel.WIDTH-fm.stringWidth(title))/2, 100);

        Font fnt1 = new Font("arial", Font.BOLD, 30);

        g.setFont(fnt1);

        g.drawString("PLAY", playButton.x + 12, playButton.y + 34);
        g.drawString("SCORE BOARD", scoresButton.x + 12, scoresButton.y + 34);
        g.drawString("HELP", helpButton.x + 12, helpButton.y + 34);
        g.drawString("EXIT", exitButton.x + 16, exitButton.y + 34);


        gg.draw(playButton);
        gg.draw(scoresButton);
        gg.draw(helpButton);
        gg.draw(exitButton);
    }
}