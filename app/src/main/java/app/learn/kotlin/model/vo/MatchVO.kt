package app.learn.kotlin.model.vo

data class MatchVO (
        var eventId: String? = "",
        var strDate: String? = "",
        var homeTeamName: String? = "",
        var homeTeamScore: Int?,
        var awayTeamName: String? = "",
        var awayTeamScore: Int?,
        var showReminder: Boolean = false
) {
    constructor() : this(null, null, null, null, null, null, false)
}