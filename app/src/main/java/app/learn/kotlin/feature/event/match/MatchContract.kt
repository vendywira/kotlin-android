package app.learn.kotlin.feature.event.match

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse

class MatchContract {

    interface Presenter : BasePresenter {

        fun getAllLeague()

        fun getLastMatch()

        fun getNextMatch()
    }

    interface View : BaseView {
        fun getSelectedLeagueId(): String?

        fun setLeagues(leagueResponse: ListResponse<League>?)

        fun setViewModel(eventResponse: ListResponse<Event>?)
    }
}