package game.menu;

import javax.swing.*;
import java.awt.*;

public class ContinueDialog extends JDialog {
    public JButton okButton = new JButton("Continue");
    public JButton cancelButton = new JButton("Exit");

    public ContinueDialog() {
        setTitle("Do you want to continue?");
        setLayout(new GridBagLayout());
        var gbConstraints = new GridBagConstraints();

        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.gridwidth = 2;
        var question = new JLabel("Do you want to continue?");
        add(question,gbConstraints);

        gbConstraints.gridy = 1;
        gbConstraints.gridwidth = 1;
        add(okButton,gbConstraints);

        gbConstraints.gridx = 1;
        add(cancelButton,gbConstraints);

        pack();
        setSize(300, 100);
        this.setLocationRelativeTo(null);
    }
}

