package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.SINGLE_COLUMN_SIZE;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class IngredientsEditor extends AbstractEditorPanel {

    @Override
    protected JPanel createPanel() {
        JPanel ingredientsListPanel = createCustomListPanel();

        JPanel ingredientPanel = createPanelWithCustomBoxLayout();
        ingredientPanel.setPreferredSize(SINGLE_COLUMN_SIZE);
        List allergensList = new List();
        ingredientPanel.add(allergensList);


        JPanel recipesEditor = createPanelWithCustomBorderLayout();
        recipesEditor.add(ingredientsListPanel, BorderLayout.CENTER);
        recipesEditor.add(ingredientPanel, BorderLayout.EAST);
        return recipesEditor;
    }


}
