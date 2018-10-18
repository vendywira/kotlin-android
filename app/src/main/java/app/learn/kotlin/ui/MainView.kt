package app.learn.kotlin.ui

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import app.learn.kotlin.Activity.DetailClubActivity
import app.learn.kotlin.Activity.MainActivity
import app.learn.kotlin.Activity.MainActivity.Companion.items
import app.learn.kotlin.R
import app.learn.kotlin.adapter.RecyclerViewAdapter
import app.learn.kotlin.model.Constant
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainView : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        verticalLayout{
            lparams(matchParent, matchParent)

            recyclerView {
                id = R.id.club_list_id
                lparams(matchParent, matchParent)
                layoutManager = LinearLayoutManager(context)
                adapter = RecyclerViewAdapter(context, items) {
                    startActivity<DetailClubActivity>(Constant.EXTRA_CONTENT.name to it)
                }
            }
        }
    }
}
