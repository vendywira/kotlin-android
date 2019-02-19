package app.learn.kotlin.feature.team.list

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import app.learn.kotlin.R
import app.learn.kotlin.feature.adapter.TeamAdapter
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.team.detail.TeamDetailActivity
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team
import app.learn.kotlin.model.vo.TeamVo
import com.airbnb.lottie.LottieAnimationView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.koin.android.ext.android.inject
import javax.inject.Inject

class TeamListFragment : BaseFragment<TeamListContract.Presenter>(), TeamListContract.View {

    private val presenter : TeamListPresenterImpl by inject()

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: LottieAnimationView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var leagueName: String
    private lateinit var adapter: TeamAdapter

    private var leagues : MutableList<String?> = mutableListOf()
    private var listResponseTeam: MutableList<TeamVo> = mutableListOf()

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
        presenter.setupView(this)
        val view = layoutInflater.inflate(R.layout.fragment_team, container, false)
        spinner = view.find(R.id.team_spinner_id)
        listTeam = view.find(R.id.base_recycle_view_id)
        swipeRefresh = view.find(R.id.base_swipe_refresh)
        progressBar = view.findViewById(R.id.base_progress_bar_id)

        presenter.getLeagueList()
        adapter = TeamAdapter(ctx, listResponseTeam) {
            ctx.startActivity<TeamDetailActivity>(
                    Constant.TEAM_INTENT to listResponseTeam[it])
        }

        listTeam.layoutManager = LinearLayoutManager(ctx)
        listTeam.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }

        return view
    }

    override fun getPresenter(): TeamListContract.Presenter? = presenter

    override fun getProgressBar(): LottieAnimationView? = progressBar

    override fun leagueName(): String = leagueName

    override fun showTeamList(teamResponse: ListResponse<Team>?) {
        swipeRefresh.isRefreshing = false
        listResponseTeam.clear()
        teamResponse?.contents?.let { teams ->
            val teamVoList = mutableListOf<TeamVo>()
            teams.forEach { teamVoList.add(mapper.map(it, TeamVo::class.java)) }
            listResponseTeam.addAll(teamVoList)
        }
        adapter.notifyDataSetChanged()
    }

    override fun getListLaugue(leagueResponse: ListResponse<League>?) {
        leagues.clear()
        leagueResponse?.contents?.forEach {
            leagues.add(it.name)
        }

        val spAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_item, leagues)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = false
        super.showLoading()
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        super.hideLoading()
    }
}
