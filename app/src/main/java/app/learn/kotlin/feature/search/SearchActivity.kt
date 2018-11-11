package app.learn.kotlin.feature.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ProgressBar
import android.widget.SearchView
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseActivity
import app.learn.kotlin.feature.search.event.SearchEventContract
import app.learn.kotlin.feature.search.event.SearchEventFragment
import app.learn.kotlin.feature.search.team.SearchTeamContract
import app.learn.kotlin.feature.search.team.SearchTeamFragment
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.Constant.FRAGMENT_EVENT
import app.learn.kotlin.model.Constant.FRAGMENT_TEAM
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchActivity : BaseActivity<SearchContract.Presenter> (), SearchContract.View {

    @Inject
    internal lateinit var presenter: SearchContract.Presenter

    private lateinit var searchEvent: SearchEventContract.View
    private lateinit var searchTeam: SearchTeamContract.View

    private lateinit var progressBar: ProgressBar
    private lateinit var flag: String

    override fun onInitView() {
        setContentView(R.layout.fragment_search)
        btn_close.setOnClickListener {
            onBackPressed()
        }

        val search = base_search
        search.setIconifiedByDefault(false)
        search.requestFocus()

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                search(query.orEmpty())
                return false
            }

        })

        flag = intent.getStringExtra(Constant.FRAGMENT_SEARCH)
        when (flag) {
            FRAGMENT_EVENT -> {
                val fragment = SearchEventFragment()
                showFragment(null, fragment)
                search.queryHint = "Search Matches"
                searchEvent = fragment
            }
            FRAGMENT_TEAM -> {
                val fragment = SearchTeamFragment()
                showFragment(null, fragment)
                search.queryHint = "Search Teams"
                searchTeam = fragment
            }
        }
    }

    private fun search(query: String) {
        when (flag) {
            FRAGMENT_EVENT -> searchEvent.search(query)
            FRAGMENT_TEAM -> searchTeam.search(query)
        }
    }

    override fun addTeamsFound(teams: ListResponse<Team>) {

    }

    override fun addEventsFound(events: ListResponse<Event>) {

    }

    private fun <T : Fragment> showFragment(savedInstanceState: Bundle?, fragment: T) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.search_container, fragment)
                    .commit()
        }
    }

    override fun getPresenter(): SearchContract.Presenter? = presenter

    override fun getProgressBar(): ProgressBar?  = progressBar
}