package de.pardertec.smartmeal.recipes.detail;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.recipes.list.RecipeListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Thiemo on 26.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> recipeListActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void clickGo_showRecipeSteps() throws Exception {
        //arrange
        onView(withText("Caesar Salad")).perform(click());
        onView(withId(R.id.btn_start_steps)).perform(ViewActions.scrollTo());

        //act
        onView(withId(R.id.btn_start_steps)).perform(click());
        onView(withId(R.id.step_pager)).perform(ViewActions.swipeLeft());

        //assert
        onView(withId(R.id.step_pager)).check(matches(isDisplayed()));
    }


}