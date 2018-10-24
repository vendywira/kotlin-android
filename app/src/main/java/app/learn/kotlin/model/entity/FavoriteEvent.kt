package app.learn.kotlin.model.entity

data class FavoriteEvent (
        var id: Int?,
        var eventId: String?,
        var teamHomeName: String?,
        var teamHomeScore: Int?,
        var teamAwayName: String?,
        var teamAwayScore: Int?,
        var startDate: String?
) {
    constructor() : this(null, null, null, null, null, null, null)

    companion object {
            const val TABLE_NAME = "favorite_event"
            const val ID = "id"
            const val EVENT_ID = "event_id"
            const val TEAM_HOME_NAME = "team_home_name"
            const val TEAM_AWAY_NAME = "team_away_name"
            const val TEAM_HOME_SCORE = "team_home_score"
            const val TEAM_AWAY_SCORE = "team_away_score"
            const val START_DATE = "start_date"
    }
}