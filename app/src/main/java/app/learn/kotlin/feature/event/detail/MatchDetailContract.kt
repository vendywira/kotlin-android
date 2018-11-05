package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.Team

class MatchDetailContract {

    interface Presenter : BasePresenter {

        fun getDetailEvent()

        fun insertMatchToFavorite(favoriteEventEntity: FavoriteEventEntity)

        fun isExistFavoriteEvent(eventId: String?)

        fun deleteMatchFromFavorite(eventId: String?)
    }

    interface View : BaseView {

        fun isExistFavoriteEvent(isFavorite: Boolean)

        fun getEventId(): String?

        fun setEventDetailModel(event: Event)

        fun setTeamDetailModel(team: Team)
    }
}