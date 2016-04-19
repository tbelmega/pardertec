package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.Set;

import static de.pardertec.recipegenerator.ui.RecipeGenerator.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class RecipesEditor extends AbstractEditor {


    //Main panel (recipe list)
    private JPanel recipesListPanel;
    private JList<Recipe> recipeList;
    private JPanel btnPanel;

    //Right panel (recipe details)
    private RecipeDetailsPanel detailsPanel;

    public RecipesEditor(RecipeGenerator owner) {
        super(owner);
    }

    protected void createEditorPanel() {

        //Main panel (recipe list)
        recipesListPanel = createPanelWithCustomBorderLayout();

        //Center list for recipes
        recipeList = new JList<>(new DefaultListModel<>());
        recipeList.getSelectionModel().addListSelectionListener(new SelectedRecipeChangeListener());
        recipesListPanel.add(new JScrollPane(recipeList), BorderLayout.CENTER);

        //Button "New"
        btnPanel = new JPanel();
        btnNew.addActionListener(new AddRecipeAction());
        btnPanel.add(btnNew);

        //Button "Delete"
        btnDelete.addActionListener(new DeleteRecipeAction());
        btnPanel.add(btnDelete);

        recipesListPanel.add(btnPanel, BorderLayout.SOUTH);

        detailsPanel = new RecipeDetailsPanel();
        updateRecipeList();

        //Add created elements to editor
        BoxLayout layout = new BoxLayout(editorPanel, BoxLayout.LINE_AXIS);
        editorPanel.setLayout(layout);
        editorPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        editorPanel.add(recipesListPanel, BorderLayout.CENTER);
        editorPanel.add(detailsPanel.recipeDetailsPanel, BorderLayout.EAST);
    }

    void updateRecipeList() {
        updateRecipeList(recipeList.getSelectedValue());
    }

    void updateRecipeList(Recipe selectedRecipe) {
        DefaultListModel<Recipe> recipeListModel = new DefaultListModel<>();

        for (Recipe r : recipesCollection().getRecipesCopy()) {
            recipeListModel.addElement(r);
        }

        recipeList.setModel(recipeListModel);
        detailsPanel.saveRecipeText();
        recipeList.setSelectedValue(selectedRecipe, true);
        detailsPanel.update(recipeList.getSelectedValue());
    }



    public Container getEditorPanel() {
        return editorPanel;
    }



    private class DeleteRecipeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Recipe r = recipeList.getSelectedValue();
            recipesCollection().remove(r);
            RecipesEditor.this.updateRecipeList();
        }
    }

    private class AddRecipeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = (String) JOptionPane.showInputDialog(
                    RecipesEditor.this.editorPanel,
                    "Bezeichnung eingeben",
                    "Neues Rezept",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

            if ((s != null) && (s.length() > 0)) {
                Recipe recipe = new Recipe(s);
                recipesCollection().add(recipe);
                RecipesEditor.this.updateRecipeList(recipe);
            }

        }
    }


    private class SelectedRecipeChangeListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            detailsPanel.saveRecipeText();
            detailsPanel.display(recipeList.getSelectedValue());
        }
    }
}
