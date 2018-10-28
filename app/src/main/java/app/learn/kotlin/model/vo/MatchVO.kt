package app.learn.kotlin.model.vo

data class MatchVO (
        var eventId: String? = "",
        var strDate: String? = "",
        var homeTeamName: String? = "",
        var homeTeamScore: Int? = 0,
        var awayTeamName: String? = "",
        var awayTeamScore: Int? = 0
) {
    constructor() : this(null, null, null, null, null, null)
}