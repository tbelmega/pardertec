package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.SINGLE_COLUMN_SIZE;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class RecipesEditor extends AbstractEditorPanel {


    protected JPanel createPanel() {
        JPanel recipesListPanel = createCustomListPanel();

        JPanel recipePanel = createPanelWithCustomBoxLayout();
        recipePanel.setPreferredSize(SINGLE_COLUMN_SIZE);
        List ingredientsList = new List();
        recipePanel.add(ingredientsList);
        TextArea recipeText = new TextArea();
        recipePanel.add(recipeText);


        JPanel recipesEditor = createPanelWithCustomBorderLayout();
        recipesEditor.add(recipesListPanel, BorderLayout.CENTER);
        recipesEditor.add(recipePanel, BorderLayout.EAST);
        return recipesEditor;
    }
}
