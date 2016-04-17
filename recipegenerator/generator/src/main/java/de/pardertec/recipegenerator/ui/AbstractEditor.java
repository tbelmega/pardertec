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

    protected void selectFirstEntry(JList<?> list) {
        try {
            list.setSelectedIndex(0);

        } catch (Exception e) {

        }
    }

    public Container getEditorPanel() {
        return editorPanel;
    }

}
