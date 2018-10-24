package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.Team
import app.learn.kotlin.feature.base.BaseView

interface MatchDetailView : BaseView {

    fun getEventId(): String?

    fun setEventDetailModel(event: Event)

    fun setTeamDetailModel(team: Team)
}