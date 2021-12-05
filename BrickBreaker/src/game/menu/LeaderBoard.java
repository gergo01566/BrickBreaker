package game.menu;

import game.PlayerScore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Letrehozza a dicsoseglista ablakot
 */
public class LeaderBoard extends JFrame {
    private final LinkedList<PlayerScore> topScores = new LinkedList<>();

    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    JScrollPane scroll;
    String[] headers = {"Usernames", "Scores"};


    public LeaderBoard() throws IOException {
        this.setTitle("Leaderboard");
        this.pack();
        setSize(400, 300);

        this.setLocationRelativeTo(null);
        model.setColumnIdentifiers(headers);
        table.setModel(model);
        scroll = new JScrollPane(table);

        readScores();

        add(scroll, BorderLayout.CENTER);
        setVisible(true);
    }

    private void readScores() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("scores.txt"));
        String line;
        while ((line = reader.readLine()) != null){
            String[] cols = line.split(",");
            topScores.add(new PlayerScore(Integer.parseInt(cols[1]), cols[0]));
        }
        Collections.sort(topScores);

        for (PlayerScore topScore : topScores) {
            model.addRow(new Object[]{topScore.getName(), topScore.getScore()});
        }
    }

    public LinkedList<PlayerScore> getTopScores() {
        return topScores;
    }
}
