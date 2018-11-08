package app.learn.kotlin.feature.search.team

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchTeamPresenterImpl @Inject constructor(
        private val view: SearchTeamContract.View,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), SearchTeamContract.Presenter {

    override fun searchTeams(query: String) {
        super.addDisposable(apiService.searchTeams(query)
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