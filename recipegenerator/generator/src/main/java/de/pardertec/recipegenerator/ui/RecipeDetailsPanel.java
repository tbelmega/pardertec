package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.Ingredient;
import de.pardertec.datamodel.Recipe;
import de.pardertec.datamodel.RecipeCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.Set;

import static de.pardertec.recipegenerator.ui.RecipeGenerator.COLUMN_WIDTH;
import static de.pardertec.recipegenerator.ui.RecipeGenerator.INSETS;
import static de.pardertec.recipegenerator.ui.RecipeGenerator.RESOLUTION_BASE;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class RecipeDetailsPanel {

    private static final String BTN_ADD_INGREDIENT = "Zutat hinzufügen";


    JPanel recipeDetailsPanel;
    private JComboBox<String> servingsBox = new JComboBox<>(new String[]{"1 Portion", "2 Portionen", "3 Portionen",
            "4 Portionen", "5 Portionen", "6 Portionen", "7 Portionen", "8 Portionen",
            "9 Portionen", "10 Portionen", "11 Portionen", "12 Portionen"});
    private JList<Map.Entry<Ingredient, Integer>> ingredientList = new JList<>(new DefaultListModel<>());
    private JTextArea recipeText = new JTextArea();
    private Recipe displayedRecipe;
    private JButton btnAddIngredient = new JButton(BTN_ADD_INGREDIENT);

    RecipeDetailsPanel() {
        //Right panel (recipe details)
        recipeDetailsPanel = new JPanel(new GridBagLayout());

        //Recipe text area
        recipeText.setLineWrap(true);
        recipeText.setWrapStyleWord(true);
        recipeText.addFocusListener(new RecipeTextFocusListener());
        recipeText.setPreferredSize(new Dimension((int) (COLUMN_WIDTH * 1.5), RESOLUTION_BASE * 3));

        GridBagConstraints recipeTextAreaConstraints = new GridBagConstraints();
        recipeTextAreaConstraints.insets = INSETS;
        recipeTextAreaConstraints.gridx = 0;
        recipeTextAreaConstraints.gridy = 0;
        recipeTextAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(recipeText, recipeTextAreaConstraints);

        //steps list

        //Selection drop down servings
        servingsBox.addActionListener(new ServingsModifiedListener());
        GridBagConstraints servingsBoxConstraints = new GridBagConstraints();
        servingsBoxConstraints.insets = INSETS;
        servingsBoxConstraints.gridx = 0;
        servingsBoxConstraints.gridy = 1;
        servingsBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(servingsBox, servingsBoxConstraints);

        //Duration text field

        //Ingredients list
        ingredientList.addMouseListener(new IngredientListClickedListener());
        ingredientList.setPreferredSize(new Dimension(COLUMN_WIDTH, RESOLUTION_BASE * 2));
        GridBagConstraints ingredientsListConstraints = new GridBagConstraints();
        ingredientsListConstraints.insets = INSETS;
        ingredientsListConstraints.gridx = 0;
        ingredientsListConstraints.gridy = 2;
        ingredientsListConstraints.fill = GridBagConstraints.BOTH;
        recipeDetailsPanel.add(new JScrollPane(ingredientList), ingredientsListConstraints);

        //Add ingredient button
        btnAddIngredient.addActionListener(new AddIngredientlistener());
        GridBagConstraints buttonAddIngredientConstrains = new GridBagConstraints();
        buttonAddIngredientConstrains.gridx = 0;
        buttonAddIngredientConstrains.gridy = 3;
        recipeDetailsPanel.add(btnAddIngredient, buttonAddIngredientConstrains);


    }

    void update(Recipe recipe) {
        if (recipe == null) return;

        recipeText.setText(recipe.getText());
        servingsBox.setSelectedIndex(recipe.getServings() - 1);

        DefaultListModel<Map.Entry<Ingredient, Integer>> ingredients = new DefaultListModel<>();
        for (Map.Entry<Ingredient, Integer> i : recipe.getIngredients().entrySet()) {
            ingredients.addElement(i);
        }
        ingredientList.setModel(ingredients);
    }

    public void display(Recipe selectedValue) {
        displayedRecipe = selectedValue;
        update(displayedRecipe);
    }

    private class RecipeTextFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            saveRecipeText();
        }
    }

    protected void saveRecipeText() {
        if (displayedRecipe != null) displayedRecipe.setText(recipeText.getText());
    }

    private class ServingsModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayedRecipe.setServings(servingsBox.getSelectedIndex() + 1);
            update(displayedRecipe);
        }
    }

    private class IngredientListClickedListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (displayedRecipe == null) return;

            if (clickedBelowTheListItems(e)) {
                addIngredientByDialog(displayedRecipe);
            } else {
                if (e.getClickCount() > 1) displayedRecipe.removeIngredient(ingredientList.getSelectedValue().getKey());
            }
            update(displayedRecipe);
        }



        private boolean clickedBelowTheListItems(MouseEvent e) {
            int index = ingredientList.locationToIndex(e.getPoint());
            return index == -1 || !ingredientList.getCellBounds(index, index).contains(e.getPoint());
        }
    }

    private class AddIngredientlistener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (displayedRecipe == null) return;
            addIngredientByDialog(displayedRecipe);
            update(displayedRecipe);
        }
    }

    private void addIngredientByDialog(Recipe r) {
        Set<Ingredient> allIngredients = RecipeCollection.getIngredientsCopy();
        Ingredient[] possibilities = allIngredients.toArray(new Ingredient[allIngredients.size()]);

        Ingredient i = (Ingredient) JOptionPane.showInputDialog(
                null,
                "Zutat auswählen",
                "Zutat hinzufügen",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "");

        if (i == null) return;

        int amount = Integer.parseInt(

                (String) JOptionPane.showInputDialog(
                        null,
                        "Menge in " + i.getMeasure() + " eingeben",
                        "Zutat hinzufügen",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "")
        );

        r.setIngredientWithAmount(i, amount);
    }
}
