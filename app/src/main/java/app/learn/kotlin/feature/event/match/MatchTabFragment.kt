package app.learn.kotlin.feature.event.match

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import app.learn.kotlin.R
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.Constant.MATCH_PREV_MATCH

class MatchTabFragment : Fragment() {

    private lateinit var tabLayout:TabLayout
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
            it.addFragment(getString(R.string.tab_title_next), MatchFragment.newInstance(Constant.MATCH_NEXT_MATCH))
            it.addFragment(getString(R.string.tab_title_prev), MatchFragment.newInstance(MATCH_PREV_MATCH))
            viewPager.adapter = it
            tabLayout.setupWithViewPager(viewPager)
            setHasOptionsMenu(true)
        }
        return view
    }
}