package app.learn.kotlin.feature.favorite

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.learn.kotlin.R
import app.learn.kotlin.feature.event.match.MatchViewPagerAdapter
import app.learn.kotlin.feature.favorite.event.FavoriteEventFragment
import app.learn.kotlin.feature.favorite.event.FavoriteTeamFragment

class FavoriteTabFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: MatchViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tabs, container, false)
        tabLayout = view.findViewById(R.id.tabs)
        viewPager = view.findViewById(R.id.viewpager)

        viewPagerAdapter = childFragmentManager.let {
            MatchViewPagerAdapter(it)
        }
        viewPagerAdapter.let {
            it.addFragment(getString(R.string.tab_title_matches), FavoriteEventFragment())
            it.addFragment(getString(R.string.tab_title_teams), FavoriteTeamFragment())
            viewPager.adapter = it
            tabLayout.setupWithViewPager(viewPager)
            setHasOptionsMenu(true)
        }
        return view
    }
}