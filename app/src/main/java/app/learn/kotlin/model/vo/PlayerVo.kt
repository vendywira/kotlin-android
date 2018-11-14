package app.learn.kotlin.model.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerVo (
        var playerImageUrl: String? = null,
        var thumbImageUrl: String? = null,
        var banner: String? = null,
        var banner1: String? = null,
        var name: String? = null,
        var position: String? = null,
        var weight: String? = "0",
        var height: String? = "0",
        var description: String? = null ) : Parcelable {
    constructor() : this( null,null, null, null, null, null, null, null, null)
}