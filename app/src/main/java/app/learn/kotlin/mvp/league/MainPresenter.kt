package app.learn.kotlin.mvp.league

import app.learn.kotlin.mvp.base.BasePresenter

interface MainPresenter : BasePresenter {

    fun getLaugueList()

    fun getTeamList(leagueName: String)
}