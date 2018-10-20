package app.learn.kotlin.base

import android.widget.ProgressBar

open interface BaseView {

    fun getProgressBar() : ProgressBar?

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)
}