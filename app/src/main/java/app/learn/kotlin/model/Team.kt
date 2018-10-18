package app.learn.kotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class Team (
        @SerializedName("idTeam")
        var id: String? = null,

        @SerializedName("strTeam")
        var name: String? = null,

        @SerializedName("strTeamBadge")
        var image: String? = null
) : Parcelable