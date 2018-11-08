package app.learn.kotlin.feature.search.event

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.feature.event.match.MatchAdapter
import app.learn.kotlin.helper.gone
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.toSimpleString
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.vo.MatchVO
import kotlinx.android.synthetic.main.abc_search_view.*
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.recycle_swipe_refresh.view.*
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import java.util.Locale.filter
import javax.inject.Inject

class SearchEventFragment : BaseFragment<SearchEventContract.Presenter>(), SearchEventContract.View {

    @Inject
    internal lateinit var presenter: SearchEventContract.Presenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var listOfMatch = mutableListOf<MatchVO>()
    private var eventResponses = mutableListOf<Event>()

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_search_match, container, false)
        recyclerView = view.base_recycle_view_id
        progressBar = view.base_progress_bar_id
        progressBar.invisible()

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        matchAdapter = MatchAdapter(listOfMatch,
                { position -> ctx.startActivity<MatchDetailActivity>(
                Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)}, {})
        recyclerView.adapter = matchAdapter
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
                listOfMatch.add(MatchVO(
                        it.eventId,
                        toSimpleString(it.strDate.orEmpty()),
                        it.teamHomeName.orEmpty(),
                        it.teamHomeScore,
                        it.teamAwayName.orEmpty(),
                        it.teamAwayScore))
            }
        }
        Log.d("list of match ", listOfMatch.size.toString())
        matchAdapter.notifyDataSetChanged()
    }

    override fun getPresenter(): SearchEventContract.Presenter? = presenter

    override fun getProgressBar(): ProgressBar? = progressBar
}