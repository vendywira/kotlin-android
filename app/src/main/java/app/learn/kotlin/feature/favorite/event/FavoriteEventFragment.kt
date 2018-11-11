package app.learn.kotlin.feature.favorite.event

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
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEventEntity
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.android.synthetic.main.recycle_swipe_refresh.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import javax.inject.Inject

class FavoriteEventFragment : BaseFragment<FavoriteEventContract.Presenter>(), FavoriteEventContract.View {

    @Inject
    internal lateinit var presenter: FavoriteEventContract.Presenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recycleView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var favoriteEventAdapter: FavoriteEventAdapter
    private var listOfMatch = mutableListOf<FavoriteEventEntity>()

    override fun getPresenter(): FavoriteEventContract.Presenter? = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeActivity.menu?.getItem(HomeActivity.MENU_SEARCH)?.invisible()
    }

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_favorite, container, false)
        recycleView = view.base_recycle_view_id
        swipeRefresh = view.base_swipe_refresh
        progressBar = view.base_progress_bar_id
        recycleView.layoutManager = LinearLayoutManager(ctx)
        favoriteEventAdapter = FavoriteEventAdapter(listOfMatch
        ) { position ->
            try {
                ctx.startActivity<MatchDetailActivity>(
                        Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)
            } catch (e: Exception) {
                loadFavorite()
            }
        }
        recycleView.adapter = favoriteEventAdapter
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

    override fun setViewModel(data: FavoriteEventEntity) {
        if (data.eventId != null && !listOfMatch.contains(data)) {
            listOfMatch.add(data)
        }
    }

    override fun notifyDataChange() {
        favoriteEventAdapter.notifyDataSetChanged()
    }

    private fun loadFavorite() {
        listOfMatch.clear()
        presenter.getListFavorite()
    }

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        super.hideLoading()
    }
}
