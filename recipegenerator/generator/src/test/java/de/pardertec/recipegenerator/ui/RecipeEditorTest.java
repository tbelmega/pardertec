package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.Recipe;
import de.pardertec.datamodel.RecipeStep;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static de.pardertec.testing.swing.SwingTestUtil.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Thiemo on 21.04.2016.
 */
public class RecipeEditorTest {

    private RecipeGenerator recipeGenerator;
    private JFrame mainFrame;
    public static final String TEST_RECIPE_NAME = "testrecipename";
    public static final String TEST_RECIPE_TEXT = "testrecipetext";
    public static final Recipe TEST_RECIPE = new Recipe(TEST_RECIPE_NAME);


    @BeforeMethod
    public void setUp() {
        recipeGenerator = new RecipeGenerator();
        recipeGenerator.initializeFrame();
        mainFrame = recipeGenerator.mainFrame;
        clickButton(mainFrame, ButtonPanel.BUTTON_MANAGE_RECIPES);

    }

    @Test
    public void testAddRecipe() throws Exception {
        //arrange
        Robot robot = new Robot();

        //create new recipe
        clickButton(mainFrame, AbstractEditor.BTN_NEW);
        typeCharacters(TEST_RECIPE_NAME);

        robot.waitForIdle();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);

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
        robot.delay(50);

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
        clickButton(mainFrame, RecipeDetailsPanel.BTN_ADD_STEP);
        typeCharacters(TEST_RECIPE_TEXT);
        FocusManager.getCurrentManager().focusNextComponent();
        FocusManager.getCurrentManager().focusNextComponent();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);

        //assert
        assertEquals(TEST_RECIPE_TEXT, TEST_RECIPE.getStep(0).getText());
        JList<RecipeStep> steps = (JList<RecipeStep>) findComponentByName(mainFrame, RecipeDetailsPanel.STEPS_LIST_NAME);
        steps.setSelectedIndex(0);
        RecipeStep step = steps.getSelectedValue();
        assertEquals(TEST_RECIPE_TEXT, step.getText());
    }

    @Test
    public void testSetServings() throws Exception {

    }

    @Test
    public void testSetDuration() throws Exception {

    }

    @Test
    public void testSetDifficulty() throws Exception {

    }

    @Test
    public void testAddIngredient() throws Exception {

    }


    public void createAndSelectRecipe() {
        recipeGenerator.getCollection().add(TEST_RECIPE);
        clickButton(mainFrame, ButtonPanel.BUTTON_MANAGE_RECIPES);

        JList<Recipe> mainList = (JList<Recipe>) findComponentByName(mainFrame, AbstractEditor.MAIN_LIST_NAME);
        mainList.setSelectedIndex(0);
    }

}
