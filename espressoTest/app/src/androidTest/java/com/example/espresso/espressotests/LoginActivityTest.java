package com.example.espresso.espressotests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.espresso.espressotests.ui.activity.LoginActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private static final int BOTH_FIELDS_ID = -1;
    @Rule
    public ActivityTestRule<LoginActivity>
            mActivityRule = new ActivityTestRule<>(LoginActivity.class, false, true);

    private void doLogin() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Test
    public void whenActivityIsLaunched_shouldDisplayInitialState() {
        onView(withId(R.id.login_image)).check(matches(not(isDisplayed())));
        onView(withId(R.id.login_username)).check(matches(isDisplayed()));
        onView(withId(R.id.login_password)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    @Test
    public void whenPasswordIsEmpty_andClickOnLoginButton_shouldDisplayDialog() {
        testEmptyFieldState(R.id.login_username);
    }

    @Test
    public void whenUserNameIsEmpty_andClickOnLoginButton_shouldDisplayDialog() {
        testEmptyFieldState(R.id.login_password);
    }

    @Test
    public void whenBothFieldsAreEmpty_andClickOnLoginButton_shouldDisplayDialog() {
        testEmptyFieldState(BOTH_FIELDS_ID);
    }

    private void testEmptyFieldState(int notEmptyFieldId) {
        if (notEmptyFieldId != BOTH_FIELDS_ID)
            onView(withId(notEmptyFieldId)).perform(typeText("defaultText"), closeSoftKeyboard());

        onView(withId(R.id.login_button)).perform(click());
        onView(withText(R.string.validation_message)).check(matches(isDisplayed()));
        onView(withText(R.string.ok)).perform(click());
    }
}