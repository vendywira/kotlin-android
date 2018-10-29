package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.model.entity.FavoriteEventEntity

interface MatchDetailPresenter : BasePresenter {

    fun getDetailEvent()

    fun insertMatchToFavorite(favoriteEventEntity: FavoriteEventEntity)

    fun isExistFavoriteEvent(eventId: String?)

    fun deleteMatchFromFavorite(eventId: String?)
}