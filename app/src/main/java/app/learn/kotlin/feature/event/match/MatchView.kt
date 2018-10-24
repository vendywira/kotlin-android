package app.learn.kotlin.feature.event.match

import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse

interface MatchView : BaseView {
    fun getSelectedLeagueId(): String?

    fun setLeagues(leagueResponse: ListResponse<League>?)

    fun setViewModel(eventResponse: ListResponse<Event>?)
}