package app.learn.kotlin.Activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import app.learn.kotlin.R
import app.learn.kotlin.adapter.RecyclerViewAdapter
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Team
import app.learn.kotlin.network.ApiRepository
import app.learn.kotlin.ui.MainView
import com.google.gson.Gson
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.onRefresh
import android.R as r


class MainActivity : AppCompatActivity(), MainService {


    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var leagueName: String

    private lateinit var presenter: MainPresenter
    private lateinit var adapter: RecyclerViewAdapter
    private var clubList: MutableList<Team> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainView().setContentView(this)
        spinner = find(R.id.spinner)
        listTeam = find(R.id.club_list_id)
        swipeRefresh = find(R.id.swipe_refresh)
        progressBar = find(R.id.progress_bar)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        adapter = RecyclerViewAdapter(this, clubList)  {
            val toast = Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT)
            toast.show()
        }

        listTeam.layoutManager = LinearLayoutManager(this)
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        clubList.clear()
        clubList.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
