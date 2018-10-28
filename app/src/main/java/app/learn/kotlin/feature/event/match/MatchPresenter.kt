package app.learn.kotlin.feature.event.match

import app.learn.kotlin.feature.base.BasePresenter

interface MatchPresenter : BasePresenter {

    fun getAllLeague()

    fun getLastMatch()

    fun getNextMatch()
}