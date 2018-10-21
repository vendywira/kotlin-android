package app.learn.kotlin.mvp.event.match

import app.learn.kotlin.model.ConstantEnum
import app.learn.kotlin.model.EventResponse
import app.learn.kotlin.mvp.base.BasePresenterImpl
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MatchPresenterImpl@Inject constructor (
        private val view: MatchView,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), MatchPresenter {

    override fun getLastMatch() {
        super.addDisposable(apiService.getLastMatchByLeagueId(view.getSelectedLeagueId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(ConstantEnum.FAILED_GET_DATA.name) }
                .onErrorReturn { EventResponse() }
                .subscribe {
                    view.setViewModel(it)
                })
    }

    override fun getNextMatch() {
        super.addDisposable(apiService.getNextMatchByLeagueId(view.getSelectedLeagueId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(ConstantEnum.FAILED_GET_DATA.name) }
                .onErrorReturn { EventResponse() }
                .subscribe {
                    view.setViewModel(it)
                })
    }

    override fun getAllLeague() {
        super.addDisposable(apiService.getAllLeagues()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(ConstantEnum.FAILED_GET_DATA.name) }
                .subscribe {
                    view.setLeagues(it)
                })
    }
}