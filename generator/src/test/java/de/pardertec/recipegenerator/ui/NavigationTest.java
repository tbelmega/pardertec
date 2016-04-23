package de.pardertec.recipegenerator.ui;

import org.testng.annotations.Test;

import java.awt.*;

import static de.pardertec.testing.swing.SwingTestUtil.assertActiveWindowTitleIs;
import static de.pardertec.testing.swing.SwingTestUtil.clickButton;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Thiemo on 17.04.2016.
 */
public class NavigationTest extends AbstractRecipeGeneratorTest {

    @Test
    public void testClickCloseButton() throws Exception {
        //arrange

        //act
        clickButton(mainFrame, app.i18n(BottomPanel.BUTTON_CLOSE));
    
        //assert
        new Robot().waitForIdle();
        assertActiveWindowTitleIs(app.i18n(BottomPanel.CLOSE_DIALOG_TITLE));
    }

    @Test
    public void testThatAllergensButtonOpensAllergensEditor() throws Exception {
        //arrange

        //act
        clickButton(mainFrame, app.i18n(ButtonPanel.BUTTON_MANAGE_ALLERGENS));

        //assert
        new Robot().waitForIdle();
        assertEquals(AllergensEditor.class, app.getActiveEditorClass());
    }

    @Test
    public void testThatIngredientsButtonOpensIngredientsEditor() throws Exception {
        //arrange

        //act
        clickButton(mainFrame, app.i18n(ButtonPanel.BUTTON_MANAGE_INGREDIENTS));

        //assert
        new Robot().waitForIdle();
        assertEquals(IngredientsEditor.class, app.getActiveEditorClass());
    }

    @Test
    public void testRecipesButtonOpensRecipesEditor() throws Exception {
        //arrange

        //act
        clickButton(mainFrame, app.i18n(ButtonPanel.BUTTON_MANAGE_RECIPES));

        //assert
        new Robot().waitForIdle();
        assertEquals(RecipesEditor.class, app.getActiveEditorClass());
    }


}
