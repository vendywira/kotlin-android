package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.Team

interface MatchDetailView : BaseView {

    fun isExistFavoriteEvent(isFavorite: Boolean)

    fun getEventId(): String?

    fun setEventDetailModel(event: Event)

    fun setTeamDetailModel(team: Team)
}