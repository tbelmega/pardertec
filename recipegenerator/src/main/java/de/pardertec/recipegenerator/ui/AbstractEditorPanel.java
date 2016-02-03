package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public abstract class AbstractEditorPanel {


    public static final String BTN_NEW = "Neu";
    public static final String BTN_DELETE = "Löschen";

    protected JPanel panel = createPanel();

    protected abstract JPanel createPanel();


    public Component getPanel() {
        return panel;
    }

    protected JPanel createCustomListPanel() {
        JPanel customListPanel = createPanelWithCustomBoxLayout();
        customListPanel.setPreferredSize(SINGLE_COLUMN_SIZE);
        JList list = new JList();
        list.setPreferredSize(LIST_SIZE);
        customListPanel.add(list);
        Button btnNew = new Button(BTN_NEW);
        btnNew.setMaximumSize(BUTTON_DIMENSION);
        customListPanel.add(btnNew);
        Button btnDelete = new Button(BTN_DELETE);
        btnDelete.setMaximumSize(BUTTON_DIMENSION);
        customListPanel.add(btnDelete);
        return customListPanel;
    }

    protected JPanel createEmptySidePanel() {
        JPanel emptyPanel = createPanelWithCustomBoxLayout();
        emptyPanel.setPreferredSize(SINGLE_COLUMN_SIZE);
        return emptyPanel;
    }

}
