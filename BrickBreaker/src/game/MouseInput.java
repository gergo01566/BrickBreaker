package game;

import game.menu.LeaderBoard;
import game.menu.Menu;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


/**
 * Lekeri a pozciot, ahova kattintott a felhasznalo a menuben
 */
public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(GamePanel.state == State.MENU){
            //Ha a Score Board opciót választottuk
            if(mx >= Menu.scoresButton.getX() && mx <= Menu.scoresButton.getX() + Menu.scoresButton.getWidth()){
                if (my >= Menu.scoresButton.getY() && my <= Menu.scoresButton.getY()+Menu.scoresButton.getHeight()) {
                    try {
                        LeaderBoard l = new LeaderBoard();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if (mx >= Menu.playButton.getX() && mx <= Menu.playButton.getX() + Menu.playButton.getWidth()) {

                //Ha a Start gombot választottuk
                if (my >= Menu.playButton.getY() && my <= Menu.playButton.getY()+Menu.playButton.getHeight()) {
                    try {
                        GamePanel.setGameCourt(new GameCourt());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    GamePanel.setState(State.GAME);
                }

                //Ha az Exit gombot választottuk
                if (my >= Menu.exitButton.getY() && my <= Menu.exitButton.getY()+Menu.exitButton.getHeight()) {
                    System.exit(1);
                }

                //Help gombot választottuk
                if (my >= Menu.helpButton.getY() && my <= Menu.helpButton.getY()+Menu.helpButton.getHeight()) {
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
