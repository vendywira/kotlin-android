package app.learn.kotlin.feature.search.event

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.learn.kotlin.R
import app.learn.kotlin.feature.adapter.EventAdapter
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.vo.EventVo
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.koin.android.ext.android.inject

class SearchEventFragment : BaseFragment<SearchEventContract.Presenter>(), SearchEventContract.View {

    private val presenter: SearchEventPresenterImpl by inject()

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var progressBar: LottieAnimationView
    private var listOfMatch = mutableListOf<EventVo>()
    private var eventResponses = mutableListOf<Event>()

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        presenter.setupView(this)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_search_match, container, false)
        recyclerView = view.base_recycle_view_id
        progressBar = view.base_progress_bar_id
        progressBar.invisible()

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        eventAdapter = EventAdapter(listOfMatch,
                { position ->
                    ctx.startActivity<MatchDetailActivity>(
                            Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)
                }, {})
        recyclerView.adapter = eventAdapter
        return view
    }

    override fun search(query: String) {
        presenter.searchMatches(query)
    }

    override fun setViewModel(data: ListResponse<Event>) {
        listOfMatch.clear()
        eventResponses.clear()
        data.let { it ->
            it.contents?.filter { i ->
                i.teamAwayName != null && i.teamHomeName != null
            }?.forEach {
                val match = mapper.map(it, EventVo::class.java)
                listOfMatch.add(match)
            }
        }
        Log.d("list of match ", listOfMatch.size.toString())
        eventAdapter.notifyDataSetChanged()
    }

    override fun getPresenter(): SearchEventContract.Presenter? = presenter

    override fun getProgressBar(): LottieAnimationView? = progressBar
}