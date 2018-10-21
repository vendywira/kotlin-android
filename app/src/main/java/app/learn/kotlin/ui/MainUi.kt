package app.learn.kotlin.ui

import android.view.View
import android.widget.LinearLayout
import app.learn.kotlin.Activity.MainActivity
import app.learn.kotlin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainUi : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner {
                id = R.id.base_spinner_id
            }
            swipeRefreshLayout {
                id = R.id.base_swipe_refresh
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    recyclerView {
                        id = R.id.club_list_id
                        lparams (width = matchParent, height = wrapContent)
                    }

                    progressBar {
                        id = R.id.base_progress_bar_id
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }
}
