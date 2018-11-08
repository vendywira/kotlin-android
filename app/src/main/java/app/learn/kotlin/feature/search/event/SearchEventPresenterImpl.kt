package app.learn.kotlin.feature.search.event

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchEventPresenterImpl @Inject constructor(
        private val view: SearchEventContract.View,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), SearchEventContract.Presenter  {

    override fun searchMatches(query: String) {
        super.addDisposable(apiService.searchEvents(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.setViewModel(it)
                })
    }
}