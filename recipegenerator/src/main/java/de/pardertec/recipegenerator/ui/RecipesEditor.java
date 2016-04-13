package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.Ingredient;
import de.pardertec.recipegenerator.model.Recipe;
import de.pardertec.recipegenerator.model.RecipeCollection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class RecipesEditor extends AbstactEditor {

    //Main panel (reciepe list)
    private JPanel recipesListPanel;
    private JList<Recipe> recipeList = new JList<>(new DefaultListModel<>());
    private Button btnNew = new Button(BTN_NEW);
    private Button btnDelete = new Button(BTN_DELETE);
    private JPanel btnPanel = new JPanel();
    protected JPanel editorPanel = new JPanel();

    //Right panel (recipe details)
    private JPanel recipeDetailsPanel;
    private JComboBox<Integer> servingsBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 8, 10, 12});
    private JList<Ingredient> ingredientList = new JList<>(new DefaultListModel<>());
    private JTextArea recipeText = new JTextArea();

    public RecipesEditor() {
        createEditorPanel();
    }

    protected void createEditorPanel() {

        //Main panel (recipe list)
        recipesListPanel = createPanelWithCustomBorderLayout();

        //Center list for recipes
        recipeList.addMouseListener(new RecipeListClickListener());
        createScrollbar(recipeList);
        recipesListPanel.add(recipeList, BorderLayout.CENTER);

        //Button "New"
        btnNew.addActionListener(new AddRecipeAction());
        btnPanel.add(btnNew);

        //Button "Delete"
        btnDelete.addActionListener(new DeleteRecipeAction());
        btnPanel.add(btnDelete);

        recipesListPanel.add(btnPanel, BorderLayout.SOUTH);

        updateReciepeList();

        //Right panel (recipe details)
        recipeDetailsPanel = new JPanel(new GridBagLayout());

        //Recipe text area
        recipeText.setLineWrap(true);
        recipeText.addFocusListener(new RecipeTextFocusListener());
        recipeText.setPreferredSize(new Dimension((int) (COLUMN_WIDTH * 1.5), RESOLUTION_BASE * 2));

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
        recipeDetailsPanel.add(ingredientList, ingredientsListConstraints);


        //Add created elements to editor
        BoxLayout layout = new BoxLayout(editorPanel, BoxLayout.LINE_AXIS);
        editorPanel.setLayout(layout);
        editorPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        editorPanel.add(recipesListPanel, BorderLayout.CENTER);
        editorPanel.add(recipeDetailsPanel, BorderLayout.EAST);
    }

    void updateReciepeList() {
        DefaultListModel<Recipe> recipelistModel = new DefaultListModel<>();

        for (Recipe r : RecipeCollection.getInstance().getRecipesCopy()) {
            recipelistModel.addElement(r);
        }

        recipeList.setModel(recipelistModel);
        selectFirstEntry(recipeList);
        updateRecipeDetails(recipeList.getSelectedValue());
    }

    public Component getEditorPanel() {
        return editorPanel;
    }

    private void createScrollbar(JList list) {
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(SCROLLER_SIZE);
    }

    private class DeleteRecipeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Recipe r = recipeList.getSelectedValue();
            RecipeCollection.getInstance().remove(r);
            RecipesEditor.this.updateReciepeList();
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
                RecipeCollection.getInstance().add(new Recipe(s));
            }

            RecipesEditor.this.updateReciepeList();
        }
    }

    private class RecipeListClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
            JList<Recipe> list = (JList) evt.getSource();
            RecipesEditor.this.updateRecipeDetails(list.getSelectedValue());
        }
    }

    private void updateRecipeDetails(Recipe recipe) {
        if (recipe == null) return;

        recipeText.setText(recipe.getText());
        servingsBox.setSelectedItem(recipe.getServings());

        DefaultListModel<Ingredient> ingredients = new DefaultListModel<>();
        for (Ingredient i : recipe.getIngredients().keySet()) {
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
            Recipe r = recipeList.getSelectedValue();

            if (r == null) return;

            r.setText(recipeText.getText());
        }
    }

    private class ServingsModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Recipe r = recipeList.getSelectedValue();
            r.setServings((Integer) servingsBox.getSelectedItem());
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
                r.removeIngredient(ingredientList.getSelectedValue());
            }
            RecipesEditor.this.updateRecipeDetails(r);
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

        private boolean clickedBelowTheListItems(MouseEvent e) {
            int index = ingredientList.locationToIndex(e.getPoint());
            return index == -1 || !ingredientList.getCellBounds(index, index).contains(e.getPoint());
        }
    }
}
