package app.learn.kotlin.model

data class MatchModelVO (
        val eventId: String?,
        val startDate: String = "",
        val homeTeamName: String = "",
        val homeTeamScore: String = "",
        val awayTeamName: String = "",
        val awayTeamScore: String = ""
)