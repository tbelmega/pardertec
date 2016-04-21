package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.BusinessObject;
import de.pardertec.datamodel.Ingredient;
import de.pardertec.datamodel.Recipe;
import de.pardertec.datamodel.RecipeStep;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.IntStream;

import static de.pardertec.recipegenerator.ui.RecipeGenerator.*;
import static de.pardertec.recipegenerator.ui.UiUtil.clickedBelowTheListItems;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class RecipeDetailsPanel implements DetailsPanel {

    public static final String BTN_ADD_INGREDIENT = "Zutat hinzufügen";
    public static final String BTN_ADD_STEP = "Rezeptschritt hinzufügen";
    public static final String DURATION_LABEL_TEXT = "Dauer in Minuten: ";
    public static final String RECIPE_TEXT_AREA_NAME = "recipeTextArea";
    public static final String RECIPE_STEP_TEXT_AREA_NAME = "recipeStepTextArea";
    public static final String STEPS_LIST_NAME = "recipeStepList";

    private final RecipeGenerator owner;
    private Recipe displayedRecipe;
    private JPanel recipeDetailsPanel = new JPanel();

    //Recipe text
    private JTextArea recipeText = new JTextArea(6, 25);

    //Recipe steps
    private ListModel<RecipeStep> stepListModel = new DefaultListModel<>();

    private JList<RecipeStep> stepJList = new JList<>(stepListModel);
    private JButton btnAddStep = new JButton(BTN_ADD_STEP);

    private final JTextField durationTextField = new JTextField(5);

    //Number of servings
    private JComboBox<String> servingsBox = new JComboBox<>(new String[]{"1 Portion", "2 Portionen", "3 Portionen",
            "4 Portionen", "5 Portionen", "6 Portionen", "7 Portionen", "8 Portionen",
            "9 Portionen", "10 Portionen", "11 Portionen", "12 Portionen"});

    //Ingredients
    private JList<Map.Entry<Ingredient, Integer>> ingredientList = new JList<>(new DefaultListModel<>());
    private JButton btnAddIngredient = new JButton(BTN_ADD_INGREDIENT);
    private final JTextArea stepInputDialogTextArea = new JTextArea(6, 40);


    RecipeDetailsPanel(RecipeGenerator owner) {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{COLUMN_WIDTH * 2};
        recipeDetailsPanel.setLayout(layout);

        this.owner = owner;
        int componentsCounter = 0;

        //Recipe text area
        recipeText.setName(RECIPE_TEXT_AREA_NAME);
        recipeText.setLineWrap(true);
        recipeText.setWrapStyleWord(true);
        recipeText.addFocusListener(new RecipeTextFocusListener());

        GridBagConstraints recipeTextAreaConstraints = createGridBagConstraints(componentsCounter++);
        recipeTextAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(new JScrollPane(recipeText,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
                recipeTextAreaConstraints);

        //steps list
        stepJList.setName(RecipeDetailsPanel.STEPS_LIST_NAME);
        stepJList.addMouseListener(new StepsListMouseAdapter());
        GridBagConstraints stepsListConstraints = createGridBagConstraints(componentsCounter++);
        stepsListConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(new JScrollPane(stepJList), stepsListConstraints);

        //Add step button
        btnAddStep.addActionListener(e -> addStepToRecipe());
        GridBagConstraints buttonAddStepConstrains = createGridBagConstraints(componentsCounter++);
        recipeDetailsPanel.add(btnAddStep, buttonAddStepConstrains);

        //Selection drop down servings
        servingsBox.addActionListener(new ServingsModifiedListener());
        GridBagConstraints servingsBoxConstraints = createGridBagConstraints(componentsCounter++);
        servingsBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(servingsBox, servingsBoxConstraints);

        //Duration text field
        JPanel durationPanel = new JPanel();
        durationPanel.add(new JLabel(DURATION_LABEL_TEXT));
        durationPanel.add(durationTextField);
        durationTextField.addFocusListener(new DurationFieldFocusListener());
        GridBagConstraints durationPanelConstraints = createGridBagConstraints(componentsCounter++);
        durationPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(durationPanel, durationPanelConstraints);

        //Ingredients list
        ingredientList.addMouseListener(new IngredientListClickedListener());
        ingredientList.setPreferredSize(new Dimension(COLUMN_WIDTH, RESOLUTION_BASE * 2));
        GridBagConstraints ingredientsListConstraints = createGridBagConstraints(componentsCounter++);
        ingredientsListConstraints.fill = GridBagConstraints.BOTH;
        recipeDetailsPanel.add(new JScrollPane(ingredientList), ingredientsListConstraints);

        //Add ingredient button
        btnAddIngredient.addActionListener(e -> addIngredientToRecipe());
        GridBagConstraints buttonAddIngredientConstrains = createGridBagConstraints(componentsCounter++);
        recipeDetailsPanel.add(btnAddIngredient, buttonAddIngredientConstrains);


    }

    private GridBagConstraints createGridBagConstraints(int componentsCounter) {
        GridBagConstraints ingredientsListConstraints = new GridBagConstraints();
        ingredientsListConstraints.insets = INSETS;
        ingredientsListConstraints.gridx = 0;
        ingredientsListConstraints.gridy = componentsCounter;
        return ingredientsListConstraints;
    }

    @Override
    public void display(BusinessObject selectedRecipe) {
        displayedRecipe = (Recipe) selectedRecipe;
        update();
    }

    private void update() {
        if (displayedRecipe == null) return;

        recipeText.setText(displayedRecipe.getText());

        DefaultListModel<RecipeStep> steps = new DefaultListModel<>();
        for (RecipeStep s : displayedRecipe.getStepsCopy()) {
            steps.addElement(s);
        }
        stepJList.setModel(steps);

        servingsBox.setSelectedIndex(displayedRecipe.getServings() - 1);

        durationTextField.setText(String.valueOf(displayedRecipe.getDuration()));

        DefaultListModel<Map.Entry<Ingredient, Integer>> ingredients = new DefaultListModel<>();
        for (Map.Entry<Ingredient, Integer> i : displayedRecipe.getIngredients().entrySet()) {
            ingredients.addElement(i);
        }
        ingredientList.setModel(ingredients);
    }


    @Override
    public JPanel getPanel() {
        return recipeDetailsPanel;
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

    void saveRecipeText() {
        if (displayedRecipe != null) displayedRecipe.setText(recipeText.getText());
    }

    private class ServingsModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayedRecipe.setServings(servingsBox.getSelectedIndex() + 1);
            update();
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
            update();
        }


        private boolean clickedBelowTheListItems(MouseEvent e) {
            int index = ingredientList.locationToIndex(e.getPoint());
            return index == -1 || !ingredientList.getCellBounds(index, index).contains(e.getPoint());
        }
    }

    public void addIngredientToRecipe() {
        if (displayedRecipe == null) return;
        addIngredientByDialog(displayedRecipe);
        update();
    }

    private void addIngredientByDialog(Recipe displayedRecipe) {
        SortedSet<Ingredient> allIngredients = owner.getCollection().getIngredientsCopy();
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

        displayedRecipe.setIngredientWithAmount(i, amount);
    }

    public void addStepToRecipe() {
        if (displayedRecipe == null) return;
        addRecipeStepByDialog(displayedRecipe);
        update();
    }

    private void addRecipeStepByDialog(Recipe displayedRecipe) {
        stepInputDialogTextArea.setName(RECIPE_STEP_TEXT_AREA_NAME);
        stepInputDialogTextArea.setLineWrap(true);
        stepInputDialogTextArea.setWrapStyleWord(false);
        stepInputDialogTextArea.addAncestorListener(new RequestFocusListener() );
        JScrollPane scrollPane = new JScrollPane(stepInputDialogTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //Create a dropdown list of numbers to choose from, ranging from 1 to current numbers of steps + 1
        int n = displayedRecipe.getStepCount();
        Integer[] values = new Integer[n + 1];
        IntStream.rangeClosed(1, n + 1).forEach(val -> values[val - 1] = val);
        JComboBox<Integer> stepNumber = new JComboBox<>(values);
        stepNumber.setSelectedIndex(n);


        JComponent[] components = new JComponent[]{
                new JLabel("Arbeitsschritt beschreiben"),
                scrollPane,
                new JLabel("Einfügen als Schritt Nummer..."),
                stepNumber
        };


        int result = JOptionPane.showConfirmDialog(owner.mainFrame, components, "Arbeitsschritt hinzufügen", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String text = stepInputDialogTextArea.getText();
            if (StringUtils.isNotBlank(text)) {
                displayedRecipe.addStep(stepNumber.getSelectedIndex(), text);
            }

        }
    }

    private class DurationFieldFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            try {
                int duration = Integer.parseUnsignedInt(durationTextField.getText());
                displayedRecipe.setDuration(duration);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Kein gültiger Wert für die Dauer des Rezepts: " + durationTextField.getText());
            }
        }
    }

    private class StepsListMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (clickedBelowTheListItems(e, stepJList)) addStepToRecipe();
            else if (e.getClickCount() > 1) {
                displayedRecipe.removeStep(stepJList.getSelectedIndex());
                update();
            }
        }
    }
}
