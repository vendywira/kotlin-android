package app.learn.kotlin.feature.league

import app.learn.kotlin.feature.base.BasePresenter

interface MainPresenter : BasePresenter {

    fun getLaugueList()

    fun getTeamList(leagueName: String)
}