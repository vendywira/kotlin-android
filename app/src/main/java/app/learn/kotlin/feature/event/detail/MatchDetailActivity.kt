package app.learn.kotlin.feature.event.detail

import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseActivity
import app.learn.kotlin.helper.*
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.Team
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject


class MatchDetailActivity : BaseActivity<MatchDetailPresenter>(), MatchDetailView {

    @Inject
    internal lateinit var presenter: MatchDetailPresenter

    private lateinit var progressBar: ProgressBar
    private lateinit var tvStartDate:TextView
    private lateinit var tvHomeTeamName: TextView
    private lateinit var tvHomeScore: TextView
    private lateinit var tvHomeForward: TextView
    private lateinit var tvHomeGoals: TextView
    private lateinit var tvHomeGoalKeeper: TextView
    private lateinit var tvHomeDefense: TextView
    private lateinit var tvHomeShots: TextView
    private lateinit var tvHomeMidfield: TextView
    private lateinit var tvHomeTeamFormation: TextView
    private lateinit var tvHomeSubstitutes: TextView
    private lateinit var tvAwayTeamName: TextView
    private lateinit var tvAwayScore: TextView
    private lateinit var tvAwayForward: TextView
    private lateinit var tvAwayGoals: TextView
    private lateinit var tvAwayGoalKeeper: TextView
    private lateinit var tvAwayDefense: TextView
    private lateinit var tvAwayShots: TextView
    private lateinit var tvAwayMidfield: TextView
    private lateinit var tvAwayTeamFormation: TextView
    private lateinit var tvAwaySubstitutes: TextView
    private lateinit var ivHomeTeamIcon: ImageView
    private lateinit var ivAwayTeamIcon: ImageView
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

    override fun getPresenter(): MatchDetailPresenter? = presenter

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun onInitView() {
        setContentView(R.layout.activity_match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        eventId = intent.getStringExtra(Constant.MATCH_EVENT_ID)

        tvStartDate = tv_start_date
        tvHomeTeamName = tv_home_team_name
        tvHomeScore = tv_home_team_score
        tvHomeForward = tv_home_foward
        tvHomeGoals = tv_home_goals
        tvHomeGoalKeeper = tv_home_goal_keeper
        tvHomeDefense = tv_home_defense
        tvHomeShots = tv_home_shots
        tvHomeMidfield = tv_home_midfield
        tvHomeTeamFormation = tv_home_team_formation
        tvHomeSubstitutes = tv_home_substitutes
        tvAwayTeamName = tv_away_team_name
        tvAwayScore = tv_away_team_score
        tvAwayForward = tv_away_foward
        tvAwayGoals = tv_away_goals
        tvAwayGoalKeeper = tv_away_goal_keeper
        tvAwayDefense = tv_away_defense
        tvAwayShots = tv_away_shots
        tvAwayMidfield = tv_away_midfield
        tvAwayTeamFormation = tv_away_team_formation
        tvAwaySubstitutes = tv_away_substitutes

        ivHomeTeamIcon = iv_home_team_icon
        ivAwayTeamIcon = iv_away_team_icon
        AndroidInjection.inject(this)
        progressBar = base_progress_bar_id
        isFavorite = presenter.isExistFavoriteEvent(eventId)
        presenter.getDetailEvent()
    }

    override fun isExistFavoriteEvent() {
        isFavorite = presenter.isExistFavoriteEvent(eventId)
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
        var inflater = menuInflater
        inflater.inflate(R.menu.toolbar, menu)
        this.menu = menu
        showFavoriteToggle()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                snackbar(findViewById(android.R.id.content), "Removed from favorite")
                isFavorite = false
                presenter.deleteMatchFromFavorite(eventId)
                showFavoriteToggle()
                return true
            }
            R.id.menu_unfavorite -> {
                snackbar(findViewById(android.R.id.content), "Added to favorite")
                isFavorite = true
                var favorite = mapper.map(event, FavoriteEventEntity::class.java)
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

    override fun setEventDetailModel(event: Event) {
        this.event = event
        event.let {
            tvStartDate.text = toSimpleString(it.strDate)

            tvHomeTeamName.text = it.teamHomeName
            tvHomeScore.text = it.teamHomeScore?.toString()
            tvHomeForward.text = it.homeLineupForward
            tvHomeGoals.text = it.homeGoalDetails
            tvHomeGoalKeeper.text = it.homeLineupGoalkeeper
            tvHomeDefense.text = it.homeLineupDefense
            tvHomeShots.text = it.homeShots?.toString()
            tvHomeMidfield.text = it.homeLineupMidfield
            tvHomeTeamFormation.text = it.homeFormation
            tvHomeSubstitutes.text = it.homeLineupSubstitutes

            tvAwayTeamName.text = it.teamAwayName
            tvAwayScore.text = it.teamAwayScore?.toString()
            tvAwayForward.text = it.awayLineupForward
            tvAwayGoals.text = it.awayGoalDetails
            tvAwayGoalKeeper.text = it.awayLineupGoalkeeper
            tvAwayDefense.text = it.awayLineupDefense
            tvAwayShots.text = it.awayShots
            tvAwayMidfield.text = it.awayLineupMidfield
            tvAwayTeamFormation.text = it.awayFormation
            tvAwaySubstitutes.text = it.awayLineupSubstitutes
        }
    }

    override fun setTeamDetailModel(team: Team) {
        if (team.id.orEmpty() == event?.idHomeTeam) {
            teamHome = team
            ivHomeTeamIcon.loadImageUrl(teamHome?.image.orEmpty())
        } else {
            teamAway = team
            ivAwayTeamIcon.loadImageUrl(teamAway?.image.orEmpty())
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }
}