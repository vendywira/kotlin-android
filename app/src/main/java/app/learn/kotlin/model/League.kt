package app.learn.kotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League (
        @SerializedName("idLeague")
        var id: String? = null,

        @SerializedName("strLeague")
        var name: String? = null,

        @SerializedName("strLeagueAlternate")
        var alternativeName: String? = null,

        @SerializedName("strSport")
        var sport: String? = null
) : Parcelable