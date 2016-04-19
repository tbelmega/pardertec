package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.Allergen;
import de.pardertec.datamodel.RecipeCollection;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static de.pardertec.testing.swing.SwingTestUtil.*;
import static org.testng.AssertJUnit.assertEquals;


/**
 * Created by Thiemo on 19.04.2016.
 */
public class AllergensEditorTest extends AbstractRecipeGeneratorTest {


    @Test
    public void testAddingAnAllergen() throws Exception {
        //arrange
        Robot robot = new Robot();
        clickButton(mainFrame, ButtonPanel.BUTTON_MANAGE_ALLERGENS);
        JList<Allergen> list = (JList<Allergen>) findComponentByName(mainFrame, AllergensEditor.ALLERGENS_LIST_NAME);

        //act
        clickButton(mainFrame, AbstractEditor.BTN_NEW);
        type("Lactose");
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.waitForIdle();

        //assert
        Allergen a = list.getModel().getElementAt(0);
        RecipeCollection.getInstance().remove(a);
        assertEquals("First list entry should be the added allergen now.",
                "Lactose", a.getName());
    }

    @Test
    public void testDeletingAnAllergen() throws Exception {
        //arrange
        Robot robot = new Robot();
        clickButton(mainFrame, ButtonPanel.BUTTON_MANAGE_ALLERGENS);
        JList<Allergen> list = (JList<Allergen>) findComponentByName(mainFrame, AllergensEditor.ALLERGENS_LIST_NAME);
        clickButton(mainFrame, AbstractEditor.BTN_NEW);
        type("Lactose");
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.waitForIdle();

        //act
        list.setSelectedIndex(0);
        clickButton(mainFrame, AbstractEditor.BTN_DELETE);

        //assert
        assertEquals("ListModel should contain 0 elements now.",
                0, list.getModel().getSize());
    }




}
