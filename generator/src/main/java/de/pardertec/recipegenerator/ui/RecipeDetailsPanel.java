package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.IntStream;

import static de.pardertec.recipegenerator.ui.RecipeGenerator.*;
import static de.pardertec.recipegenerator.ui.UiUtil.clickedBelowTheListItems;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class RecipeDetailsPanel implements DetailsPanel {


    public static final String RECIPE_TEXT_AREA_NAME = "recipeTextArea";
    public static final String RECIPE_STEP_TEXT_AREA_NAME = "recipeStepTextArea";
    public static final String STEPS_LIST_NAME = "recipeStepList";
    public static final String SERVINGS_BOX_NAME = "servingsSelectionBox";
    public static final String DURATION_TEXTFIELD_NAME = "durationTextField";
    public static final String INGREDIENTS_LIST_NAME = "ingredientsList";
    public static final String DIFFICULTY_BOX_NAME = "difficultySelectionBox";

    public static final String BTN_ADD_INGREDIENT = "ingredient.add";
    public static final String BTN_ADD_STEP = "step.add";
    public static final String DURATION_LABEL_TEXT = "duration.in.min";

    private static final String SET_AMOUNT = "set.amount";
    private static final String ADD_INGREDIENT = "ingredient.add";
    private static final String SELECT_INGREDIENT = "ingredient.select";

    public static final String STEP_DESCRIBE = "step.describe";
    public static final String STEP_INSERT_AS_NUMBER = "step.insert.as.number";
    public static final String STEP_ADD = "step.add";
    public static final String WARNING_INVALID_DURATION = "warning.invalid.duration";

    public static final String MEASURE_REPLACE_TAG = "<MEASURE>";

    private final RecipeGenerator owner;
    private Recipe displayedRecipe;
    private JPanel recipeDetailsPanel = new JPanel();

    //Recipe text
    private JTextArea recipeText = new JTextArea(6, 25);

    //Recipe steps
    private ListModel<RecipeStep> stepListModel = new DefaultListModel<>();

    private JList<RecipeStep> stepJList = new JList<>(stepListModel);
    private JButton btnAddStep;

    //duration
    private final JTextField durationTextField = new JTextField(5);

    //difficulty
    private final JComboBox<Difficulty> difficultyJComboBox = new JComboBox<>(Difficulty.values());

    //Number of servings
    private JComboBox<String> servingsBox = new JComboBox<>(new String[]{"1 Portion", "2 Portionen", "3 Portionen",
            "4 Portionen", "5 Portionen", "6 Portionen", "7 Portionen", "8 Portionen",
            "9 Portionen", "10 Portionen", "11 Portionen", "12 Portionen"});

    //Ingredients
    private JList<Map.Entry<Ingredient, Integer>> ingredientList = new JList<>(new DefaultListModel<>());
    private JButton btnAddIngredient;
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
        btnAddStep = new JButton(owner.i18n(BTN_ADD_STEP));
        btnAddStep.addActionListener(e -> addStepToRecipe());
        GridBagConstraints buttonAddStepConstrains = createGridBagConstraints(componentsCounter++);
        recipeDetailsPanel.add(btnAddStep, buttonAddStepConstrains);

        //Selection drop down servings
        servingsBox.setName(SERVINGS_BOX_NAME);
        servingsBox.addActionListener(e -> {
            displayedRecipe.setServings(servingsBox.getSelectedIndex() + 1);
            update();
        });
        GridBagConstraints servingsBoxConstraints = createGridBagConstraints(componentsCounter++);
        servingsBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(servingsBox, servingsBoxConstraints);

        //Selection drop down difficulty
        difficultyJComboBox.setName(DIFFICULTY_BOX_NAME);
        difficultyJComboBox.addActionListener(e -> {
            displayedRecipe.setDifficulty((Difficulty)difficultyJComboBox.getSelectedItem());
            update();
        });
        GridBagConstraints difficultyBoxConstraints = createGridBagConstraints(componentsCounter++);
        difficultyBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(difficultyJComboBox, difficultyBoxConstraints);

        //Duration text field
        durationTextField.setName(DURATION_TEXTFIELD_NAME);
        JPanel durationPanel = new JPanel();
        durationPanel.add(new JLabel(owner.i18n(DURATION_LABEL_TEXT)));
        durationPanel.add(durationTextField);
        durationTextField.addFocusListener(new DurationFieldFocusListener());
        GridBagConstraints durationPanelConstraints = createGridBagConstraints(componentsCounter++);
        durationPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        recipeDetailsPanel.add(durationPanel, durationPanelConstraints);

        //Ingredients list
        ingredientList.setName(INGREDIENTS_LIST_NAME);
        ingredientList.addMouseListener(new IngredientListClickedListener());
        ingredientList.setPreferredSize(new Dimension(COLUMN_WIDTH, RESOLUTION_BASE * 2));
        GridBagConstraints ingredientsListConstraints = createGridBagConstraints(componentsCounter++);
        ingredientsListConstraints.fill = GridBagConstraints.BOTH;
        recipeDetailsPanel.add(new JScrollPane(ingredientList), ingredientsListConstraints);

        //Add ingredient button
        btnAddIngredient = new JButton(owner.i18n(BTN_ADD_INGREDIENT));
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
        difficultyJComboBox.setSelectedItem(displayedRecipe.getDifficulty());

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
                owner.i18n(SELECT_INGREDIENT),
                owner.i18n(ADD_INGREDIENT),
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "");

        if (i == null) return;

        int amount = Integer.parseInt(

                (String) JOptionPane.showInputDialog(
                        null,
                        owner.i18n(SET_AMOUNT).replace(MEASURE_REPLACE_TAG, i.getMeasure().getName()),
                        owner.i18n(ADD_INGREDIENT),
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
                new JLabel(owner.i18n(STEP_DESCRIBE)),
                scrollPane,
                new JLabel(owner.i18n(STEP_INSERT_AS_NUMBER)),
                stepNumber
        };


        int result = JOptionPane.showConfirmDialog(owner.mainFrame, components, owner.i18n(STEP_ADD), JOptionPane.OK_CANCEL_OPTION);

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
                JOptionPane.showMessageDialog(null, owner.i18n(WARNING_INVALID_DURATION) + durationTextField.getText());
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
