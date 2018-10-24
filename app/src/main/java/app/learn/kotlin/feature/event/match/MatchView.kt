package app.learn.kotlin.feature.event.match

import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.EventResponse
import app.learn.kotlin.model.response.LeagueResponse

interface MatchView : BaseView {
    fun getSelectedLeagueId(): String?

    fun setLeagues(leagueResponse: LeagueResponse?)

    fun setViewModel(eventResponse: EventResponse?)
}