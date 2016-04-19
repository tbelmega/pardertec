package de.pardertec;

import de.pardertec.datamodel.*;
import de.pardertec.recipegenerator.ui.AbstractRecipeGeneratorTest;
import de.pardertec.recipegenerator.ui.BottomPanel;
import de.pardertec.recipegenerator.ui.RecipeGenerator;
import de.pardertec.util.FileUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import static de.pardertec.testing.swing.SwingTestUtil.clickButton;
import static de.pardertec.testing.swing.SwingTestUtil.type;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class ImportExportTest extends AbstractRecipeGeneratorTest {


    private Allergen testAllergen = new Allergen("TestAllergen");
    private Ingredient testIngredient = new Ingredient("TestIngredient", Measure.GRAMS, VeganismStatus.CONTAINS_MEAT);
    private Recipe testRecipe = new Recipe("TestRecipe");



    @Test
    public void testThatExportSucceeds() throws Exception {
        //arrange
        File f = new File("ExportTest.json");
        f.deleteOnExit();
        recipeGenerator.getCollection().add(testAllergen);
        recipeGenerator.getCollection().add(testIngredient);
        recipeGenerator.getCollection().add(testRecipe);

        //act
        clickButton(mainFrame, BottomPanel.BUTTON_EXPORT);
        type(f.getName());
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);

        //assert
        String data = FileUtil.readFile(f);

        System.out.println(data);
        System.out.println(testAllergen.getId());
        System.out.println(testIngredient.getId());
        System.out.println(testRecipe.getId());

        assertTrue(data.contains(testAllergen.getId()));
        assertTrue(data.contains(testIngredient.getId()));
        assertTrue(data.contains(testRecipe.getId()));
    }


    @Test
    public void testThatImportSucceeds() throws Exception {
        //arrange
        File f = new File("ImportTest.json");
        f.deleteOnExit();
        recipeGenerator.getCollection().add(testAllergen);
        recipeGenerator.getCollection().add(testIngredient);
        recipeGenerator.getCollection().add(testRecipe);

        clickButton(mainFrame, BottomPanel.BUTTON_EXPORT);
        type(f.getName());
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);

        recipeGenerator.resetRecipes();

        //act
        clickButton(mainFrame, BottomPanel.BUTTON_IMPORT);
        type(f.getName());
        robot.waitForIdle();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);

        //assert
        assertTrue(recipeGenerator.getCollection().contains(testRecipe));
        assertTrue(recipeGenerator.getCollection().contains(testAllergen));
        assertTrue(recipeGenerator.getCollection().contains(testIngredient));
    }


}