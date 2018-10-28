package app.learn.kotlin.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Parcel
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.modelmapper.ModelMapper
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun MenuItem.visible() {
    isVisible = true
}

fun MenuItem.invisible() {
    isVisible = false
}

fun MenuItem.enable() {
    isEnabled = true
}

fun MenuItem.disable() {
    isEnabled = false
}

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this.rootView.context).load(url).into(this)
}

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

val mapper: ModelMapper
    get() = ModelMapperUtils.configuration()

val objectMapper = ObjectMapper()

fun convertObjectToPair(ob: Any): ContentValues {
    val parcel: Parcel = Parcel.obtain()
    parcel.writeMap(objectMapper.convertValue(ob, Map::class.java))
    parcel.setDataPosition(0);
    return ContentValues.CREATOR.createFromParcel(parcel)
}

fun convertMapToContentValues(map: Map<*, *>): ContentValues {
    val parcel: Parcel = Parcel.obtain()
    parcel.writeMap(map)
    parcel.setDataPosition(0);
    return ContentValues.CREATOR.createFromParcel(parcel)
}

val Context.database: DatabaseUtils
    get() = DatabaseUtils.getInstance(applicationContext)

@SuppressLint("SimpleDateFormat")
fun toSimpleString(strDate: String?): String? {
    return try {
        val localeId = Locale("in", "ID")
        SimpleDateFormat("dd/MM/yy").parse(strDate)
                .let(SimpleDateFormat("EEE, d MMM yyyy", localeId)::format)
    } catch (e: Exception) {
        strDate
    }
}