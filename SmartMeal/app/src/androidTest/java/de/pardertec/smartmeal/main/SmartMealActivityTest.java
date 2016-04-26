package de.pardertec.smartmeal.main;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.pardertec.smartmeal.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static de.pardertec.smartmeal.R.id.search_filter_activity;

import static de.pardertec.smartmeal.R.layout.activity_recipe_list;

/**
 * Created by Thiemo on 26.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SmartMealActivityTest {

    @Rule
    public ActivityTestRule<SmartMealActivity> recipeListActivityTestRule =
            new ActivityTestRule<>(SmartMealActivity.class);

    @Test
    public void clickSearchButton_showsSelectFilterActivity() throws Exception {
        //arrange
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        String menuItemTitle = getInstrumentation().getTargetContext().getResources()
                .getString(R.string.edit_search_filters);

        //act
        onView(withText(menuItemTitle)).perform(click());

        //assert
        onView(withId(search_filter_activity)).check(matches(isDisplayed()));
    }



}
