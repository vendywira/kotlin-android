package app.learn.kotlin.Activity

import app.learn.kotlin.mvp.base.BasePresenter

interface MainPresenter : BasePresenter {

    fun getLaugueList()

    fun getTeamList(leagueName: String)
}