package de.pardertec.smartmeal.recipes.list;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.pardertec.smartmeal.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Thiemo on 26.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RecipeListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> recipeListActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void clickRecipe_showRecipeDetails() throws Exception {
        //act
        onView(withText("Caesar Salad")).perform(click());

        //assert
        onView(withId(R.id.recipe_title)).check(matches(withText("Caesar Salad")));
    }
}
