package app.learn.kotlin.feature.team.list

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.feature.event.detail.TeamDetailContract
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class TeamListPresenterImpl(
        private val idleListener: BaseIdleListener,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), TeamListContract.Presenter {

    lateinit var view: TeamListContract.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as TeamListContract.View
    }

    override fun getLeagueList() {
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
                    view.getListLaugue(it)
                })
    }

    override fun getTeamList(leagueName: String) {
        super.addDisposable(apiService.getAllTeams(view.leagueName())
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
                    view.showTeamList(it)
                })
    }
}