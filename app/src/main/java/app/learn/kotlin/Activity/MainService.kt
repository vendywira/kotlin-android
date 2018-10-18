package app.learn.kotlin.Activity

import app.learn.kotlin.model.Team

interface MainService {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}