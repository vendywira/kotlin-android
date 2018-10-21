package app.learn.kotlin.mvp.base

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import app.learn.kotlin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.viewPager

class BaseTabLayout<T> : AnkoComponent<T> {
    internal lateinit var tabLayout: TabLayout
    internal lateinit var viewPager: ViewPager

    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        verticalLayout {
            tabLayout = tabLayout {
                lparams(width = matchParent, height = wrapContent)
                tabMode = TabLayout.MODE_FIXED
            }
            viewPager = viewPager {
                id = R.id.base_view_pager
            }.lparams {
                width = matchParent
                height = matchParent
            }
        }
    }
}

