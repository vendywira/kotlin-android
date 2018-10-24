package app.learn.kotlin.model.response

data class MatchModelVO (
        val eventId: String?,
        val startDate: String = "",
        val homeTeamName: String = "",
        val homeTeamScore: Int? = 0,
        val awayTeamName: String = "",
        val awayTeamScore: Int? = 0
)