package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.entity.FavoriteTeamEntity
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.Team

class TeamDetailContract {

    interface Presenter : BasePresenter {

        fun insertTeamToFavorite(favoriteTeamEntity: FavoriteTeamEntity)

        fun isExistFavoriteTeam(teamId: String?)

        fun deleteMatchFromFavorite(teamId: String?)
    }

    interface View : BaseView {

        fun isExistFavoriteTeam(isFavorite: Boolean)

        fun getTeamId(): String?
    }
}