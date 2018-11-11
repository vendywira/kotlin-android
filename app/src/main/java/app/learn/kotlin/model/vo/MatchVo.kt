package app.learn.kotlin.model.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MatchVo (
        var eventId: String? = "",
        var dateEvent: Date? = null,
        var time: String?,
        var teamHomeName: String? = "",
        var teamHomeScore: Int?,
        var teamAwayName: String? = "",
        var teamAwayScore: Int?,
        var showReminder: Boolean = false
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null, false)
}