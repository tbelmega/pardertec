package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.Ingredient;
import de.pardertec.recipegenerator.model.Recipe;
import de.pardertec.recipegenerator.model.RecipeCollection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.Set;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class RecipesEditor extends AbstactEditor {

    private static final String BTN_ADD_INGREDIENT = "Zutat hinzufügen";

    //Main panel (reciepe list)
    private JPanel recipesListPanel;
    private JList<Recipe> recipeList = new JList<>(new DefaultListModel<>());
    private Button btnNew = new Button(BTN_NEW);
    private Button btnDelete = new Button(BTN_DELETE);
    private Button btnAddIngredient = new Button(BTN_ADD_INGREDIENT);
    private JPanel btnPanel = new JPanel();

    protected JPanel editorPanel = new JPanel();
    //Right panel (recipe details)
    private JPanel recipeDetailsPanel;
    private JComboBox<String> servingsBox = new JComboBox<>(new String[]{"1 Portion", "2 Portionen", "3 Portionen",
            "4 Portionen", "5 Portionen", "6 Portionen", "7 Portionen", "8 Portionen",
            "9 Portionen", "10 Portionen", "11 Portionen", "12 Portionen"});
    private JList<Map.Entry<Ingredient, Integer>> ingredientList = new JList<>(new DefaultListModel<>());
    private JTextArea recipeText = new JTextArea();
    private Recipe displayedRecipeDetails;

    public RecipesEditor() {
        createEditorPanel();
    }

    protected void createEditorPanel() {

        //Main panel (recipe list)
        recipesListPanel = createPanelWithCustomBorderLayout();

        //Center list for recipes
        recipeList.getSelectionModel().addListSelectionListener(new SelectedRecipeChangeListener());
        recipesListPanel.add(new JScrollPane(recipeList), BorderLayout.CENTER);

        //Button "New"
        btnNew.addActionListener(new AddRecipeAction());
        btnPanel.add(btnNew);

        //Button "Delete"
        btnDelete.addActionListener(new DeleteRecipeAction());
        btnPanel.add(btnDelete);

        recipesListPanel.add(btnPanel, BorderLayout.SOUTH);

        updateRecipeList();

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

        //Selection drop down servings
        servingsBox.addActionListener(new ServingsModifiedListener());
        GridBagConstraints servingsBoxConstraints = new GridBagConstraints();
        servingsBoxConstraints.insets = INSETS;
        servingsBoxConstraints.gridx = 0;
        servingsBoxConstraints.gridy = 1;
        servingsBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(servingsBox, servingsBoxConstraints);

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


        //Add created elements to editor
        BoxLayout layout = new BoxLayout(editorPanel, BoxLayout.LINE_AXIS);
        editorPanel.setLayout(layout);
        editorPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        editorPanel.add(recipesListPanel, BorderLayout.CENTER);
        editorPanel.add(recipeDetailsPanel, BorderLayout.EAST);
    }

    void updateRecipeList() {
        DefaultListModel<Recipe> recipelistModel = new DefaultListModel<>();

        for (Recipe r : RecipeCollection.getInstance().getRecipesCopy()) {
            recipelistModel.addElement(r);
        }

        recipeList.setModel(recipelistModel);
        selectFirstEntry(recipeList);
        updateRecipeDetails(recipeList.getSelectedValue());
    }

    void updateRecipeList(Recipe selectedRecipe) {
        DefaultListModel<Recipe> recipeListModel = new DefaultListModel<>();

        for (Recipe r : RecipeCollection.getInstance().getRecipesCopy()) {
            recipeListModel.addElement(r);
        }

        recipeList.setModel(recipeListModel);
        saveRecipeText();
        recipeList.setSelectedValue(selectedRecipe, true);
        updateRecipeDetails(recipeList.getSelectedValue());
    }



    public Component getEditorPanel() {
        return editorPanel;
    }



    private class DeleteRecipeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Recipe r = recipeList.getSelectedValue();
            RecipeCollection.getInstance().remove(r);
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
                RecipeCollection.getInstance().add(recipe);
                RecipesEditor.this.updateRecipeList(recipe);
            }

        }
    }

    private void updateRecipeDetails(Recipe recipe) {
        if (recipe == null) return;

        recipeText.setText(recipe.getText());
        servingsBox.setSelectedIndex(recipe.getServings() - 1);

        DefaultListModel<Map.Entry<Ingredient, Integer>> ingredients = new DefaultListModel<>();
        for (Map.Entry<Ingredient, Integer> i : recipe.getIngredients().entrySet()) {
            ingredients.addElement(i);
        }
        ingredientList.setModel(ingredients);
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

    private void saveRecipeText() {
        if (displayedRecipeDetails != null) displayedRecipeDetails.setText(recipeText.getText());
    }

    private class ServingsModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Recipe r = recipeList.getSelectedValue();
            r.setServings(servingsBox.getSelectedIndex() + 1);
            RecipesEditor.this.updateRecipeDetails(r);
        }
    }

    private class IngredientListClickedListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Recipe r = recipeList.getSelectedValue();
            if (r == null) return;


            if (clickedBelowTheListItems(e)) {
                addIngredientByDialog(r);
            } else {
                if (e.getClickCount() > 1) r.removeIngredient(ingredientList.getSelectedValue().getKey());
            }
            RecipesEditor.this.updateRecipeDetails(r);
        }



        private boolean clickedBelowTheListItems(MouseEvent e) {
            int index = ingredientList.locationToIndex(e.getPoint());
            return index == -1 || !ingredientList.getCellBounds(index, index).contains(e.getPoint());
        }
    }

    private class AddIngredientlistener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Recipe r = recipeList.getSelectedValue();
            if (r == null) return;
            addIngredientByDialog(r);
            RecipesEditor.this.updateRecipeDetails(r);
        }
    }

    private void addIngredientByDialog(Recipe r) {
        Set<Ingredient> allIngredients = RecipeCollection.getInstance().getIngredientsCopy();
        Ingredient[] possibilities = allIngredients.toArray(new Ingredient[allIngredients.size()]);

        Ingredient i = (Ingredient) JOptionPane.showInputDialog(
                RecipesEditor.this.editorPanel,
                "Zutat auswählen",
                "Zutat hinzufügen",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "");

        if (i == null) return;

        int amount = Integer.parseInt(

                (String) JOptionPane.showInputDialog(
                        RecipesEditor.this.editorPanel,
                        "Menge in " + i.getMeasure() + " eingeben",
                        "Zutat hinzufügen",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "")
        );

        r.setIngredientWithAmount(i, amount);
    }

    private class SelectedRecipeChangeListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            saveRecipeText();
            displayedRecipeDetails = recipeList.getSelectedValue();
            RecipesEditor.this.updateRecipeDetails(displayedRecipeDetails);
        }
    }
}
