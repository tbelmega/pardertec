package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.BUTTON_DIMENSION;
import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.LIST_SIZE;
import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.SINGLE_COLUMN_SIZE;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public abstract class AbstractEditorPanel {


    protected JPanel panel = createPanel();

    protected abstract JPanel createPanel();

    public Component getPanel() {
        return panel;
    }

    protected JPanel createCustomListPanel() {
        JPanel ingredientsListPanel = createPanelWithCustomBoxLayout();
        ingredientsListPanel.setPreferredSize(SINGLE_COLUMN_SIZE);
        List ingredientsList = new List();
        ingredientsList.setPreferredSize(LIST_SIZE);
        ingredientsListPanel.add(ingredientsList);
        Button btnNew = new Button("Neu");
        btnNew.setMaximumSize(BUTTON_DIMENSION);
        ingredientsListPanel.add(btnNew);
        Button btnDelete = new Button("Löschen");
        btnDelete.setMaximumSize(BUTTON_DIMENSION);
        ingredientsListPanel.add(btnDelete);
        return ingredientsListPanel;
    }
}
