package app.learn.kotlin.feature.team

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseActivity
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.helper.gone
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.recycle_swipe_refresh.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import javax.inject.Inject
import android.R as r


class TeamFragment : BaseFragment<TeamContract.Presenter>(), TeamContract.View {

    @Inject
    internal lateinit var presenter : TeamContract.Presenter

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var leagueName: String
    private lateinit var adapter: TeamAdapter

    private var leagues : MutableList<String?> = mutableListOf()
    private var clubList: MutableList<Team> = mutableListOf()

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
        val view = layoutInflater.inflate(R.layout.fragment_team, container, false);
        spinner = view.find(R.id.base_spinner_id)
        listTeam = view.find(R.id.base_recycle_view_id)
        swipeRefresh = view.find(R.id.base_swipe_refresh)
        progressBar = view.find(R.id.base_progress_bar_id)

        presenter.getLeagueList()
        adapter = TeamAdapter(ctx, clubList) {
            val toast = Toast.makeText(ctx, it.name, Toast.LENGTH_SHORT)
            toast.show()
        }

        listTeam.layoutManager = LinearLayoutManager(ctx)
        listTeam.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }

        return view
    }

    override fun getPresenter(): TeamContract.Presenter? = presenter

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun leagueName(): String = leagueName

    fun hideSpinner() = spinner.gone()

    override fun showTeamList(teamResponse: ListResponse<Team>?) {
        swipeRefresh.isRefreshing = false
        clubList.clear()
        teamResponse?.contents?.let {
            clubList.addAll(it)
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
        progressBar.visible()
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        progressBar.invisible()
    }
}
