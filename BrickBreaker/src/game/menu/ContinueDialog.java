package game.menu;

import game.GamePanel;
import game.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ContinueDialog osztaly, amely megvalositja a JDialog-ot
 * Ket gombbal rendelezik (Continue es Exit)
 * Megkerdezi a felhasznalot, hogy tovabb szeretne-e lepni a kovetkezo palyara
 */
public class ContinueDialog extends JDialog {
    public JButton okButton = new JButton("Continue");
    public JButton cancelButton = new JButton("Exit");
    boolean close = false;

    public ContinueDialog() {

        final CloseAction closeAction = new CloseAction(this);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(!close){
                    closeAction.confirmClosing();
                }
            }
        });

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

    class CloseAction extends AbstractAction {
        private ContinueDialog mainFrame;

        public CloseAction(ContinueDialog mainFrame) {
            super("Exit");
            putValue(MNEMONIC_KEY, KeyEvent.VK_X);
            this.mainFrame = mainFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            confirmClosing();
        }

        public void confirmClosing() {
            int confirmed = JOptionPane.showConfirmDialog(mainFrame,
                    "Are you sure you want to quit?", "Confirm quit",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                // clean up code
                GamePanel.setState(State.MENU);
                close = true;
            } else {
                dispose();
            }
        }
    }
}

