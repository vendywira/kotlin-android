package app.learn.kotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventResponse (
        @field:SerializedName("events", alternate = ["event"])
        val events: List<Event>? = null
) : Parcelable