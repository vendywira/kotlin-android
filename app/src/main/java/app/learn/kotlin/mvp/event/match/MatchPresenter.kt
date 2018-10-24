package app.learn.kotlin.mvp.event.match

import app.learn.kotlin.mvp.base.BasePresenter

interface MatchPresenter : BasePresenter {

    fun getAllLeague()

    fun getLastMatch()

    fun getNextMatch()
}