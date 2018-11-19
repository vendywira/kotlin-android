package app.learn.kotlin.feature.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import app.learn.kotlin.R
import app.learn.kotlin.feature.search.event.SearchEventContract
import app.learn.kotlin.feature.search.event.SearchEventFragment
import app.learn.kotlin.feature.search.team.SearchTeamContract
import app.learn.kotlin.feature.search.team.SearchTeamFragment
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.Constant.FRAGMENT_EVENT
import app.learn.kotlin.model.Constant.FRAGMENT_TEAM
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_search.*

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEvent: SearchEventContract.View
    private lateinit var searchTeam: SearchTeamContract.View

    private lateinit var progressBar: LottieAnimationView
    private lateinit var flag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search)
        btn_close.setOnClickListener {
            onBackPressed()
        }

        val search = base_search
        search.setIconifiedByDefault(false)
        search.requestFocus()

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private fun <T : Fragment> showFragment(savedInstanceState: Bundle?, fragment: T) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.search_container, fragment)
                    .commit()
        }
    }
}