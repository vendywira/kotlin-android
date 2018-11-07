package app.learn.kotlin.feature.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseActivity
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.match.MatchFragment
import app.learn.kotlin.feature.team.TeamFragment
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

    private lateinit var progressBar: ProgressBar
    private lateinit var flag: String

    override fun onInitView() {
        setContentView(R.layout.fragment_search)
        btn_close.setOnClickListener {
            onBackPressed()
        }

        val flag = intent.getStringExtra(Constant.FRAGMENT_SEARCH)
        when (flag) {
            FRAGMENT_EVENT -> {
                showFragment(null, MatchFragment())
            }
            FRAGMENT_TEAM -> {
                showFragment(null, TeamFragment())
            }
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

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

}