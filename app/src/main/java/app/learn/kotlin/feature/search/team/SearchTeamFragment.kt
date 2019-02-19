package app.learn.kotlin.feature.search.team

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.learn.kotlin.R
import app.learn.kotlin.feature.adapter.TeamAdapter
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.team.detail.TeamDetailActivity
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team
import app.learn.kotlin.model.vo.TeamVo
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.koin.android.ext.android.inject
import javax.inject.Inject

class SearchTeamFragment : BaseFragment<SearchTeamContract.Presenter>(), SearchTeamContract.View {

    private val presenter: SearchTeamPresenterImpl by inject()

    private lateinit var recycleView: RecyclerView
    private lateinit var progressBar: LottieAnimationView
    private lateinit var adapter: TeamAdapter

    private var listResponseTeam: MutableList<TeamVo> = mutableListOf()

    override fun getPresenter(): SearchTeamContract.Presenter? = presenter

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        presenter.setupView(this)
        val view = layoutInflater.inflate(R.layout.fragment_search_team, container, false)
        progressBar = view.base_progress_bar_id
        progressBar.invisible()
        recycleView = view.base_recycle_view_id

        adapter = TeamAdapter(ctx, listResponseTeam) { position ->
            ctx.startActivity<TeamDetailActivity>(
                    Constant.TEAM_INTENT to listResponseTeam[position])
        }

        recycleView.layoutManager = LinearLayoutManager(ctx)
        recycleView.adapter = adapter

        return view
    }

    override fun getProgressBar(): LottieAnimationView? = progressBar

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
        adapter.notifyDataSetChanged()
    }

}