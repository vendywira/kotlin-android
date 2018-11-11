package app.learn.kotlin.feature.search.team

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.feature.event.detail.TeamDetailPresenterImpl
import app.learn.kotlin.feature.team.detail.TeamDetailActivity
import app.learn.kotlin.feature.team.list.TeamListAdapter
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team
import app.learn.kotlin.model.vo.TeamVo
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import javax.inject.Inject

class SearchTeamFragment : BaseFragment<SearchTeamContract.Presenter>(), SearchTeamContract.View {

    @Inject
    internal lateinit var presenter: SearchTeamContract.Presenter

    private lateinit var recycleView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapterList: TeamListAdapter

    private var listResponseTeam: MutableList<TeamVo> = mutableListOf()

    override fun getPresenter(): SearchTeamContract.Presenter? = presenter

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = layoutInflater.inflate(R.layout.fragment_search_team, container, false);
        progressBar = view.base_progress_bar_id
        progressBar.invisible()
        recycleView = view.base_recycle_view_id

        adapterList = TeamListAdapter(ctx, listResponseTeam) {
            position -> ctx.startActivity<TeamDetailActivity>(
                Constant.TEAM_INTENT to listResponseTeam[position])
        }

        recycleView.layoutManager = LinearLayoutManager(ctx)
        recycleView.adapter = adapterList

        return view
    }

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun search(query: String) {
        presenter.searchTeams(query)
    }

    override fun setViewModel(data: ListResponse<Team>) {
        listResponseTeam.clear()
        data.contents?.let { teams ->
            val teamVoList = mutableListOf<TeamVo>()
            teams.forEach { teamVoList.add(mapper.map(it, TeamVo::class.java)) }
            listResponseTeam.addAll(teamVoList)
        }
        adapterList.notifyDataSetChanged()
    }

}