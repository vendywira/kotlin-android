package app.learn.kotlin.helper

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.modelmapper.ModelMapper

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this.rootView.context).load(url).into(this)
}

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

val mapper: ModelMapper
    get() = ModelMapperUtils.getInstance()

val Context.database: DatabaseUtils
    get() = DatabaseUtils.getInstance(applicationContext)