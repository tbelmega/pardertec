package de.pardertec;

import de.pardertec.datamodel.*;
import de.pardertec.recipegenerator.ui.AbstractRecipeGeneratorTest;
import de.pardertec.recipegenerator.ui.BottomPanel;
import de.pardertec.util.FileUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;
import static de.pardertec.testing.swing.SwingTestUtil.*;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class ImportExportTest extends AbstractRecipeGeneratorTest {

    File f;
    private Allergen testAllergen = new Allergen("TestAllergen");
    private Ingredient testIngredient = new Ingredient("TestIngredient", Measure.GRAMS, VeganismStatus.CONTAINS_MEAT);
    private Recipe testRecipe = new Recipe("TestRecipe");
    private int counter = 0;

    @BeforeClass
    public void cleanBefore(){
        RecipeCollection.clear();
    }

    @AfterClass
    public void cleanAfter(){
        f.delete();
        RecipeCollection.clear();
    }


    @Test
    public void testThatExportSucceeds() throws Exception {
        //arrange
        f = new File("ExportTest" + counter++ + ".json");
        RecipeCollection.add(testAllergen);
        RecipeCollection.add(testIngredient);
        RecipeCollection.add(testRecipe);

        //act
        clickButton(mainFrame, BottomPanel.BUTTON_EXPORT);
        type(f.getName());
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.waitForIdle();

        //assert
        String data = FileUtil.readFile(f);

        assertTrue(data.contains(testAllergen.getId()));
        assertTrue(data.contains(testIngredient.getId()));
        assertTrue(data.contains(testRecipe.getId()));
    }


    @Test
    public void testThatImportSucceeds() throws Exception {
        //arrange
        f = new File("ImportTest" + counter++ + ".json");
        RecipeCollection.add(testAllergen);
        RecipeCollection.add(testIngredient);
        RecipeCollection.add(testRecipe);

        clickButton(mainFrame, BottomPanel.BUTTON_EXPORT);
        type(f.getName());
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.waitForIdle();

        RecipeCollection.clear();

        //act
        clickButton(mainFrame, BottomPanel.BUTTON_IMPORT);
        type(f.getName());
        robot.waitForIdle();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.waitForIdle();

        //assert
        assertTrue(RecipeCollection.contains(testRecipe));
        assertTrue(RecipeCollection.contains(testAllergen));
        assertTrue(RecipeCollection.contains(testIngredient));
    }


}
