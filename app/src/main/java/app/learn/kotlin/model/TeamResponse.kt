package app.learn.kotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamResponse (val teams: List<Team>) : Parcelable