package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextField extends JFrame implements ActionListener {
    JButton button;
    JTextField textField;

    public TextField(){
        this.setLayout(new GridBagLayout());

        button = new JButton("Submit");
        button.addActionListener(this);
        JLabel jlabel = new JLabel("Enter your username");

        this.setLocationRelativeTo(null);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250,40));

        this.add(jlabel);
        this.add(button);
        this.add(textField);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            textField.setEditable(false);
            try {
                saveScores();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        dispose();
    }

    public void saveScores() throws IOException {
        FileWriter fw = new FileWriter("scores.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(textField.getText() + "," + InfoPanel.scores + "\n");
        bw.close();
    }
}
