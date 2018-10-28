package app.learn.kotlin.feature.event.match

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseTabLayout
import app.learn.kotlin.model.Constant.MATCH_NEXT_MATCH
import app.learn.kotlin.model.Constant.MATCH_PREV_MATCH
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class MatchTabFragment : Fragment() {

    private lateinit var content: BaseTabLayout<MatchTabFragment>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        content = BaseTabLayout()
        val view = content.createView(AnkoContext.create(ctx, this))
        val viewPagerAdapter = childFragmentManager.let {
            MatchViewPagerAdapter(it)
        }
        viewPagerAdapter.let {
            it.addFragment(getString(R.string.tab_title_prev), MatchFragment.newInstance(MATCH_PREV_MATCH))
            it.addFragment(getString(R.string.tab_title_next), MatchFragment.newInstance(MATCH_NEXT_MATCH))
            content.viewPager.adapter = it
            content.tabLayout.setupWithViewPager(content.viewPager)
        }
        return view
    }
}