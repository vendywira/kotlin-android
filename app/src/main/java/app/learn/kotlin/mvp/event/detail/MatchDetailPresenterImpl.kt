package app.learn.kotlin.mvp.event.detail

import app.learn.kotlin.mvp.base.BasePresenterImpl
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MatchDetailPresenterImpl @Inject constructor (
        private val view: MatchDetailView,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), MatchDetailPresenter {

    override fun getDetailEvent() {
        super.addDisposable(apiService.getEventByEventId(view.getEventId().orEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnNext {
                    it?.events?.get(0)?.let {
                        getTeamDetail(it.idHomeTeam.orEmpty())
                        getTeamDetail(it.idAwayTeam.orEmpty())
                    }
                }
                .subscribe {
                    it?.events?.get(0)?.let {
                        view.setEventDetailModel(it)
                    }
                })
    }

    private fun getTeamDetail(teamId: String) {
        super.addDisposable(apiService.getTeamByTeamId(teamId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it?.teams?.get(0)?.let {
                        view.setTeamDetailModel(it)
                    }
                })
    }
}