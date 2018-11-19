package app.learn.kotlin.feature.team.list

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team

class TeamListContract {

    interface Presenter : BasePresenter {

        fun getLeagueList()

        fun getTeamList(leagueName: String)
    }

    interface View : BaseView {

        fun leagueName() : String

        fun showTeamList(teamResponse: ListResponse<Team>?)

        fun getListLaugue(leagueResponse: ListResponse<League>?)
    }
}