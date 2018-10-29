package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.network.TheSportDBApiService
import app.learn.kotlin.repository.FavoriteMatchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MatchDetailPresenterImpl @Inject constructor (
        private val view: MatchDetailView,
        private val apiService: TheSportDBApiService,
        private val favoriteRepository: FavoriteMatchRepository)
    : BasePresenterImpl(), MatchDetailPresenter {

    override fun insertMatchToFavorite(favoriteEventEntity: FavoriteEventEntity) {
        super.addDisposable(favoriteRepository.insertEvent(favoriteEventEntity)
                .doOnSubscribe { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { view.showMessage("Failed add to favorite") }
                .doOnSuccess { view.showMessage("Added to favorite") }
                .subscribe())
    }

    override fun deleteMatchFromFavorite(eventId: String?) {
        super.addDisposable(favoriteRepository.deleteEvent(eventId.orEmpty())
                .doOnSubscribe { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { view.showMessage("Failed removed from favorite") }
                .doOnSuccess { view.showMessage("Removed from favorite") }
                .subscribe())
    }

    override fun isExistFavoriteEvent(eventId: String?) {
        super.addDisposable(favoriteRepository.isExistEvent(eventId.orEmpty())
                .doOnSubscribe { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { view.showMessage("failed to get data from db") }
                .doOnSuccess { i -> view.isExistFavoriteEvent(i) }
                .subscribe())
    }

    override fun getDetailEvent() {
        super.addDisposable(apiService.getEventByEventId(view.getEventId().orEmpty())
                .doOnNext { it ->
                    it?.contents?.get(0)?.let {
                        getTeamDetail(it.idHomeTeam.orEmpty())
                        getTeamDetail(it.idAwayTeam.orEmpty())
                    }
                }
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    it?.contents?.get(0)?.let {
                        view.setEventDetailModel(it)
                    }
                })
    }

    private fun getTeamDetail(teamId: String) {
        super.addDisposable(apiService.getTeamByTeamId(teamId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    it?.contents?.get(0)?.let {
                        view.setTeamDetailModel(it)
                    }
                })
    }
}