package app.learn.kotlin.feature.event.match

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MatchPresenterImpl @Inject constructor (
        private val view: MatchView,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), MatchPresenter {

    override fun getLastMatch() {
        super.addDisposable(apiService.getLastMatchByLeagueId(view.getSelectedLeagueId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse()}
                .subscribe {
                    view.setViewModel(it)
                })
    }

    override fun getNextMatch() {
        super.addDisposable(apiService.getNextMatchByLeagueId(view.getSelectedLeagueId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.setViewModel(it)
                })
    }

    override fun getAllLeague() {
        super.addDisposable(apiService.getAllLeagues()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.setLeagues(it)
                })
    }
}