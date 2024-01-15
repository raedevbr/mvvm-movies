package com.raedevbr.movies.ui.components.movies

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.raedevbr.movies.DataStatus
import com.raedevbr.movies.R
import com.raedevbr.movies.TestUtil.dataStatus
import com.raedevbr.movies.ui.components.MainActivity
import com.raedevbr.movies.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MoviesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private var mIdlingResource: IdlingResource? = null

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun displayMoviesList() {
        dataStatus = DataStatus.Success

        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        val scenario = mActivityTestRule.scenario
        scenario.onActivity { activity ->
            activity.startActivity(intent)
        }

        onView(withId(R.id.rv_movies_list)).check(matches(isDisplayed()))
        onView(withId(R.id.pb_loading)).check(matches(not(isDisplayed())))
    }

    @Test
    fun noData() {
        dataStatus = DataStatus.Fail

        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        val scenario = mActivityTestRule.scenario
        scenario.onActivity { activity ->
            activity.startActivity(intent)
        }

        onView(withId(R.id.rv_movies_list)).check(matches(not(isDisplayed())))
        onView(withId(R.id.pb_loading)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_no_data)).check(matches(isDisplayed()))
    }

    @Test
    fun testScroll() {
        dataStatus = DataStatus.Success

        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        val scenario = mActivityTestRule.scenario
        scenario.onActivity { activity ->
            activity.startActivity(intent)
        }

        onView(withId(R.id.rv_movies_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }
}