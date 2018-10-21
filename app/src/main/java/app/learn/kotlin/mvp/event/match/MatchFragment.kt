package app.learn.kotlin.mvp.event.match

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
import app.learn.kotlin.model.*
import app.learn.kotlin.model.Constant.MATCH_NEXT_MATCH
import app.learn.kotlin.model.Constant.MATCH_PREV_MATCH
import app.learn.kotlin.mvp.base.BaseFragment
import app.learn.kotlin.mvp.base.BaseRecycleView
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
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
        const val TAG_MENU_PREV_MATCH = "TAG_MENU_PREV_MATCH"
        const val TAG_MENU_NEXT_MATCH = "TAG_MENU_NEXT_MATCH"
        const val TAG_MENU_FAVORITE = "TAG_MENU_FAVORITE"
        const val TAG_MENU = "menu"
        const val TAG_EVENT_ID = "event_id"

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
        leagueId = "4328"
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
        matchAdapter = MatchAdapter(listOfMatch, {position -> toast(listOfMatch[position].toString())})
        contentUi.recycleView.adapter = matchAdapter
        contentUi.swipeRefreshLayout.setOnRefreshListener {
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
                listOfMatch.add(MatchModelVO(it.idEvent, it.strDate.orEmpty(), it.strHomeTeam.orEmpty(),
                        it.intHomeScore.orEmpty(), it.strAwayTeam.orEmpty(), it.intAwayScore.orEmpty()))
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

    /*override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)

        val item = menu?.findItem(R.id.search)
        val sv = SearchView((activity as MainActivity).supportActionBar?.themedContext)
        item?.setShowAsAction(MenuItemImpl.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItemImpl.SHOW_AS_ACTION_IF_ROOM)
        item?.actionView = sv
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterData(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterData(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun filterData(keyword: String) {
        if (keyword.isNotBlank())
            presenter.searchMatch(keyword)
        else
            getMatch()
    }*/

}