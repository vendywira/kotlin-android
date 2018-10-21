package app.learn.kotlin.mvp.base

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import app.learn.kotlin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class BaseRecycleView<T> : AnkoComponent<T> {
    lateinit var spinner: Spinner
    lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var recycleView: RecyclerView
    lateinit var progressBar: ProgressBar

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(8)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.base_spinner_id
                layoutMode = Spinner.MODE_DIALOG
            }.lparams {
                width = matchParent
                height = wrapContent
                margin = dip(16)
            }
            swipeRefresh = swipeRefreshLayout {
                id = R.id.base_swipe_refresh
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    recycleView = recyclerView {
                        id = R.id.base_recycle_view_id
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams(width = matchParent, height = wrapContent)

                    progressBar = progressBar {
                        id = R.id.base_progress_bar_id
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }
}
