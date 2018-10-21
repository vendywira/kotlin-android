package app.learn.kotlin.Activity

import app.learn.kotlin.model.LeagueResponse
import app.learn.kotlin.model.TeamResponse
import app.learn.kotlin.mvp.base.BaseView

interface MainView : BaseView {

    fun leagueName() : String

    fun showTeamList(teamResponse: TeamResponse?)

    fun getListLaugue(leagueResponse: LeagueResponse?)
}