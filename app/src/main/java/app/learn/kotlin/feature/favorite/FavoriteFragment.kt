package app.learn.kotlin.feature.favorite

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
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEventEntity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FavoriteContract.presenter>(), FavoriteContract.view {

    @Inject
    internal lateinit var presenter: FavoriteContract.presenter
    private lateinit var progressBar: ProgressBar
    private lateinit var contentUi: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var listOfMatch = mutableListOf<FavoriteEventEntity>()

    override fun getPresenter(): FavoriteContract.presenter? = presenter

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.base_recycle_view, container, false)
        contentUi = view.base_recycle_view_id
        swipeRefresh = view.base_swipe_refresh
        contentUi.layoutManager = LinearLayoutManager(ctx)
        favoriteAdapter = FavoriteAdapter(listOfMatch
        ) { position ->
            try {
                ctx.startActivity<MatchDetailActivity>(
                        Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)
            } catch (e: Exception) {
                favoriteChange()
            }
           }
        contentUi.adapter = favoriteAdapter
        favoriteChange()

        swipeRefresh.setOnRefreshListener {
            favoriteChange()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        favoriteChange()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setViewModel(favorite: FavoriteEventEntity) {
        if (!listOfMatch.contains(favorite)) {
            listOfMatch.add(favorite)
        }
    }

    override fun notifyDataChange() {
        favoriteAdapter.notifyDataSetChanged()
    }

    private fun favoriteChange() {
        listOfMatch.clear()
        presenter.getListEventFavorite()
    }

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun showLoading() {
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }
}
