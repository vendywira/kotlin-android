package app.learn.kotlin.model.entity

data class FavoriteTeamEntity (
    var id: Int?,
    var teamId: String?,
    var teamName: String?,
    var teamLogoUrl: String?,
    var teamBanner: String?,
    var teamDescription: String?
) {

    constructor() : this(null, null, null, null, null, null)
    companion object {
        const val TABLE_NAME = "favorite_team"
        const val ID = "id"
        const val TEAM_ID = "teamId"
        const val TEAM_NAME = "teamName"
        const val TEAM_LOGO_URL = "teamLogoUrl"
        const val TEAM_DESCRIPTION = "teamDescription"
        const val TEAM_BANNER = "teamBanner"
    }
}