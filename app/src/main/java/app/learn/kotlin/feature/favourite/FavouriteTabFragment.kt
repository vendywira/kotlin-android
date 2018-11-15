package app.learn.kotlin.feature.favourite

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BasePagerAdapter
import app.learn.kotlin.feature.favourite.event.FavouriteEventFragment
import app.learn.kotlin.feature.favourite.team.FavouriteTeamFragment

class FavouriteTabFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: BasePagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tabs, container, false)
        tabLayout = view.findViewById(R.id.tabs)
        viewPager = view.findViewById(R.id.viewpager)

        viewPagerAdapter = childFragmentManager.let {
            BasePagerAdapter(it)
        }
        viewPagerAdapter.let {
            it.addFragment(getString(R.string.tab_title_matches), FavouriteEventFragment())
            it.addFragment(getString(R.string.tab_title_teams), FavouriteTeamFragment())
            viewPager.adapter = it
            tabLayout.setupWithViewPager(viewPager)
            setHasOptionsMenu(true)
        }
        return view
    }
}