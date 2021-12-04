package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;

public class TextField extends JFrame implements ActionListener, Serializable {
    JButton button;
    JTextField textField;

    public TextField(){

        button = new JButton("Submit");
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.addActionListener(this);
        JLabel jlabel = new JLabel("The game has ended. Pleae enter your username",JLabel.CENTER);

        this.setLocationRelativeTo(null);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300,70));

        this.add(jlabel);
        this.add(button);
        this.add(textField);
        createLayout(jlabel,textField,button);
        this.pack();
        this.setVisible(true);

    }

    private void createLayout(Component... arg) {

        var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(50)
                .addGroup(gl.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                .addGap(50)
        ));

        gl.setVerticalGroup(gl.createSequentialGroup()

                .addGroup(gl.createSequentialGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1], GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(arg[2])

        ));

        pack();
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
