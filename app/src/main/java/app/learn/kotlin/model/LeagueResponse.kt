package app.learn.kotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueResponse (val leagues: List<League>? = null) : Parcelable