package app.learn.kotlin.feature.base

interface BasePresenter {

    fun <T : BaseView> setupView(view: T)

    fun onAttach()

    fun onDetach()
}