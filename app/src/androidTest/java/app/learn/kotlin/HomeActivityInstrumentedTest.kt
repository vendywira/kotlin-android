package app.learn.kotlin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import app.learn.kotlin.feature.HomeActivity
import app.learn.kotlin.feature.event.detail.MatchDetailPresenterImpl.Companion.ADDED_TO_FAVORITE
import app.learn.kotlin.feature.event.detail.MatchDetailPresenterImpl.Companion.REMOVED_FROM_FAVORITE
import app.learn.kotlin.feature.team.TeamAdapter
import org.hamcrest.CoreMatchers.not
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HomeActivityInstrumentedTest {

    @Rule
    @JvmField
    var homeActivityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun openDetailLastMatch() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(withId(R.id.base_spinner_id)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.base_spinner_id)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        onView(withId(R.id.base_recycle_view_id))
                .perform(scrollToPosition<TeamAdapter.TeamHolder>(13), click())
        Thread.sleep(1000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.base_recycle_view_id)).check(matches(isDisplayed()))
    }

    @Test
    fun openDetailNextMatch() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.base_progress_bar_id).also { withTimeout(3000) }).check(matches(not(isDisplayed())))
        onView(withId(R.id.menu_match_id)).check(matches(isCompletelyDisplayed())).perform(click())
        onView(withId(R.id.menu_match_id)).check(matches(isFocusable()))
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(withId(R.id.base_spinner_id)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.base_spinner_id)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        onView(withId(R.id.base_recycle_view_id))
                .perform(scrollToPosition<TeamAdapter.TeamHolder>(13), click())
        Thread.sleep(1000)
        onView(withId(R.id.iv_home_team_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_away_team_icon)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.base_recycle_view_id)).check(matches(isDisplayed()))
    }

    @Test
    fun addLastMatchToFavoriteTest() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(withId(R.id.base_spinner_id)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.base_spinner_id)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        onView(withId(R.id.base_recycle_view_id))
                .perform(scrollToPosition<TeamAdapter.TeamHolder>(13), click())
        Thread.sleep(1000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        try {
            // already to add favorite
            clickFavoriteIcon()
        } catch (e: NoMatchingViewException) {
        }

        onView(withId(R.id.menu_favorite)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.base_recycle_view_id)).check(matches(isDisplayed()))
    }

    @Test
    fun addNextMatchToFavoriteTest() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.base_progress_bar_id).also { withTimeout(3000) })
                .check(matches(not(isDisplayed())))
        onView(withId(R.id.menu_match_id))
                .check(matches(isCompletelyDisplayed()))
                .perform(click())
                .check(matches(isFocusable()))
        onView(withId(R.id.menu_match_id)).check(matches(isFocusable()))
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(withId(R.id.base_spinner_id)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.base_spinner_id)).perform(click())
        onView(withText("English League Championship")).perform(click())
        onView(withId(R.id.base_recycle_view_id))
                .perform(scrollToPosition<TeamAdapter.TeamHolder>(5), click())
        Thread.sleep(1000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        try {
            // already to add favorite
            clickFavoriteIcon()
        } catch (e: NoMatchingViewException) {
        }

        onView(withId(R.id.menu_favorite)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.base_recycle_view_id)).check(matches(isDisplayed()))
    }

    @Test
    fun unfavoriteTest() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.base_progress_bar_id)
                .also { withTimeout(3000) })
                .check(matches(not(isDisplayed())))
        onView(withId(R.id.menu_favorite_id))
                .check(matches(isCompletelyDisplayed()))
                .perform(click())
                .check(matches(isFocusable()))
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(withId(R.id.base_recycle_view_id))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        clickUnfavoriteIcon()
        pressBack()
        onView(withId(R.id.base_recycle_view_id))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(1000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        clickUnfavoriteIcon()
        pressBack()
        onView(withId(R.id.base_recycle_view_id)).check(matches(isDisplayed()))
    }

    private fun clickFavoriteIcon() {
        onView(withId(R.id.menu_unfavorite))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())
        onView(withText(ADDED_TO_FAVORITE)).check(matches(isDisplayed()))
    }

    private fun clickUnfavoriteIcon() {
        onView(withId(R.id.menu_favorite))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())
        onView(withText(REMOVED_FROM_FAVORITE)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_unfavorite)).check(matches(isDisplayed()))
    }

    private fun withTimeout(time: Long) {
        Thread.sleep(time)
    }
}