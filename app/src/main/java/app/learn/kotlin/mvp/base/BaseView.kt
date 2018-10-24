package app.learn.kotlin.mvp.base

import android.widget.ProgressBar

open interface BaseView {

    fun getProgressBar() : ProgressBar?

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)
}