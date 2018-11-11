package app.learn.kotlin.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team (
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strTeamBadge")
        var teamLogoUrl: String? = null,

        @SerializedName("strTeamFanart1")
        var teamBanner: String? = null,

        @SerializedName("strDescriptionEN")
        val teamDescription: String?
) : Parcelable