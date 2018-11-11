package app.learn.kotlin.feature.team.detail

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.R.layout.activity_team_detail
import app.learn.kotlin.feature.base.BaseActivity
import app.learn.kotlin.feature.event.detail.TeamDetailContract
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.loadImageUrl
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.model.entity.FavoriteTeamEntity
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.Team
import app.learn.kotlin.model.vo.TeamVo
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject


class TeamDetailActivity : BaseActivity<TeamDetailContract.Presenter>(),
        TeamDetailContract.View {

    @Inject
    internal lateinit var presenter: TeamDetailContract.Presenter

    private lateinit var progressBar: ProgressBar
    private lateinit var team: TeamVo
    private lateinit var menu: Menu

    private var isFavorite: Boolean = false

    companion object {
        const val FAVORITE_UNSELECTED = 0
        const val FAVORITE_SELECTED = 1
    }

    override fun getTeamId(): String? = team.teamId

    override fun getPresenter(): TeamDetailContract.Presenter? = presenter

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun onInitView() {
        setContentView(activity_team_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressBar = base_progress_bar_id
        team = intent.getParcelableExtra(Constant.TEAM_INTENT)
        Log.d("team detail", team.toString())
        loadBanner(team.teamBanner.orEmpty())
    }

    private fun loadBanner(banner: String){
        img_team_banner.loadImageUrl(banner)
    }

    override fun isExistFavoriteTeam(isFavorite: Boolean) {
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
        presenter.isExistFavoriteTeam(team.teamId)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("test menu","hit menu "+item.itemId)
        when (item.itemId) {
            R.id.menu_favorite -> {
                snackbar(findViewById(android.R.id.content), "Removed from favorite")
                isFavorite = false
                presenter.deleteMatchFromFavorite(team.teamId.orEmpty())
                showFavoriteToggle()
                return true
            }
            R.id.menu_unfavorite -> {
                isFavorite = true
                val favorite = mapper.map(team, FavoriteTeamEntity::class.java)
                presenter.insertTeamToFavorite(favorite)
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

    override fun setViewModel(team: Team) {

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }
}