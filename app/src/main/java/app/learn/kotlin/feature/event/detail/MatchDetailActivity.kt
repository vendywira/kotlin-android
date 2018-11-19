package app.learn.kotlin.feature.event.detail

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import app.learn.kotlin.R
import app.learn.kotlin.R.layout.activity_match_detail
import app.learn.kotlin.feature.base.BaseActivity
import app.learn.kotlin.helper.dateFormating
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.loadImageUrl
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.helper.timeFormating
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.EventEntity
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.Team
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject


class MatchDetailActivity : BaseActivity<MatchDetailContract.Presenter>(),
        MatchDetailContract.View {

    @Inject
    internal lateinit var presenter: MatchDetailContract.Presenter

    private lateinit var progressBar: LottieAnimationView
    private lateinit var menu: Menu

    private var isFavorite: Boolean = false
    private var eventId: String? = null
    private var event: Event? = null
    private var teamHome: Team? = null
    private var teamAway: Team? = null

    companion object {
        const val FAVORITE_UNSELECTED = 0
        const val FAVORITE_SELECTED = 1
    }

    override fun getEventId(): String? = eventId

    override fun getPresenter(): MatchDetailContract.Presenter? = presenter

    override fun getProgressBar(): LottieAnimationView? = progressBar

    override fun onInitView() {
        setContentView(activity_match_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        eventId = intent.getStringExtra(Constant.MATCH_EVENT_ID)
        progressBar = base_progress_bar_id
        presenter.getDetailEvent()
    }

    override fun isExistFavoriteEvent(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        showFavoriteToggle()
    }

    private fun showFavoriteToggle() {
        if (isFavorite) {
            menu.getItem(FAVORITE_SELECTED).visible()
            menu.getItem(FAVORITE_UNSELECTED).invisible()
        } else {
            menu.getItem(FAVORITE_SELECTED).invisible()
            menu.getItem(FAVORITE_UNSELECTED).visible()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar, menu)
        this.menu = menu
        presenter.isExistFavoriteEvent(eventId)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("test menu", "hit menu " + item.itemId)
        when (item.itemId) {
            R.id.menu_favorite -> {
                snackbar(findViewById(android.R.id.content), "Removed from favorite")
                isFavorite = false
                presenter.deleteMatchFromFavorite(eventId)
                showFavoriteToggle()
                return true
            }
            R.id.menu_unfavorite -> {
                isFavorite = true
                val favorite = mapper.map(event, EventEntity::class.java)
                presenter.insertMatchToFavorite(favorite)
                showFavoriteToggle()
                return true
            }
            else -> {
                onBackPressed()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showMessage(message: String) {
        snackbar(findViewById(android.R.id.content), message)
    }

    override fun setEventDetailModel(event: Event) {
        this.event = event
        event.let {
            tv_start_date.text = dateFormating(it.dateEvent)
            tv_start_time.text = timeFormating(it.time.orEmpty())
            tv_home_team_name.text = it.teamHomeName
            tv_home_team_score.text = it.teamHomeScore?.toString()
            tv_home_foward.text = it.homeLineupForward
            tv_home_goals.text = it.homeGoalDetails
            tv_home_goal_keeper.text = it.homeLineupGoalkeeper
            tv_home_defense.text = it.homeLineupDefense
            tv_home_shots.text = it.homeShots?.toString()
            tv_home_midfield.text = it.homeLineupMidfield
            tv_home_team_formation.text = it.homeFormation
            tv_home_substitutes.text = it.homeLineupSubstitutes

            tv_away_team_name.text = it.teamAwayName
            tv_away_team_score.text = it.teamAwayScore?.toString()
            tv_away_foward.text = it.awayLineupForward
            tv_away_goals.text = it.awayGoalDetails
            tv_away_goal_keeper.text = it.awayLineupGoalkeeper
            tv_away_defense.text = it.awayLineupDefense
            tv_away_shots.text = it.awayShots
            tv_away_midfield.text = it.awayLineupMidfield
            tv_away_team_formation.text = it.awayFormation
            tv_away_substitutes.text = it.awayLineupSubstitutes
        }
        supportActionBar?.title = "${event.teamHomeName} vs ${event.teamAwayName}"
    }

    override fun setTeamDetailModel(team: Team) {
        if (team.teamId.orEmpty() == event?.idHomeTeam) {
            teamHome = team
            iv_home_team_icon.loadImageUrl(teamHome?.teamLogoUrl.orEmpty())
        } else {
            teamAway = team
            iv_away_team_icon.loadImageUrl(teamAway?.teamLogoUrl.orEmpty())
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }
}