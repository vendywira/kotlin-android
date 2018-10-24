package app.learn.kotlin.feature.league

import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.LeagueResponse
import app.learn.kotlin.model.response.TeamResponse
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainPresenterImpl @Inject constructor (
        private val view: MainView,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), MainPresenter {

    override fun getLaugueList() {
        super.addDisposable(apiService.getAllLeagues()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { LeagueResponse() }
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
                .onErrorReturn { TeamResponse() }
                .subscribe {
                    view.showTeamList(it)
                })
    }
}