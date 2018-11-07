package app.learn.kotlin.feature

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import app.learn.kotlin.R
import app.learn.kotlin.R.id.*
import app.learn.kotlin.R.layout.acivity_home
import app.learn.kotlin.feature.event.match.MatchTabFragment
import app.learn.kotlin.feature.favorite.FavoriteContract
import app.learn.kotlin.feature.favorite.FavoriteFragment
import app.learn.kotlin.feature.search.SearchActivity
import app.learn.kotlin.feature.team.TeamFragment
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import kotlinx.android.synthetic.main.acivity_home.*
import kotlinx.android.synthetic.main.nav_bar_view.*
import org.jetbrains.anko.startActivity
import kotlin.concurrent.thread

class HomeActivity : AppCompatActivity() {

    private var menu: Menu? = null
    private lateinit var fragmentName: String

    companion object {
        private const val MENU_SEARCH = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acivity_home)
        toolbar_home.setTitle(R.string.app_name)
        setSupportActionBar(toolbar_home)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                menu_team_id -> {
                        menu?.getItem(MENU_SEARCH)?.visible()
                    showFragment(savedInstanceState, TeamFragment())
                    fragmentName = Constant.FRAGMENT_TEAM
                }
                menu_match_id -> {
                    menu?.getItem(MENU_SEARCH)?.visible()
                    showFragment(savedInstanceState, MatchTabFragment())
                    fragmentName = Constant.FRAGMENT_EVENT
                }
                menu_favorite_id -> {
                    menu?.getItem(MENU_SEARCH)?.invisible()
                    showFragment(savedInstanceState, FavoriteFragment())
                    fragmentName = Constant.FRAGMENT_FAVORITE
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.menu_match_id
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search_btn, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.search_btn) {
            startActivity<SearchActivity>(Constant.FRAGMENT_SEARCH to fragmentName)
        }
        return false
    }

    private fun <T : Fragment> showFragment(savedInstanceState: Bundle?, fragment: T) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit()
        }
    }


}