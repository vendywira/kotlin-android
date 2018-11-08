package app.learn.kotlin.feature.search.team

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.search.SearchContract
import app.learn.kotlin.feature.team.TeamAdapter
import app.learn.kotlin.helper.gone
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.recycle_swipe_refresh.*
import kotlinx.android.synthetic.main.recycle_swipe_refresh.view.*
import kotlinx.android.synthetic.main.fragment_match.view.*
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.ctx
import javax.inject.Inject

class SearchTeamFragment : BaseFragment<SearchTeamContract.Presenter>(), SearchTeamContract.View {

    @Inject
    internal lateinit var presenter: SearchTeamContract.Presenter

    private lateinit var recycleView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: TeamAdapter

    private var clubList: MutableList<Team> = mutableListOf()

    override fun getPresenter(): SearchTeamContract.Presenter? = presenter

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = layoutInflater.inflate(R.layout.fragment_search_team, container, false);
        progressBar = view.base_progress_bar_id
        progressBar.invisible()
        recycleView = view.base_recycle_view_id

        adapter = TeamAdapter(ctx, clubList) {
            val toast = Toast.makeText(ctx, it.name, Toast.LENGTH_SHORT)
            toast.show()
        }

        recycleView.layoutManager = LinearLayoutManager(ctx)
        recycleView.adapter = adapter

        return view
    }

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun search(query: String) {
        presenter.searchTeams(query)
    }

    override fun setViewModel(data: ListResponse<Team>) {
        clubList.clear()
        data.contents?.let {
            clubList.addAll(it)
        }
        adapter.notifyDataSetChanged()
    }

}