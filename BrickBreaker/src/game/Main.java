package game;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame obj = new JFrame();
        GamePanel gamepanel = new GamePanel();
        obj.add(gamepanel);
        obj.setSize(GamePanel.WIDTH+14,GamePanel.HEIGHT);
        obj.setLocationRelativeTo(null);
        obj.setTitle("BRICK BREAKER");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
