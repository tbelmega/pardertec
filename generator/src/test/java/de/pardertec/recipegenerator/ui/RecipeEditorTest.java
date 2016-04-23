package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.FocusManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;

import static de.pardertec.testing.swing.SwingTestUtil.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Thiemo on 21.04.2016.
 */
public class RecipeEditorTest extends AbstractRecipeGeneratorTest {

    public static final int DELAY = 100;

    public static final String TEST_RECIPE_NAME = "testrecipename";
    public static final String TEST_RECIPE_TEXT = "testrecipetext";
    public static final String TEST_INGREDIENT_NAME = "testIngredientName";
    public static final Recipe TEST_RECIPE = new Recipe(TEST_RECIPE_NAME);
    public static final Measure GRAMS = new Measure("Gramm");
    public static final Ingredient TEST_INGREDIENT = new Ingredient(TEST_INGREDIENT_NAME, GRAMS, VeganismStatus.CONTAINS_MEAT);


    @BeforeMethod
    public void clickRecipes() {
        clickButton(mainFrame, app.i18n(ButtonPanel.BUTTON_MANAGE_RECIPES));

    }

    @Test
    public void testAddRecipe() throws Exception {
        //arrange
        Robot robot = new Robot();

        //create new recipe
        clickButton(mainFrame, app.i18n(AbstractEditor.BTN_NEW));
        typeCharacters(TEST_RECIPE_NAME);

        robot.waitForIdle();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(DELAY);

        //assert
        JList<Recipe> mainList = (JList<Recipe>) findComponentByName(mainFrame, AbstractEditor.MAIN_LIST_NAME);
        Recipe r = mainList.getModel().getElementAt(0);
        assertEquals(TEST_RECIPE_NAME, r.getName());
    }

    @Test
    public void testAddRecipeText() throws Exception {
        //arrange
        Robot robot = new Robot();
        createAndSelectRecipe();

        //act (input recipe text)
        JTextArea recipeTextArea = (JTextArea) findComponentByName(mainFrame, RecipeDetailsPanel.RECIPE_TEXT_AREA_NAME);
        recipeTextArea.requestFocus();
        typeCharacters(TEST_RECIPE_TEXT);
        FocusManager.getCurrentManager().focusNextComponent();
        robot.delay(DELAY);

        //assert
        assertEquals(TEST_RECIPE_TEXT, recipeTextArea.getText());
        assertEquals(TEST_RECIPE_TEXT, TEST_RECIPE.getText());
    }


    @Test
    public void testAddRecipeStep() throws Exception {
        //arrange
        Robot robot = new Robot();
        createAndSelectRecipe();

        //act
        clickButton(mainFrame, app.i18n(RecipeDetailsPanel.BTN_ADD_STEP));
        typeCharacters(TEST_RECIPE_TEXT);
        FocusManager.getCurrentManager().focusNextComponent();
        FocusManager.getCurrentManager().focusNextComponent();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(DELAY);

        //assert
        assertEquals(TEST_RECIPE_TEXT, TEST_RECIPE.getStep(0).getText());
        JList<RecipeStep> steps = (JList<RecipeStep>) findComponentByName(mainFrame, RecipeDetailsPanel.STEPS_LIST_NAME);
        steps.setSelectedIndex(0);
        RecipeStep step = steps.getSelectedValue();
        assertEquals(TEST_RECIPE_TEXT, step.getText());
    }

    @Test
    public void testSetServings() throws Exception {
        //arrange
        Robot robot = new Robot();
        createAndSelectRecipe();

        //act
        JComboBox<String> servingsBox = (JComboBox<String>)findComponentByName(mainFrame, RecipeDetailsPanel.SERVINGS_BOX_NAME);
        servingsBox.setSelectedIndex(3);
        robot.delay(DELAY);

        //assert
        assertEquals(4, TEST_RECIPE.getServings());
    }


    @Test
    public void testSetDifficulty() throws Exception {
        //arrange
        Robot robot = new Robot();
        createAndSelectRecipe();

        //act
        JComboBox<Difficulty> difficultyBox= (JComboBox<Difficulty>)findComponentByName(mainFrame, RecipeDetailsPanel.DIFFICULTY_BOX_NAME);
        difficultyBox.setSelectedItem(Difficulty.EASY);
        robot.waitForIdle();

        //assert
        assertEquals(Difficulty.EASY, TEST_RECIPE.getDifficulty());
    }

    @Test
    public void testSetDuration() throws Exception {
        //arrange
        Robot robot = new Robot();
        createAndSelectRecipe();

        //act
        JTextField duration = (JTextField) findComponentByName(mainFrame, RecipeDetailsPanel.DURATION_TEXTFIELD_NAME);
        duration.setText("");
        duration.requestFocus();
        robot.delay(DELAY);
        robot.waitForIdle();
        typeCharacters("180");
        FocusManager.getCurrentManager().focusNextComponent();
        robot.delay(DELAY);

        //assert
        assertEquals("180", duration.getText());
        assertEquals(180, TEST_RECIPE.getDuration());
    }


    @Test
    public void testAddIngredient() throws Exception {
        //arrange
        Robot robot = new Robot();
        createAndSelectRecipe();
        app.getCollection().add(TEST_INGREDIENT);

        //act
        clickButton(mainFrame, app.i18n(RecipeDetailsPanel.BTN_ADD_INGREDIENT));
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.waitForIdle();
        typeCharacters("50");
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(DELAY);

        //assert
        assertEquals(new Integer(50), TEST_RECIPE.getIngredients().get(TEST_INGREDIENT));

        JList<Map.Entry<Ingredient, Integer>> steps = (JList<Map.Entry<Ingredient, Integer>>) findComponentByName(mainFrame, RecipeDetailsPanel.INGREDIENTS_LIST_NAME);
        steps.setSelectedIndex(0);
        Ingredient ingredient = steps.getSelectedValue().getKey();
        assertEquals(TEST_INGREDIENT, ingredient);
    }


    public void createAndSelectRecipe() {
        app.getCollection().add(TEST_RECIPE);
        clickButton(mainFrame, app.i18n(ButtonPanel.BUTTON_MANAGE_RECIPES));

        JList<Recipe> mainList = (JList<Recipe>) findComponentByName(mainFrame, AbstractEditor.MAIN_LIST_NAME);
        mainList.setSelectedIndex(0);
    }

}
