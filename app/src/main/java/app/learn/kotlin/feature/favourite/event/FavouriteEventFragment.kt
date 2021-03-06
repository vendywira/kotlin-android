package app.learn.kotlin.feature.favourite.event

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.learn.kotlin.R
import app.learn.kotlin.feature.HomeActivity
import app.learn.kotlin.feature.adapter.EventAdapter
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.EventEntity
import app.learn.kotlin.model.vo.EventVo
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_favourite.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.koin.android.ext.android.inject
import javax.inject.Inject

class FavouriteEventFragment : BaseFragment<FavouriteEventContract.Presenter>(), FavouriteEventContract.View {

    private val presenter: FavouriteEventPresenterImpl by inject()
    private lateinit var progressBar: LottieAnimationView
    private lateinit var recycleView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var favoriteEventAdapter: EventAdapter
    private var listOfMatch = mutableListOf<EventVo>()

    override fun getPresenter(): FavouriteEventContract.Presenter? = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeActivity.menu?.getItem(HomeActivity.MENU_SEARCH)?.invisible()
    }

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        presenter.setupView(this)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_favourite, container, false)
        recycleView = view.rv_favorite
        swipeRefresh = view.base_swipe_refresh
        progressBar = view.base_progress_bar_id
        recycleView.layoutManager = LinearLayoutManager(ctx)
        favoriteEventAdapter = EventAdapter(listOfMatch, { position ->
            try {
                ctx.startActivity<MatchDetailActivity>(
                        Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)
            } catch (e: Exception) {
                loadFavorite()
            }
        }, {})
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

    override fun setViewModel(data: EventEntity) {
        val eventVo = mapper.map(data, EventVo::class.java)
        if (eventVo.eventId != null && !listOfMatch.contains(eventVo)) {
            listOfMatch.add(eventVo)
        }
    }

    override fun notifyDataChange() {
        favoriteEventAdapter.notifyDataSetChanged()
    }

    private fun loadFavorite() {
        listOfMatch.clear()
        presenter.getListFavorite()
    }

    override fun getProgressBar(): LottieAnimationView? = progressBar

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        super.hideLoading()
    }
}
