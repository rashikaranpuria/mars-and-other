package android.rashi.com.usingroom

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by rashi on 9/2/18.
 */

@RunWith(AndroidJUnit4::class)
class MarsLauncherUITest {

    @get:Rule
    val mActivityRule: IntentsTestRule<MarsLauncher> = IntentsTestRule<MarsLauncher>(MarsLauncher::class.java)

    @Test
    fun ensureAllElementsDisplayed() {
        onView(withId(R.id.marsLauncherImage)).check(matches(isDisplayed()))
        onView(withId(R.id.welcomeMessage)).check(matches(isDisplayed()))
        onView(withId(R.id.textNameInputLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_launch)).check(matches(isDisplayed()))
    }

    @Test
    fun ensureIfNameEmptyShowsSnackbarMessage() {
        onView(withId(R.id.btn_launch)).perform(click())
        onView(withText(R.string.no_name_error)).check(matches(isDisplayed()))
    }

    @Test
    fun ensureMarsLandingOpenedOnClickLaunchWithDataName() {
        var test_name = "Test Name"
        onView(withId(R.id.et_name)).perform(typeText(test_name))
        onView(withId(R.id.btn_launch)).perform(click())
        intended(allOf(hasComponent(MarsLanding::class.java.getName()), hasExtra("name", test_name)))
        onView(withId(R.id.mars_landing_image)).check(matches(isDisplayed()))
        onView(withId(R.id.welcome_on_mars)).check(matches(withText("Welcome to Mars, $test_name!")))
    }

}