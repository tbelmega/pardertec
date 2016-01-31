package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class BottomPanel {

    public static final String BUTTON_CLOSE = "Schließen";
    public static final String BUTTON_EXPORT = "Export";
    public static final String BUTTON_IMPORT = "Import";

    private final JPanel panel;
    private final JFrame owner;

    public BottomPanel(JFrame owner, Dimension size){
        this.owner = owner;
        this.panel = UiUtil.createPanelWithCustomGridLayout(1, 0);

        this.panel.setPreferredSize(size);
        this.panel.setMaximumSize(size);

        addButtons();
    }

    private void addButtons() {
        Button btn_import = new Button(BUTTON_IMPORT);
        btn_import.addActionListener(new ButtonPressedAction(owner));
        this.panel.add(btn_import);
        Button btn_export = new Button(BUTTON_EXPORT);
        this.panel.add(btn_export);
        addCloseButton(this.owner, panel);
    }

    private static void addCloseButton(final JFrame frameToClose, JPanel container) {
        Button btn_close = new Button(BUTTON_CLOSE);
        container.add(btn_close);

        btn_close.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frameToClose.dispose();
            }
        });
    }

    public JPanel getPanel() {
        return this.panel;
    }

    private class ButtonPressedAction implements ActionListener {
        private final JFrame owner;

        public ButtonPressedAction(JFrame owner) {
           this.owner = owner;
        }

        public void actionPerformed(ActionEvent e)
        {
            JDialog d = new JDialog(this.owner, "Hello", true);
            d.setLocationRelativeTo(this.owner);
            d.setVisible(true);
        }
    }
}
