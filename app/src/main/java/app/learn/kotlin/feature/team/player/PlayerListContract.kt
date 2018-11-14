package app.learn.kotlin.feature.team.player

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Player

class PlayerListContract {

    interface View : BaseView {

        fun setPlayerViewModel(players: ListResponse<Player>)
    }

    interface Presenter : BasePresenter {
        fun getListTeamPlayer(teamId: String)
    }
}