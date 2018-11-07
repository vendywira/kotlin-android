package app.learn.kotlin.feature.search

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchPresenterImpl @Inject constructor(
        private val view: SearchContract.View,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), SearchContract.Presenter {

    override fun searchTeams(query: String) {
        super.addDisposable(apiService.searchTeams(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.addTeamsFound(it)
                })
    }

    override fun searchEvent(query: String) {
        super.addDisposable(apiService.searchEvents(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.addEventsFound(it)
                })
    }

}