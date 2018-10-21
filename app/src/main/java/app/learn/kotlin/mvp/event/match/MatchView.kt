package app.learn.kotlin.mvp.event.match

import app.learn.kotlin.model.EventResponse
import app.learn.kotlin.model.LeagueResponse
import app.learn.kotlin.mvp.base.BaseView

interface MatchView : BaseView {
    fun getSelectedLeagueId(): String?

    fun setLeagues(leagueResponse: LeagueResponse?)

    fun setViewModel(eventResponse: EventResponse?)
}