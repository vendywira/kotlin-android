package app.learn.kotlin.feature.event.match

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
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
import android.widget.Spinner
import app.learn.kotlin.R.layout.fragment_match
import app.learn.kotlin.feature.adapter.EventAdapter
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.helper.toDate
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.Constant.MATCH_NEXT_MATCH
import app.learn.kotlin.model.Constant.MATCH_PREV_MATCH
import app.learn.kotlin.model.Constant.TAG_MENU
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.vo.EventVo
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_match.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.koin.android.ext.android.inject

open class MatchFragment : BaseFragment<MatchContract.Presenter>(), MatchContract.View {

    private val presenter: MatchPresenterImpl by inject()

    private lateinit var contentUi: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var progressBar: LottieAnimationView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private var leagueId: String? = null
    private var tagMenu: String? = null
    private var listOfMatch = mutableListOf<EventVo>()
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

    override fun getPresenter(): MatchContract.Presenter? = presenter

    override fun getProgressBar(): LottieAnimationView? = progressBar

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        presenter.setupView(this)
        val view = LayoutInflater.from(context).inflate(fragment_match, container, false)
        progressBar = view.base_progress_bar_id
        contentUi = view.rv_match
        swipeRefresh = view.base_swipe_refresh
        spinner = view.event_spinner_id

        presenter.getAllLeague()
        Log.d("list of match ", listOfMatch.size.toString())
        contentUi.layoutManager = LinearLayoutManager(ctx)
        eventAdapter = EventAdapter(listOfMatch,
                { position ->
                    ctx.startActivity<MatchDetailActivity>(
                            Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)
                }, { addEventToCalender(eventResponses[it]) })
        contentUi.adapter = eventAdapter
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

    private fun addEventToCalender(event: Event) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.putExtra(CalendarContract.Events.TITLE, event.event)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                ("${event.dateEvent}.${event.time}").toDate().time)
        intent.putExtra(CalendarContract.Events.ALL_DAY, false)
        intent.putExtra(CalendarContract.Events.DESCRIPTION, event.filename)
        startActivity(intent)
    }


    override fun getSelectedLeagueId(): String? = leagueId

    override fun setViewModel(eventResponse: ListResponse<Event>?) {
        listOfMatch.clear()
        eventResponses.clear()
        eventResponse?.let { it ->
            eventResponses.addAll(it.contents.orEmpty())
            it.contents?.forEach {
                val match = mapper.map(it, EventVo::class.java)
                match.showReminder = (tagMenu == MATCH_NEXT_MATCH)
                listOfMatch.add(match)
            }
        }
        Log.d("list of match ", listOfMatch.size.toString())
        eventAdapter.notifyDataSetChanged()
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