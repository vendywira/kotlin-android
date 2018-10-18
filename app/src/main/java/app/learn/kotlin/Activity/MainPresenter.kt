package app.learn.kotlin.Activity

import app.learn.kotlin.model.TeamResponse
import app.learn.kotlin.network.ApiRepository
import app.learn.kotlin.network.TheSportDBApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter (
        private val view: MainService,
        private val apiRepository: ApiRepository,
        private val gson: Gson) {

    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(league)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}