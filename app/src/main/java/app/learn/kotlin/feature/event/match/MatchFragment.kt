package app.learn.kotlin.feature.event.match

import android.R
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.feature.base.BaseRecycleView
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.Constant.MATCH_NEXT_MATCH
import app.learn.kotlin.model.Constant.MATCH_PREV_MATCH
import app.learn.kotlin.model.Constant.TAG_MENU
import app.learn.kotlin.model.response.*
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import javax.inject.Inject

class MatchFragment : BaseFragment<MatchPresenter>(), MatchView {

    @Inject
    internal lateinit var presenter: MatchPresenter
    private lateinit var contentUi: BaseRecycleView<MatchFragment>
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private var leagueId: String? = null
    private var tagMenu: String? = null
    private var listOfMatch = mutableListOf<MatchModelVO>()
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
        contentUi = BaseRecycleView()
        val view = contentUi.createView(AnkoContext.create(ctx, this))
        progressBar = contentUi.progressBar
        presenter.getAllLeague()
        matchAdapter = MatchAdapter(listOfMatch
        ) { position -> ctx.startActivity<MatchDetailActivity>(
                Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)}
        contentUi.recycleView.adapter = matchAdapter
        contentUi.swipeRefresh.setOnRefreshListener {
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

    override fun setViewModel(eventResponse: EventResponse?) {
        listOfMatch.clear()
        eventResponses.clear()
        eventResponse?.let { it ->
            eventResponses.addAll(it.events.orEmpty())
            it.events?.forEach {
                listOfMatch.add(MatchModelVO(it.eventId, it.strDate.orEmpty(), it.teamHomeName.orEmpty(),
                        it.teamHomeScore ?: 0, it.teamAwayName.orEmpty(), it.teamAwayScore ?: 0))
            }
        }
        matchAdapter.notifyDataSetChanged()
    }

    override fun setLeagues(leagueResponse: LeagueResponse?) {
        leagueResponse?.let { response ->
            leaguesResponses.addAll(response.leagues.orEmpty())
            response.leagues?.forEach {
                leagues.add(it.name.orEmpty())
            }
        }
        val spAdapter = ArrayAdapter(ctx, R.layout.simple_spinner_item, leagues)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        contentUi.spinner.adapter = spAdapter
        contentUi.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueId = leaguesResponses[position].id
                getMatch()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                leagueId = leaguesResponses[0].id
                getMatch()
            }
        }
    }

    override fun showLoading() {
        contentUi.swipeRefresh.isRefreshing = false
        progressBar?.visible()
    }

    override fun hideLoading() {
        progressBar?.invisible()
    }
}