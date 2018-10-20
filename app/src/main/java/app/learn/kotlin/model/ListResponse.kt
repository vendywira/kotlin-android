package app.learn.kotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListResponse<out T : Parcelable> (val leagues: List<T>) : Parcelable