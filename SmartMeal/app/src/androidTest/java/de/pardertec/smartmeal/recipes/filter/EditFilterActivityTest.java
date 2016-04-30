package de.pardertec.smartmeal.recipes.filter;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.pardertec.datamodel.RecipeFilterBundle;
import de.pardertec.datamodel.VeganismStatus;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.SmartMealApplication;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;


/**
 * Created by Thiemo on 26.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class EditFilterActivityTest {

    @Rule
    public ActivityTestRule<EditFilterActivity> editFilterActivityTestRule =
            new ActivityTestRule<>(EditFilterActivity.class);

    @Test //Navigation Test
    public void clickSave_showsSmartMealActivity() throws Exception {
        //act
        onView(withText(R.string.save_filters)).perform(click());

        //assert
        onView(withId(R.id.main_view)).check(matches(isDisplayed()));
    }

    @Test //Navigation Test
    public void clickAbort_showsSmartMealActivity() throws Exception {
        //act
        onView(withText(R.string.abort)).perform(click());

        //assert
        onView(withId(R.id.main_view)).check(matches(isDisplayed()));
    }

    @Test //Feature Test
    public void clickSave_savesSelectedStatus() throws Exception {
        //arrange
        onView(withText(R.string.vegetarian)).perform(click());

        //act
        onView(withText(R.string.save_filters)).perform(click());

        //assert
        SmartMealApplication app = (SmartMealApplication) editFilterActivityTestRule.getActivity().getApplication();
        RecipeFilterBundle filter = app.getFilterBundle();
        assertEquals(VeganismStatus.VEGETARIAN, filter.getStatus());
    }

    @Test //Feature Test
    public void clickReset_resetsSelectedStatus() throws Exception {
        //arrange
        SmartMealApplication app = (SmartMealApplication) editFilterActivityTestRule.getActivity().getApplication();
        RecipeFilterBundle filter = app.getFilterBundle();
        filter.setStatus(VeganismStatus.CONTAINS_MEAT);
        onView(withText(R.string.vegetarian)).perform(click());

        //act
        onView(withText(R.string.reset_filters)).perform(click());

        //assert
        onView(withText(R.string.none)).check(matches(isSelected()));
    }

    @Test //Feature Test
    public void clickAbort_preservesSavedStatus() throws Exception {
        //arrange
        SmartMealApplication app = (SmartMealApplication) editFilterActivityTestRule.getActivity().getApplication();
        RecipeFilterBundle filter = app.getFilterBundle();
        filter.setStatus(VeganismStatus.CONTAINS_MEAT);
        onView(withText(R.string.vegetarian)).perform(click());

        //act
        onView(withText(R.string.abort)).perform(click());

        //assert
        assertEquals(VeganismStatus.CONTAINS_MEAT, filter.getStatus());
    }




}
