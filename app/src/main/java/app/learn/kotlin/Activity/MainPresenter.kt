package app.learn.kotlin.Activity

import app.learn.kotlin.base.BasePresenterSevice

interface MainPresenter : BasePresenterSevice {

    fun getLaugueList()

    fun getTeamList(leagueName: String)
}