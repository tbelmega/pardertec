package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class ButtonPanel {

    public static final String BUTTON_MANAGE_RECIPES = "Rezepte verwalten";
    public static final String BUTTON_MANAGE_INGREDIENTS = "Zutaten verwalten";
    public static final String BUTTON_MANAGE_ALLERGENS = "Allergene verwalten";

    private final JPanel panel;
    private final RecipeGenerator owner;

    public  ButtonPanel(RecipeGenerator owner, Dimension size){
        this.owner = owner;

        this.panel = UiUtil.createPanelWithCustomBoxLayout();
        this.panel.setBorder(BorderFactory.createEmptyBorder());
        this.panel.setPreferredSize(size);
        this.panel.setMaximumSize(size);
        addButtons();
    }

    private void addButtons() {
        JButton btnRecipes = new JButton(BUTTON_MANAGE_RECIPES);
        btnRecipes.addActionListener(new ShowRecipesEditorAction());
        this.panel.add(btnRecipes);

        this.panel.add(new JPanel());

        JButton btnIngredients = new JButton(BUTTON_MANAGE_INGREDIENTS);
        btnIngredients.addActionListener(new ShowIngredientsEditorAction());
        this.panel.add(btnIngredients);

        this.panel.add(new JPanel());

        JButton btnAllergens = new JButton(BUTTON_MANAGE_ALLERGENS);
        btnAllergens.addActionListener(new ShowAllergensEditorAction());
        this.panel.add(btnAllergens);
    }


    public JPanel getPanel() {
        return this.panel;
    }

    private class ShowAllergensEditorAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonPanel.this.owner.showAllergensEditor();
        }
    }

    private class ShowIngredientsEditorAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonPanel.this.owner.showIngredientsEditor();
        }
    }

    private class ShowRecipesEditorAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonPanel.this.owner.showRecipesEditor();
        }
    }
}
