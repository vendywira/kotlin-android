package app.learn.kotlin.model.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamVo (
        var teamId: String? = null,
        var teamName: String? = null,
        var teamLogoUrl: String? = null,
        var teamBanner: String? = null,
        val teamDescription: String?
) : Parcelable {
    constructor() : this(null, null, null, null, null)
}