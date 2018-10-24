package app.learn.kotlin.model.entity

data class FavoriteEvent (
        val id: Int?,
        val eventId: String?,
        val teamHomeName: String?,
        val teamHomeScore: String?,
        val teamAwayName: String?,
        val teamAwayScore: String?,
        val startDate: String?
) {
    constructor() : this(null, null, null, null, null, null, null)
}