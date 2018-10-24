package app.learn.kotlin.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueResponse (val leagues: List<League>? = null) : Parcelable

