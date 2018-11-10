package app.learn.kotlin.feature.favorite.event

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.feature.HomeActivity
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.feature.favorite.team.FavoriteTeamAdapter
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteTeamEntity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.recycle_swipe_refresh.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import javax.inject.Inject

class FavoriteTeamFragment : BaseFragment<FavoriteTeamContract.Presenter>(), FavoriteTeamContract.View {

    @Inject
    internal lateinit var presenter: FavoriteTeamContract.Presenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recycleView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var favoriteTeamAdapter: FavoriteTeamAdapter
    private var listOfTeam = mutableListOf<FavoriteTeamEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeActivity.menu?.getItem(HomeActivity.MENU_SEARCH)?.invisible()
    }

    override fun getPresenter(): FavoriteTeamContract.Presenter? = presenter

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_swipe_refresh, container, false)
        recycleView = view.base_recycle_view_id
        swipeRefresh = view.base_swipe_refresh
        recycleView.layoutManager = LinearLayoutManager(ctx)
        favoriteTeamAdapter = FavoriteTeamAdapter(listOfTeam
        ) { position ->
            try {
                ctx.startActivity<MatchDetailActivity>(
                        Constant.MATCH_EVENT_ID to listOfTeam[position].teamId)
            } catch (e: Exception) {
                loadFavorite()
            }
        }
        recycleView.adapter = favoriteTeamAdapter
        loadFavorite()

        swipeRefresh.setOnRefreshListener {
            loadFavorite()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        loadFavorite()
    }

    override fun setViewModel(data: FavoriteTeamEntity) {
        if (!listOfTeam.contains(data)) {
            listOfTeam.add(data)
        }
    }

    override fun notifyDataChange() {
        favoriteTeamAdapter.notifyDataSetChanged()
    }

    private fun loadFavorite() {
        listOfTeam.clear()
        presenter.getListFavorite()
    }

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun showLoading() {
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }
}