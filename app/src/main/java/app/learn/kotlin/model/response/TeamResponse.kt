package app.learn.kotlin.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamResponse (val teams: List<Team>? = null) : Parcelable