package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static de.pardertec.recipegenerator.ui.RecipeGenerator.RESOLUTION_BASE;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class ButtonPanel {

    public static final String BUTTON_MANAGE_RECIPES = "button.manage.recipes";
    public static final String BUTTON_MANAGE_INGREDIENTS = "button.manage.ingredients";
    public static final String BUTTON_MANAGE_ALLERGENS = "button.manage.allergens";
    public static final String BUTTON_MANAGE_MEASURES = "button.manage.measures";

    public static final Dimension BUTTON_DIMENSION = new Dimension(RESOLUTION_BASE * 3, RESOLUTION_BASE);

    private final JPanel panel = new JPanel();
    private final RecipeGenerator owner;

    public  ButtonPanel(RecipeGenerator owner, Dimension size){
        this.owner = owner;

        BoxLayout layout = new BoxLayout(this.panel, BoxLayout.LINE_AXIS);
        this.panel.setLayout(layout);
        this.panel.setBorder(BorderFactory.createEmptyBorder());
        this.panel.setPreferredSize(size);
        this.panel.setMaximumSize(size);
        addButtons();
    }

    private void addButtons() {
        JButton btnRecipes = new JButton(owner.i18n(BUTTON_MANAGE_RECIPES));
        btnRecipes.addActionListener(new ShowRecipesEditorAction());
        btnRecipes.setPreferredSize(BUTTON_DIMENSION);
        this.panel.add(btnRecipes);

        this.panel.add(new JPanel());

        JButton btnIngredients = new JButton(owner.i18n(BUTTON_MANAGE_INGREDIENTS));
        btnIngredients.addActionListener(new ShowIngredientsEditorAction());
        btnIngredients.setPreferredSize(BUTTON_DIMENSION);
        this.panel.add(btnIngredients);

        this.panel.add(new JPanel());

        JButton btnAllergens = new JButton(owner.i18n(BUTTON_MANAGE_ALLERGENS));
        btnAllergens.addActionListener(new ShowAllergensEditorAction());
        btnAllergens.setPreferredSize(BUTTON_DIMENSION);
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
