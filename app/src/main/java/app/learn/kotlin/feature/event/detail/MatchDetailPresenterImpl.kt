package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.network.TheSportDBApiService
import app.learn.kotlin.repository.FavoriteMatchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MatchDetailPresenterImpl @Inject constructor (
        private val view: MatchDetailView,
        private val apiService: TheSportDBApiService,
        private val favoriteRepository: FavoriteMatchRepository)
    : BasePresenterImpl(), MatchDetailPresenter {

    override fun insertMatchToFavorite(favoriteEventEntity: FavoriteEventEntity) {
        favoriteRepository.insertEvent(favoriteEventEntity)
    }

    override fun deleteMatchFromFavorite(eventId: String?) {
        if (eventId != null) {
            favoriteRepository.deleteEvent(eventId)
        }
    }

    override fun isExistFavoriteEvent(eventId: String?): Boolean {
        if (eventId != null) {
            return favoriteRepository.isExistEvent(eventId)
        }
        return false
    }

    override fun getDetailEvent() {
        super.addDisposable(apiService.getEventByEventId(view.getEventId().orEmpty())
                .doOnNext {
                    it?.contents?.get(0)?.let {
                        getTeamDetail(it.idHomeTeam.orEmpty())
                        getTeamDetail(it.idAwayTeam.orEmpty())
                    }
                }
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    it?.contents?.get(0)?.let {
                        view.setEventDetailModel(it)
                    }
                })
    }

    private fun getTeamDetail(teamId: String) {
        super.addDisposable(apiService.getTeamByTeamId(teamId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    it?.contents?.get(0)?.let {
                        view.setTeamDetailModel(it)
                    }
                })
    }
}