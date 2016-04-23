package de.pardertec.smartmeal;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.pardertec.datamodel.RecipeCollection;
import de.pardertec.smartmeal.recipes.list.RecipeListActivity;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Thiemo on 22.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SmartMealApplicationTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> recipeListActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void testThatRecipeCollectionIsImportedFromFile() throws Exception {
        //arrange
        SmartMealApplication app = (SmartMealApplication) recipeListActivityTestRule.getActivity().getApplication();

        //act
        app.importRecipes();

        //assert
        RecipeCollection recipes = app.recipes();
        assertEquals(3, recipes.getRecipesCopy().size());
    }
}
