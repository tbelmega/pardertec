package de.pardertec.recipegenerator.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.testing.swing.SwingTestUtil.assertActiveWindowTitleIs;
import static de.pardertec.testing.swing.SwingTestUtil.clickButton;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Thiemo on 17.04.2016.
 */
public class NavigationTest {

    private RecipeGenerator recipeGenerator;
    private JFrame mainFrame;


    @BeforeMethod
    public void setUp() throws AWTException {
        recipeGenerator = new RecipeGenerator();
        recipeGenerator.initializeFrame();
        mainFrame = recipeGenerator.mainFrame;

    }
    
    @Test
    public void testClickCloseButton() throws Exception {
        //arrange

        //act
        clickButton(mainFrame, BottomPanel.BUTTON_CLOSE);
    
        //assert
        new Robot().waitForIdle();
        assertActiveWindowTitleIs(BottomPanel.CLOSE_DIALOG_TITLE);
    }

    @Test
    public void testThatAllergensButtonOpensAllergensEditor() throws Exception {
        //arrange

        //act
        clickButton(mainFrame, ButtonPanel.BUTTON_MANAGE_ALLERGENS);

        //assert
        new Robot().waitForIdle();
        assertEquals(AllergensEditor.class, recipeGenerator.getActiveEditorClass());
    }

    @Test
    public void testThatIngredientsButtonOpensIngredientsEditor() throws Exception {
        //arrange

        //act
        clickButton(mainFrame, ButtonPanel.BUTTON_MANAGE_INGREDIENTS);

        //assert
        new Robot().waitForIdle();
        assertEquals(IngredientsEditor.class, recipeGenerator.getActiveEditorClass());
    }

    @Test
    public void testRecipesButtonOpensRecipesEditor() throws Exception {
        //arrange

        //act
        clickButton(mainFrame, ButtonPanel.BUTTON_MANAGE_RECIPES);

        //assert
        new Robot().waitForIdle();
        assertEquals(RecipesEditor.class, recipeGenerator.getActiveEditorClass());
    }


}
