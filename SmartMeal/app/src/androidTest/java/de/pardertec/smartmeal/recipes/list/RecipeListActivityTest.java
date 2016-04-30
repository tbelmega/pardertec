package de.pardertec.smartmeal.recipes.list;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.pardertec.smartmeal.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Thiemo on 26.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RecipeListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> recipeListActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Test //Navigation test
    public void clickRecipe_showRecipeDetails() throws Exception {
        //precondition
        onView(withText("Caesar Salad")).check(matches(isDisplayed()));

        //act
        onView(withText("Caesar Salad")).perform(click());

        //assert
        onView(withId(R.id.recipe_title)).check(matches(withText("Caesar Salad")));
    }

    @Test
    public void onSearch_showSearchResults() throws Exception {
        //arrange
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
        Intent result = new Intent(targetContext, RecipeListActivity.class);
        result.setAction(Intent.ACTION_SEARCH);
        result.putExtra(SearchManager.QUERY, "a");

        //act
        recipeListActivityTestRule.launchActivity(result);

        //assert
        onView(withText("Caesar Salad")).check(matches(isDisplayed()));
    }

    @Test
    public void onNoResults_showToast() throws Exception {
        //arrange
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
        Intent result = new Intent(targetContext, RecipeListActivity.class);
        result.setAction(Intent.ACTION_SEARCH);
        result.putExtra(SearchManager.QUERY, "zzzzzzzzzzzzzzzzzzzz");

        //act
        recipeListActivityTestRule.launchActivity(result);

        //assert
        String toastMessage = recipeListActivityTestRule.getActivity().getString(R.string.no_search_results);
        Matcher<Root> rootMatcher = withDecorView(not(is(recipeListActivityTestRule.getActivity().getWindow().getDecorView()))); //copied from stackoverflow O_O works.
        onView(withText(toastMessage)).inRoot(rootMatcher).check(matches(isDisplayed()));
    }
}
