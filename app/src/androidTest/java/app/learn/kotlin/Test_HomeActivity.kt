package app.learn.kotlin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.isClickable
import android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.isFocusable
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import app.learn.kotlin.feature.HomeActivity
import app.learn.kotlin.feature.adapter.TeamAdapter
import app.learn.kotlin.feature.event.detail.MatchDetailPresenterImpl.Companion.ADDED_TO_FAVORITE
import app.learn.kotlin.feature.event.detail.MatchDetailPresenterImpl.Companion.REMOVED_FROM_FAVORITE
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Test_HomeActivity {

    @Rule
    @JvmField
    var homeActivityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun openDetailTeamTest() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(withId(R.id.team_spinner_id)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(R.id.team_spinner_id)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        onView(withId(R.id.base_recycle_view_id))
                .perform(scrollToPosition<TeamAdapter.TeamHolder>(5), click())
        Thread.sleep(3000)
        try {
            clickFavoriteIcon()
        } catch (e: NoMatchingViewException) {
        }
        onView(withId(R.id.menu_favorite)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.base_recycle_view_id)).check(matches(isDisplayed()))
    }


    @Test
    fun openDetailNextMatchTest() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_match_id)).check(matches(isCompletelyDisplayed())).perform(click())
        onView(withId(R.id.menu_match_id)).check(matches(isFocusable()))
        Thread.sleep(3000)
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(TestHelper.withIndex(withId(R.id.event_spinner_id), 0))
                .check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(TestHelper.withIndex(withId(R.id.event_spinner_id), 0)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        Thread.sleep(5000)
        onView(TestHelper.withIndex(withId(R.id.rv_match),0)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))
        Thread.sleep(3000)
        onView(withId(R.id.iv_home_team_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_away_team_icon)).check(matches(isDisplayed()))
        pressBack()
        onView(TestHelper.withIndex(withId(R.id.rv_match),0)).check(matches(isDisplayed()))
    }

    @Test
    fun openDetailLastMatchTest() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_match_id)).check(matches(isCompletelyDisplayed())).perform(click())
        onView(withId(R.id.menu_match_id)).check(matches(isFocusable()))
        Thread.sleep(3000)
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(TestHelper.withIndex(withId(R.id.event_spinner_id), 0))
                .check(matches(isDisplayed()))
        onView(withText("Last")).perform(click())
        onView(TestHelper.withIndex(withId(R.id.event_spinner_id), 1)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        Thread.sleep(5000)
        onView(TestHelper.withIndex(withId(R.id.rv_match),1))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        Thread.sleep(3000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        try {
            clickFavoriteIcon()
        } catch (e: NoMatchingViewException) {
        }
        onView(withId(R.id.menu_favorite)).check(matches(isDisplayed()))
        pressBack()
        onView(TestHelper.withIndex(withId(R.id.rv_match),1)).check(matches(isDisplayed()))
    }

    @Test
    fun addNextMatchToFavoriteTest() {
        addNextMatchToFavorite()
    }

    @Test
    fun addLastMatchToFavoriteTest() {
        addLastMatchToFavorite()
    }


    @Test
    fun unfavoriteTest() {
        addNextMatchToFavorite(4)
        addLastMatchToFavorite(1)
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_favorite_id))
                .check(matches(isCompletelyDisplayed()))
                .perform(click())
                .check(matches(isFocusable()))
        Thread.sleep(3000)
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(TestHelper.withIndex(withId(R.id.rv_favorite),0))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(1000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        clickUnfavoriteIcon()
        pressBack()
        onView(TestHelper.withIndex(withId(R.id.rv_favorite),0))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(1000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        clickUnfavoriteIcon()
        pressBack()
        onView(TestHelper.withIndex(withId(R.id.rv_favorite),0)).check(matches(isDisplayed()))
    }

    private fun addNextMatchToFavorite(index: Int = 0){
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_match_id)).check(matches(isCompletelyDisplayed())).perform(click())
        onView(withId(R.id.menu_match_id)).check(matches(isFocusable()))
        Thread.sleep(3000)
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(TestHelper.withIndex(withId(R.id.event_spinner_id), 0))
                .check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(TestHelper.withIndex(withId(R.id.event_spinner_id), 0)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        Thread.sleep(5000)
        onView(TestHelper.withIndex(withId(R.id.rv_match),0)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(index, click()))
        Thread.sleep(3000)
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
        onView(TestHelper.withIndex(withId(R.id.rv_match),0)).check(matches(isDisplayed()))
    }

    private fun addLastMatchToFavorite(index: Int = 0) {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_match_id)).check(matches(isCompletelyDisplayed())).perform(click())
        onView(withId(R.id.menu_match_id)).check(matches(isFocusable()))
        Thread.sleep(3000)
        onView(withId(R.id.main_container)).check(matches(isDisplayed()))
        onView(TestHelper.withIndex(withId(R.id.event_spinner_id), 0))
                .check(matches(isDisplayed()))
        onView(withText("Last")).perform(click())
        onView(TestHelper.withIndex(withId(R.id.event_spinner_id), 1)).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        Thread.sleep(3000)
        onView(TestHelper.withIndex(withId(R.id.rv_match),1))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(index, click()))
        Thread.sleep(3000)
        onView(withId(R.id.iv_home_team_icon
                .and(R.id.iv_away_team_icon)))
                .check(matches(isDisplayed()))
        try {
            clickFavoriteIcon()
        } catch (e: NoMatchingViewException) {
        }
        onView(withId(R.id.menu_favorite)).check(matches(isDisplayed()))
        pressBack()
        onView(TestHelper.withIndex(withId(R.id.rv_match),1)).check(matches(isDisplayed()))
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