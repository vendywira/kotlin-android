package app.learn.kotlin.feature.team.player.list

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.event.detail.TeamDetailPresenterImpl
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PlayerListPresenterImpl @Inject constructor(
        private val view : PlayerListContract.View,
        private val apiService: TheSportDBApiService
) : BasePresenterImpl(), PlayerListContract.Presenter {

    override fun getListTeamPlayer(teamId: String) {
        super.addDisposable(apiService.getAllPlayers(teamId)
                .doOnSubscribe { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { view.showMessage(TeamDetailPresenterImpl.FAILED_ADD_TO_FAVORITE) }
                .subscribe {
                    view.setPlayerViewModel(it)
                })
    }
}