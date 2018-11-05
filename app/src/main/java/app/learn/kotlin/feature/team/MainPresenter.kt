package app.learn.kotlin.feature.team

import app.learn.kotlin.feature.base.BasePresenter

interface MainPresenter : BasePresenter {

    fun getLeagueList()

    fun getTeamList(leagueName: String)
}