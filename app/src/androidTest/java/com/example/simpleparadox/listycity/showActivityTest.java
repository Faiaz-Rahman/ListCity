package com.example.simpleparadox.listycity;
import android.app.Activity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simpleparadox.listycityity.R;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class showActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkList() {
        //Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("ADD CITY"); //Click ADD CITY Button

        //Get view for EditText and enter a city name
        solo.enterText((EditText) solo.getView(R.id.editText_name), "1707077");
        solo.clickOnButton("CONFIRM"); //Select CONFIRM Button
        solo.clearEditText((EditText) solo.getView(R.id.editText_name)); //Clear the EditText

        assertTrue(solo.waitForText("1707077", 1, 2000));
        solo.clickOnButton("ClEAR ALL"); //Select ClEAR ALL

        assertFalse(solo.searchText("1707077"));
    }

    @Test
    public void intentSwitcher() {
        ListView listView = (ListView)solo.getView(R.id.city_list);
        View view = listView.getChildAt(0);
        solo.clickOnView(view);
        solo.assertCurrentActivity("Unable to Switch", ShowActivity.class);
    }

    @Test
    public void intentConsistency() {
        ListView listView = (ListView)solo.getView(R.id.city_list);
        View view = listView.getChildAt(0);
        solo.clickOnView(view);
        TextView textView = (TextView)solo.getView(R.id.city_name);
        String name = textView.getText().toString();
        assertEquals("1707077", name);
    }

    @Test
    public void backButtonChecker() {
        ListView listView = (ListView)solo.getView(R.id.city_list);
        View view = listView.getChildAt(0);
        solo.clickOnView(view);
        solo.waitForActivity(ShowActivity.class);
        Button button = (Button)solo.getView(R.id.back_button);

        solo.clickOnView(button);
        solo.assertCurrentActivity("Unable to switch", MainActivity.class);
    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}