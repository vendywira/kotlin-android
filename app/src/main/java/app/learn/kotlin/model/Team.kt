package app.learn.kotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team (
        @SerializedName("idTeam")
        var id: String? = null,

        @SerializedName("strTeam")
        var name: String? = null,

        @SerializedName("strTeamBadge")
        var image: String? = null
) : Parcelable