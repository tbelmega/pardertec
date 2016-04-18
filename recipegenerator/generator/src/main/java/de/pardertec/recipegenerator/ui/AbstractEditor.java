package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thiemo on 12.04.2016.
 */
public abstract class AbstractEditor {
    public static final String BTN_NEW = "Neu";
    public static final String BTN_DELETE = "Löschen";

    protected JPanel editorPanel = new JPanel();
    protected JButton btnNew = new JButton(BTN_NEW);
    protected JButton btnDelete = new JButton(BTN_DELETE);

    protected void selectFirstEntry(JList<?> list) {
        try {
            list.setSelectedIndex(0);
        } catch (Exception e) {
            //if not able to select index 0, do nothing
        }
    }

    public Container getEditorPanel() {
        return editorPanel;
    }

}
