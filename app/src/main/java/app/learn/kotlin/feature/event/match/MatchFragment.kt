package app.learn.kotlin.feature.event.match

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.feature.event.match.MatchAdapter
import app.learn.kotlin.feature.event.match.MatchPresenter
import app.learn.kotlin.feature.event.match.MatchView
import android.widget.Spinner
import app.learn.kotlin.R.layout.fragment_match
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.toSimpleString
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.Constant.MATCH_NEXT_MATCH
import app.learn.kotlin.model.Constant.MATCH_PREV_MATCH
import app.learn.kotlin.model.Constant.TAG_MENU
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.vo.MatchVO
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.base_recycle_view.view.*
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import javax.inject.Inject

class MatchFragment : BaseFragment<MatchPresenter>(), MatchView {

    @Inject
    internal lateinit var presenter: MatchPresenter
    private lateinit var contentUi: RecyclerView
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private var leagueId: String? = null
    private var tagMenu: String? = null
    private var listOfMatch = mutableListOf<MatchVO>()
    private var leagues: MutableList<String> = mutableListOf()
    private var eventResponses = mutableListOf<Event>()
    private var leaguesResponses: MutableList<League> = mutableListOf()

    companion object {
        fun newInstance(tag: String): MatchFragment {
            val fragment = MatchFragment()
            val bundle = Bundle()
            bundle.putString(TAG_MENU, tag)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tagMenu = arguments?.getString(TAG_MENU) ?: MATCH_PREV_MATCH
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun getPresenter(): MatchPresenter? = presenter

    override fun getProgressBar(): ProgressBar? = progressBar


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = LayoutInflater.from(context).inflate(fragment_match, container, false)
        progressBar = view.base_progress_bar_id
        contentUi = view.base_recycle_view_id
        swipeRefresh = view.base_swipe_refresh
        spinner = view.base_spinner_id

        presenter.getAllLeague()
        Log.d("list of match ", listOfMatch.size.toString())
        contentUi.layoutManager = LinearLayoutManager(ctx)
        matchAdapter = MatchAdapter(listOfMatch
        ) { position -> ctx.startActivity<MatchDetailActivity>(
                Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)}
        contentUi.adapter = matchAdapter
        swipeRefresh.setOnRefreshListener {
            getMatch()
        }
        return view
    }

    private fun getMatch() {
        when (tagMenu) {
            MATCH_PREV_MATCH -> {
                presenter.getLastMatch()
            }
            MATCH_NEXT_MATCH -> {
                presenter.getNextMatch()
            }
        }
    }

    override fun getSelectedLeagueId(): String? = leagueId

    override fun setViewModel(eventResponse: ListResponse<Event>?) {
        listOfMatch.clear()
        eventResponses.clear()
        eventResponse?.let { it ->
            eventResponses.addAll(it.contents.orEmpty())
            it.contents?.forEach {
                listOfMatch.add(MatchVO(it.eventId, toSimpleString(it.strDate.orEmpty()), it.teamHomeName.orEmpty(),
                        it.teamHomeScore ?: 0, it.teamAwayName.orEmpty(), it.teamAwayScore ?: 0))
            }
        }
        Log.d("list of match ", listOfMatch.size.toString())
        matchAdapter.notifyDataSetChanged()
    }

    override fun setLeagues(leagueResponse: ListResponse<League>?) {
        leagueResponse?.let { response ->
            leaguesResponses.addAll(response.contents.orEmpty())
            response.contents?.forEach {
                leagues.add(it.name.orEmpty())
            }
        }
        val spAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_item, leagues)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueId = leaguesResponses[position].id
                getMatch()
            }

        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = false
        progressBar.visible()
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        progressBar.invisible()
    }
}