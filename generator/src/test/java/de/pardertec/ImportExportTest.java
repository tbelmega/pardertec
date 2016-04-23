package de.pardertec;

import de.pardertec.datamodel.*;
import de.pardertec.recipegenerator.ui.AbstractRecipeGeneratorTest;
import de.pardertec.recipegenerator.ui.BottomPanel;
import de.pardertec.util.FileUtil;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import static de.pardertec.testing.swing.SwingTestUtil.clickButton;
import static de.pardertec.testing.swing.SwingTestUtil.typeCharacters;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class ImportExportTest extends AbstractRecipeGeneratorTest {

    public static final Measure GRAMS = new Measure("Gramm");
    private Allergen testAllergen = new Allergen("TestAllergen");
    private Ingredient testIngredient = new Ingredient("TestIngredient", GRAMS, VeganismStatus.CONTAINS_MEAT);
    private Recipe testRecipe = new Recipe("TestRecipe");



    @Test
    public void testThatExportSucceeds() throws Exception {
        //arrange
        File f = new File("ExportTest.json");
        f.deleteOnExit();
        app.getCollection().add(testAllergen);
        app.getCollection().add(testIngredient);
        app.getCollection().add(testRecipe);

        //act
        clickButton(mainFrame, app.i18n(BottomPanel.BUTTON_EXPORT));
        typeCharacters(f.getName());
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);

        //assert
        String data = FileUtil.readFile(f);

        assertTrue(data.contains(testAllergen.getId()));
        assertTrue(data.contains(testIngredient.getId()));
        assertTrue(data.contains(testRecipe.getId()));
    }


    @Test
    public void testThatImportSucceeds() throws Exception {
        //arrange
        File f = new File("ImportTest.json");
        f.deleteOnExit();
        app.getCollection().add(testAllergen);
        app.getCollection().add(testIngredient);
        app.getCollection().add(testRecipe);

        clickButton(mainFrame, app.i18n(BottomPanel.BUTTON_EXPORT));
        typeCharacters(f.getName());
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);

        app.resetRecipes();

        //act
        clickButton(mainFrame, app.i18n(BottomPanel.BUTTON_IMPORT));
        typeCharacters(f.getName());
        robot.waitForIdle();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);

        //assert
        assertTrue(app.getCollection().contains(testRecipe));
        assertTrue(app.getCollection().contains(testAllergen));
        assertTrue(app.getCollection().contains(testIngredient));
    }


}
