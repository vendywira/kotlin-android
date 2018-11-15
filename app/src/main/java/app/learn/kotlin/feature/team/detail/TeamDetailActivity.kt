package app.learn.kotlin.feature.team.detail

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.R.layout.activity_team_detail
import app.learn.kotlin.feature.base.BaseActivity
import app.learn.kotlin.feature.event.detail.TeamDetailContract
import app.learn.kotlin.feature.base.BasePagerAdapter
import app.learn.kotlin.feature.team.player.PlayerListFragment
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.loadImageUrl
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.TeamEntity
import app.learn.kotlin.model.vo.TeamVo
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout






class TeamDetailActivity : BaseActivity<TeamDetailContract.Presenter>(),
        TeamDetailContract.View {

    @Inject
    internal lateinit var presenter: TeamDetailContract.Presenter

    private lateinit var progressBar: ProgressBar
    private lateinit var team: TeamVo
    private lateinit var menu: Menu
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var basePagerAdapter: BasePagerAdapter

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
        progressBar = base_progress_bar_id
        team = intent.getParcelableExtra(Constant.TEAM_INTENT)
        app_bar_team_detail.addOnOffsetChangedListener(titleShownOnCollapse(collapsing_toolbar))

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        loadBanner(team)
        setTabLayout()
    }

    private fun titleShownOnCollapse(collapsingToolbarLayout: CollapsingToolbarLayout):
            AppBarLayout.OnOffsetChangedListener {

        return object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = team.teamName
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = " "
                    isShow = false
                }
            }
        }
    }

    private fun loadBanner(team: TeamVo){
        img_logo_team.loadImageUrl(team.teamLogoUrl.orEmpty())
        tv_team_name.text = team.teamName
        tv_team_formed_year.text = team.teamFormedYear
        tv_team_stadium.text = team.teamStadiumName
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
                val favorite = mapper.map(team, TeamEntity::class.java)
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

    private fun setTabLayout() {
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewpagerTeam)
        val teamOverviewFragment = TeamDetailOverviewFragment()
        val content = Bundle()
        content.putString(Constant.CONTENT_TEAM_OVERVIEW, team.teamDescription.orEmpty())
        teamOverviewFragment.arguments = content

        val playerListFragment = PlayerListFragment()
        val players = Bundle()
        players.putString(Constant.TEAM_ID, team.teamId)
        playerListFragment.arguments = players

        basePagerAdapter = BasePagerAdapter(supportFragmentManager)
        basePagerAdapter.let {
            it.addFragment(getString(R.string.tab_title_overview), teamOverviewFragment)
            it.addFragment(getString(R.string.tab_title_player), playerListFragment)
            viewPager.adapter = it
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun showMessage(message: String) {
        snackbar(findViewById(android.R.id.content), message)
    }

}