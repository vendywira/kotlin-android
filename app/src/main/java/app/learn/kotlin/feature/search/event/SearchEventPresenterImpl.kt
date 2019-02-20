package app.learn.kotlin.feature.search.event

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers

class SearchEventPresenterImpl(
        private val idleListener: BaseIdleListener,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), SearchEventContract.Presenter {

    lateinit var view: SearchEventContract.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as SearchEventContract.View
    }

    override fun searchMatches(query: String) {
        super.addDisposable(apiService.searchEvents(query)
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
}