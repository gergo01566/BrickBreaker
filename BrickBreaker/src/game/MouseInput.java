package game;

import game.menu.LeaderBoard;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //Ha a Score Board opciót választottuk
        if(mx >= (GamePanel.WIDTH - 250)/2 && mx <= ((GamePanel.WIDTH - 250)/2 +250)){
            if (my >= 250 && my <= 300) {
                try {
                    LeaderBoard l = new LeaderBoard();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (mx >= ((GamePanel.WIDTH - 100) / 2) && mx <= ((GamePanel.WIDTH - 100) / 2) + 100) {

            //Ha a Start gombot választottuk
            if (my >= 150 && my <= 200) {
                try {
                    GamePanel p = new GamePanel();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                GamePanel.setState(State.GAME);
            }

            //Ha az Exit gombot választottuk
            if (my >= 450 && my <= 500) {
                System.exit(1);
            }

            //Help gombot választottuk
            if (my >= 350 && my <= 400) {
                String message =
                        """
                                Use the keyboard arrow keys to move your paddle and hit the ball.
                                Grey bricks are unbreakable. Win the game by breaking all\040
                                breakable bricks. After a loss or win, you can enter your username\040
                                You can go back to the main menu by pressing the ESC button
                                on your keyboard. Score is determined by how many bricks you hit\040
                                and broke. You have 3 lives and your top scores will be held.\040
                                Good luck & have fun!""";
                JOptionPane.showMessageDialog(null, message,
                        "Brick Breaker Game Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
