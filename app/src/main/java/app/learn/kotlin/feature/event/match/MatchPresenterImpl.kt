package app.learn.kotlin.feature.event.match

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers

class MatchPresenterImpl(
        private val idleListener: BaseIdleListener,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), MatchContract.Presenter {

    lateinit var view: MatchContract.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as MatchContract.View
    }

    override fun getLastMatch() {
        super.addDisposable(apiService.getLastMatchByLeagueId(view.getSelectedLeagueId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doOnTerminate {
                    view.hideLoading()
                    idleListener.decrement()
                }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.setViewModel(it)
                })
    }

    override fun getNextMatch() {
        super.addDisposable(apiService.getNextMatchByLeagueId(view.getSelectedLeagueId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doOnTerminate {
                    view.hideLoading()
                    idleListener.decrement()
                }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.setViewModel(it)
                })
    }

    override fun getAllLeague() {
        super.addDisposable(apiService.getAllLeagues()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doOnTerminate {
                    view.hideLoading()
                    idleListener.decrement()
                }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.setLeagues(it)
                })
    }
}