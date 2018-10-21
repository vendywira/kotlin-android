package app.learn.kotlin.mvp.event.detail

import android.view.MenuItem
import android.widget.ProgressBar
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.loadImageUrl
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.Event
import app.learn.kotlin.model.Team
import app.learn.kotlin.mvp.base.BaseActivity
import dagger.android.AndroidInjection
import org.jetbrains.anko.setContentView
import javax.inject.Inject

class MatchDetailActivity : BaseActivity<MatchDetailPresenter>(), MatchDetailView {

    @Inject
    internal lateinit var presenter: MatchDetailPresenter
    private lateinit var contentUI: MatchDetailUI
    private lateinit var progressBar: ProgressBar
    private var eventId: String? = null
    private var event: Event? = null
    private var teamHome: Team? = null
    private var teamAway: Team? = null

    override fun getEventId(): String? = eventId

    override fun getPresenter(): MatchDetailPresenter? = presenter

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun onReadyView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        eventId = intent.getStringExtra(Constant.MATCH_EVENT_ID)
        contentUI = MatchDetailUI()
        contentUI.setContentView(this)
        AndroidInjection.inject(this)
        progressBar = contentUI.progressBar
        presenter.getDetailEvent()
    }

    override fun setEventDetailModel(event: Event) {
        this.event = event
        event.let {
            contentUI.tvStartDate.text = it.strDate

            contentUI.tvHomeTeamName.text = it.strHomeTeam
            contentUI.tvHomeScore.text = it.intHomeScore
            contentUI.tvHomeForward.text = it.strHomeLineupForward
            contentUI.tvHomeGoals.text = it.strHomeGoalDetails
            contentUI.tvHomeGoalKeeper.text = it.strHomeLineupGoalkeeper
            contentUI.tvHomeDefense.text = it.strHomeLineupDefense
            contentUI.tvHomeShots.text = it.intHomeShots
            contentUI.tvHomeMidfield.text = it.strHomeLineupMidfield
            contentUI.tvHomeTeamFormation.text = it.strHomeFormation
            contentUI.tvHomeSubstitutes.text = it.strHomeLineupSubstitutes

            contentUI.tvAwayTeamName.text = it.strAwayTeam
            contentUI.tvAwayScore.text = it.intAwayScore
            contentUI.tvAwayForward.text = it.strAwayLineupForward
            contentUI.tvAwayGoals.text = it.strAwayGoalDetails
            contentUI.tvAwayGoalKeeper.text = it.strAwayLineupGoalkeeper
            contentUI.tvAwayDefense.text = it.strAwayLineupDefense
            contentUI.tvAwayShots.text = it.intAwayShots
            contentUI.tvAwayMidfield.text = it.strAwayLineupMidfield
            contentUI.tvAwayTeamFormation.text = it.strAwayFormation
            contentUI.tvAwaySubstitutes.text = it.strAwayLineupSubstitutes
        }
    }

    override fun setTeamDetailModel(team: Team) {
        if (team.id.orEmpty() == event?.idHomeTeam) {
            teamHome = team
            contentUI.ivHomeTeamIcon.loadImageUrl(teamHome?.image.orEmpty())
        } else {
            teamAway = team
            contentUI.ivAwayTeamIcon.loadImageUrl(teamAway?.image.orEmpty())
        }
    }

    override fun showLoading() {
        progressBar?.visible()
    }

    override fun hideLoading() {
        progressBar?.invisible()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return run {
                onBackPressed()
                true
        }
    }
}