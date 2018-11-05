package app.learn.kotlin.feature

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import app.learn.kotlin.R
import app.learn.kotlin.R.id.*
import app.learn.kotlin.R.layout.acivity_home
import app.learn.kotlin.feature.event.match.MatchFragment
import app.learn.kotlin.feature.event.match.MatchTabFragment
import app.learn.kotlin.feature.favorite.FavoriteFragment
import app.learn.kotlin.model.Constant
import kotlinx.android.synthetic.main.acivity_home.*
import kotlinx.android.synthetic.main.nav_bar_view.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acivity_home)
        toolbar_home.setTitle(R.string.app_name)
        setSupportActionBar(toolbar_home)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                menu_team_id -> {
                    showFragment(savedInstanceState,
                            MatchFragment.newInstance(Constant.MATCH_NEXT_MATCH))
                }
                menu_match_id -> {
                    showFragment(savedInstanceState, MatchTabFragment())
                }
                menu_favorite_id -> {
                    showFragment(savedInstanceState, FavoriteFragment())
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.menu_team_id
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