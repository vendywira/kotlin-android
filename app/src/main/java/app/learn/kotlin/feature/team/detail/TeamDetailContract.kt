package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.entity.TeamEntity

class TeamDetailContract {

    interface Presenter : BasePresenter {

        fun insertTeamToFavorite(teamEntity: TeamEntity)

        fun isExistFavoriteTeam(teamId: String?)

        fun deleteMatchFromFavorite(teamId: String?)
    }

    interface View : BaseView {

        fun isExistFavoriteTeam(isFavorite: Boolean)

        fun getTeamId(): String?
    }
}