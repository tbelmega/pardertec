package de.pardertec.recipegenerator.ui;

import javax.swing.*;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class AllergensEditor extends AbstractEditorPanel {

    protected JPanel createPanel() {
        JPanel allergensEditor =  createCustomListPanel();
        return allergensEditor;
    }
}
