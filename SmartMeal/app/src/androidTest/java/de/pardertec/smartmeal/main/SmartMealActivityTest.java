package de.pardertec.smartmeal.main;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.pardertec.datamodel.VeganismStatus;
import de.pardertec.smartmeal.R;
import de.pardertec.smartmeal.SmartMealApplication;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Thiemo on 26.04.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SmartMealActivityTest {

    @Rule
    public ActivityTestRule<SmartMealActivity> smartMealActivityTestRule =
            new ActivityTestRule<>(SmartMealActivity.class);

    @Test //Navigation Test
    public void clickMenuItemEditFilters_showsSelectFilterActivity() throws Exception {
        //arrange
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        String menuItemTitle = getInstrumentation().getTargetContext().getResources()
                .getString(R.string.edit_search_filters);

        //act
        onView(withText(menuItemTitle)).perform(click());

        //assert
        onView(withId(R.id.search_filter_activity)).check(matches(isDisplayed()));
    }

    @Test //Feature Test
    public void clickMenuItemEditFilters_showsPredefinedVeganStatus() throws Exception {
        //arrange
        SmartMealApplication app = (SmartMealApplication)
                smartMealActivityTestRule.getActivity().getApplication();
        app.getFilterBundle().setStatus(VeganismStatus.VEGETARIAN);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        String menuItemTitle = getInstrumentation().getTargetContext().getResources()
                .getString(R.string.edit_search_filters);

        //act
        onView(withText(menuItemTitle)).perform(click());

        //assert
        onView(withId(R.id.rb_vegetarian)).check(matches(isSelected()));
    }




}
