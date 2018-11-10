package app.learn.kotlin.model.entity

data class FavoriteTeamEntity (
    val id: Int?,
    val teamId: String?,
    val teamName: String?,
    val teamLogoUrl: String?,
    val teamBanner: String?,
    val teamDescription: String?
) {

    constructor() : this(null, null, null, null, null, null)
    companion object {
        const val TABLE_NAME = "favorite_team"
        const val ID = "id"
        const val TEAM_ID = "team_id"
        const val TEAM_NAME = "team_name"
        const val TEAM_LOGO_URL = "team_logo_url"
        const val TEAM_DESCRIPTION = "team_description"
        const val TEAM_BANNER = "team_banner"
    }
}