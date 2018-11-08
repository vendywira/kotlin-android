package app.learn.kotlin.feature.favorite

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
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.recycle_swipe_refresh.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FavoriteContract.Presenter>(), FavoriteContract.View {

    @Inject
    internal lateinit var presenter: FavoriteContract.Presenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recycleView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var listOfMatch = mutableListOf<FavoriteEventEntity>()
    private var tagMenu: String? = null

    companion object {
        fun newInstance(tag: String): FavoriteFragment {
            val fragment = FavoriteFragment()
            val bundle = Bundle()
            bundle.putString(Constant.TAG_MENU, tag)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getPresenter(): FavoriteContract.Presenter? = presenter

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_swipe_refresh, container, false)
        recycleView = view.base_recycle_view_id
        swipeRefresh = view.base_swipe_refresh
        recycleView.layoutManager = LinearLayoutManager(ctx)
        favoriteAdapter = FavoriteAdapter(listOfMatch
        ) { position ->
            try {
                ctx.startActivity<MatchDetailActivity>(
                        Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)
            } catch (e: Exception) {
                favoriteChange()
            }
           }
        recycleView.adapter = favoriteAdapter
        favoriteChange()

        swipeRefresh.setOnRefreshListener {
            favoriteChange()
        }

        tagMenu = arguments?.getString(Constant.TAG_MENU) ?: Constant.FAVORITE_MATCHES
        return view
    }

    private fun getMatch() {
        when (tagMenu) {
            Constant.FAVORITE_MATCHES -> {
                presenter.getListEventFavorite()
            }
            Constant.FAVORITE_TEAMS -> {
                presenter.getListEventFavorite()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteChange()
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
