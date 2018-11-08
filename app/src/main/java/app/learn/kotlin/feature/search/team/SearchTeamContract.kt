package app.learn.kotlin.feature.search.team

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team

class SearchTeamContract {

    interface View : BaseView {
        fun setViewModel(data: ListResponse<Team>)

        fun search(query: String)
    }

    interface Presenter : BasePresenter {
        fun searchTeams(query: String)
    }
}