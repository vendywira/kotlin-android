package app.learn.kotlin.mvp.event.detail

import app.learn.kotlin.model.Event
import app.learn.kotlin.model.Team
import app.learn.kotlin.mvp.base.BaseView

interface MatchDetailView : BaseView {

    fun getEventId(): String?

    fun setEventDetailModel(event: Event)

    fun setTeamDetailModel(team: Team)
}