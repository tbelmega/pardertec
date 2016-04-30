package de.pardertec.smartmeal.recipes.detail;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static de.pardertec.smartmeal.SmartMealApplication.EXTRA_RECIPE_ID;

/**
 * Created by Thiemo on 26.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> recipeListActivityTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent intent = new Intent(targetContext, RecipeDetailActivity.class);
                    intent.putExtra(EXTRA_RECIPE_ID, "061a35ae-4ea9-4e3d-bc62-ff2ee6c11f64"); //FIXME: This id will not work if the imported data change. should be replaced dynamically by an existing recipe ID
                    return intent;
                }
            };

    @Test
    public void clickGo_showRecipeSteps() throws Exception {
        //arrange
        onView(withId(R.id.btn_start_steps)).perform(ViewActions.scrollTo());

        //act
        onView(withId(R.id.btn_start_steps)).perform(click());
        onView(withId(R.id.step_pager)).perform(ViewActions.swipeLeft());

        //assert
        onView(withId(R.id.step_pager)).check(matches(isDisplayed()));
    }


}