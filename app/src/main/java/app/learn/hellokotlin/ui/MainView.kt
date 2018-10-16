package app.learn.hellokotlin.ui

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import app.learn.hellokotlin.Activity.DetailClubActivity
import app.learn.hellokotlin.Activity.MainActivity
import app.learn.hellokotlin.Activity.MainActivity.Companion.items
import app.learn.hellokotlin.R
import app.learn.hellokotlin.adapter.RecyclerViewAdapter
import app.learn.hellokotlin.model.Constant
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
                    Log.d("test", it.toString())
                    startActivity<DetailClubActivity>(Constant.EXTRA_CONTENT.name to it)
                }
            }
        }
    }
}
