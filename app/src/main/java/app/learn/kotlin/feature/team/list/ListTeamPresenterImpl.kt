package app.learn.kotlin.feature.team.list

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ListTeamPresenterImpl @Inject constructor (
        private val view: ListTeamContract.View,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), ListTeamContract.Presenter {

    override fun getLeagueList() {
        super.addDisposable(apiService.getAllLeagues()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.getListLaugue(it)
                })
    }

    override fun getTeamList(leagueName: String) {
        super.addDisposable(apiService.getAllTeams(view.leagueName())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.showTeamList(it)
                })
    }
}