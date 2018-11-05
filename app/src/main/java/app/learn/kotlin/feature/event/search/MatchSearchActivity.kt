package app.learn.kotlin.feature.event.search

import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseActivity

class MatchSearchActivity : BaseActivity<MatchSearchContract.Presenter>(), MatchSearchContract.View {

    override fun onInitView() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)

        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?

        searchView?.queryHint = "Search matches"

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }


    override fun getPresenter(): MatchSearchContract.Presenter? = null

    override fun getProgressBar(): ProgressBar? = null

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

}