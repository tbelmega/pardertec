package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class RecipesEditor extends AbstractEditor {

    public static final String NEW_RECIPE = "recipe.new";

    public RecipesEditor(RecipeGenerator owner) {
        super(owner);
    }

    @Override
    protected DetailsPanel createDetailsPanel() {
        return new RecipeDetailsPanel(owner);
    }

    protected void customizeEditorPanel() {
        mainList.getSelectionModel().addListSelectionListener(new SelectedRecipeChangeListener());
    }

    protected DefaultListModel<BusinessObject> getUpdatedListModel() {
        DefaultListModel<BusinessObject> listModel = new DefaultListModel<>();
        for (BusinessObject bo : recipesCollection().getRecipesCopy()) {
            listModel.addElement(bo);
        }
        return listModel;
    }

    private class SelectedRecipeChangeListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            ((RecipeDetailsPanel) detailsPanel).saveRecipeText();
            detailsPanel.display(mainList.getSelectedValue());
        }
    }

    @Override
    protected ActionListener createAddActionListener() {
        return e -> {
            String s = (String) JOptionPane.showInputDialog(
                    RecipesEditor.this.editorPanel,
                    string(SET_NAME),
                    string(NEW_RECIPE),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

            if ((s != null) && (s.length() > 0)) {
                Recipe recipe = new Recipe(s);
                recipesCollection().add(recipe);
                ((RecipeDetailsPanel) detailsPanel).saveRecipeText();
                mainList.setSelectedValue(recipe, true);
                RecipesEditor.this.updateView();
            }

        };
    }
}
