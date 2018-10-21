package app.learn.kotlin.Activity

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import app.learn.kotlin.R
import app.learn.kotlin.adapter.RecyclerViewAdapter
import app.learn.kotlin.model.LeagueResponse
import app.learn.kotlin.model.Team
import app.learn.kotlin.model.TeamResponse
import app.learn.kotlin.mvp.base.BaseActivity
import app.learn.kotlin.ui.MainUi
import dagger.android.AndroidInjection
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.onRefresh
import javax.inject.Inject
import android.R as r


class MainActivity : BaseActivity<MainPresenter>(), MainView {

    @Inject
    internal lateinit var presenter : MainPresenter

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var leagueName: String
    private lateinit var adapter: RecyclerViewAdapter

    private var leagues : MutableList<String?> = mutableListOf()
    private var clubList: MutableList<Team> = mutableListOf()

    override fun onReadyView() {
        MainUi().setContentView(this)
        spinner = find(R.id.base_spinner_id)
        listTeam = find(R.id.club_list_id)
        swipeRefresh = find(R.id.base_swipe_refresh)
        progressBar = find(R.id.base_progress_bar_id)
        AndroidInjection.inject(this)

        presenter?.getLaugueList()
        adapter = RecyclerViewAdapter(this, clubList)  {
            val toast = Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT)
            toast.show()
        }

        listTeam.layoutManager = LinearLayoutManager(this)
        listTeam.adapter = adapter

        swipeRefresh.onRefresh {
            presenter?.getTeamList(leagueName)
        }
    }

    override fun getPresenter(): MainPresenter? = presenter

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun leagueName(): String = leagueName

    override fun showTeamList(teamResponse: TeamResponse?) {
        swipeRefresh.isRefreshing = false
        clubList.clear()
        teamResponse?.teams?.let {
            clubList.addAll(it)
        }
        adapter.notifyDataSetChanged()
    }

    override fun getListLaugue(leagueResponse: LeagueResponse?) {
        leagues.clear()
        leagueResponse?.leagues?.forEach {
            leagues.add(it.name)
        }

        val spAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_item, leagues)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter?.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
