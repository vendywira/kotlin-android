package app.learn.kotlin.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListResponse<out T : Parcelable> (
        @field:SerializedName("contents", alternate = ["events","event","teams","leagues"])
        val contents: List<T>? = null
) : Parcelable {
    companion object {
        inline operator fun <reified T: Parcelable> invoke(): T = T::class.java.newInstance()
    }
}