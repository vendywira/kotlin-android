package app.learn.kotlin.feature.event.detail

import android.util.Log
import android.view.MenuItem
import android.widget.ProgressBar
import app.learn.kotlin.feature.base.BaseActivity
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.loadImageUrl
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEvent
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.Team
import dagger.android.AndroidInjection
import org.jetbrains.anko.setContentView
import org.modelmapper.ModelMapper
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

            contentUI.tvHomeTeamName.text = it.teamHomeName
            contentUI.tvHomeScore.text = it.teamHomeScore?.toString()
            contentUI.tvHomeForward.text = it.homeLineupForward
            contentUI.tvHomeGoals.text = it.homeGoalDetails
            contentUI.tvHomeGoalKeeper.text = it.homeLineupGoalkeeper
            contentUI.tvHomeDefense.text = it.homeLineupDefense
            contentUI.tvHomeShots.text = it.homeShots?.toString()
            contentUI.tvHomeMidfield.text = it.homeLineupMidfield
            contentUI.tvHomeTeamFormation.text = it.homeFormation
            contentUI.tvHomeSubstitutes.text = it.homeLineupSubstitutes

            contentUI.tvAwayTeamName.text = it.teamAwayName
            contentUI.tvAwayScore.text = it.teamAwayScore?.toString()
            contentUI.tvAwayForward.text = it.awayLineupForward
            contentUI.tvAwayGoals.text = it.awayGoalDetails
            contentUI.tvAwayGoalKeeper.text = it.awayLineupGoalkeeper
            contentUI.tvAwayDefense.text = it.awayLineupDefense
            contentUI.tvAwayShots.text = it.awayShots
            contentUI.tvAwayMidfield.text = it.awayLineupMidfield
            contentUI.tvAwayTeamFormation.text = it.awayFormation
            contentUI.tvAwaySubstitutes.text = it.awayLineupSubstitutes
        }

//        val data = converter.convertTo(event)
        val modelMapper = ModelMapper()
        val data = modelMapper.map<FavoriteEvent>(event, FavoriteEvent::class.java)
        Log.d("log converter", "${event.toString()} -> ${data.toString()}")
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