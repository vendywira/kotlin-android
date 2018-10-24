package app.learn.kotlin.feature.league

import app.learn.kotlin.model.response.LeagueResponse
import app.learn.kotlin.model.response.TeamResponse
import app.learn.kotlin.feature.base.BaseView

interface MainView : BaseView {

    fun leagueName() : String

    fun showTeamList(teamResponse: TeamResponse?)

    fun getListLaugue(leagueResponse: LeagueResponse?)
}