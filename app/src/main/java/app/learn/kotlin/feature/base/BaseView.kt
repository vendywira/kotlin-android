package app.learn.kotlin.feature.base

import com.airbnb.lottie.LottieAnimationView

open interface BaseView{

    fun getProgressBar() : LottieAnimationView?

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)
}