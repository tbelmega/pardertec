package de.pardertec.smartmeal.calendar;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Thiemo on 01.05.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CalendarActivityTest {

    @Rule
    public ActivityTestRule<CalendarActivity> calendarActivityTestRule =
            new ActivityTestRule<>(CalendarActivity.class);

    @Test
    public void click_show() throws Exception {
        System.out.println("");
    }


}
