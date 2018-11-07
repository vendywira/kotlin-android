package app.learn.kotlin.feature.search

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team

class SearchContract {

    companion object {
        const val SEARCH_TEAM = "SEARCH_TEAM"
        const val SEARCH_EVENT = "SEARCH_EVENT"
    }

    interface Presenter : BasePresenter {

        fun searchTeams(query: String)

        fun searchEvent(query: String)
    }

    interface View : BaseView {

        fun addTeamsFound(teams: ListResponse<Team>)

        fun addEventsFound(events: ListResponse<Event>)
    }
}