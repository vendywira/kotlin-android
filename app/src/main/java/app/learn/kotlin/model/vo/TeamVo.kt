package app.learn.kotlin.model.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamVo (
        var teamId: String? = null,
        var teamName: String? = null,
        var teamLogoUrl: String? = null,
        var teamBanner: String? = null,
        var teamStadiumName: String? = null,
        var teamFormedYear: String? = null,
        var teamDescription: String? = null
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null)
}