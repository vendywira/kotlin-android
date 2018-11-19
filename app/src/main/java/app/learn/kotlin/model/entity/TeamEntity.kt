package app.learn.kotlin.model.entity

data class TeamEntity (
    var id: Int?,
    var teamId: String?,
    var teamName: String?,
    var teamLogoUrl: String?,
    var teamBanner: String?,
    var teamStadiumName: String?,
    var teamFormedYear: String?,
    var teamDescription: String?
) {

    constructor() : this(null, null, null, null, null, null, null, null)
    companion object {
        const val TABLE_NAME = "team"
        const val ID = "id"
        const val TEAM_ID = "teamId"
        const val TEAM_NAME = "teamName"
        const val TEAM_LOGO_URL = "teamLogoUrl"
        const val TEAM_DESCRIPTION = "teamDescription"
        const val TEAM_BANNER = "teamBanner"
        const val TEAM_STADIUM_NAME = "teamStadiumName"
        const val TEAM_FORMED_YEAR = "teamFormedYear"
    }
}