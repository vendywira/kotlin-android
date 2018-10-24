package app.learn.kotlin.model.entity

data class FavoriteTeam (
    val id: Int,
    val teamId: String,
    val teamName: String,
    val teamLogoUrl: String,
    val teamBanner: String,
    val teamDescription: String
)