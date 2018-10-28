package app.learn.kotlin.feature.league

import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team

interface MainView : BaseView {

    fun leagueName() : String

    fun showTeamList(teamResponse: ListResponse<Team>?)

    fun getListLaugue(leagueResponse: ListResponse<League>?)
}