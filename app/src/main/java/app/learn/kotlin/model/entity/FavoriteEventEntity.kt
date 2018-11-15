package app.learn.kotlin.model.entity

data class FavoriteEventEntity (
        var id: Int?,
        var eventId: String?,
        var homeTeamName: String?,
        var homeTeamScore: Int?,
        var awayTeamName: String?,
        var awayTeamScore: Int?,
        var strDate: String?,
        var time: String?
) {
    constructor() : this(null,null, null, null, null, null, null, null)

    companion object {
            const val TABLE_NAME = "favorite_event"
            const val ID = "id"
            const val EVENT_ID = "eventId"
            const val TEAM_HOME_NAME = "homeTeamName"
            const val TEAM_AWAY_NAME = "awayTeamName"
            const val TEAM_HOME_SCORE = "homeTeamScore"
            const val TEAM_AWAY_SCORE = "awayTeamScore"
            const val START_DATE = "strDate"
            const val TIME = "time"
    }
}