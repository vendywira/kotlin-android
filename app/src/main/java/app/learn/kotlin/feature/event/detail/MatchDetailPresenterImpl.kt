package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BaseIdleResource
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.entity.EventEntity
import app.learn.kotlin.network.TheSportDBApiService
import app.learn.kotlin.repository.FavouriteMatchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MatchDetailPresenterImpl @Inject constructor(
        private val idleListener: BaseIdleListener,
        private val view: MatchDetailContract.View,
        private val apiService: TheSportDBApiService,
        private val favouriteRepository: FavouriteMatchRepository)
    : BasePresenterImpl(), MatchDetailContract.Presenter {

    companion object {
        const val FAILED_ADD_TO_FAVORITE = "Failed add to favorite"
        const val ADDED_TO_FAVORITE = "Added to favorite"
        const val FAILED_TO_REMOVE_FROM_FAVORITE = "Failed to remove from favorite"
        const val REMOVED_FROM_FAVORITE = "Removed from favorite"
        const val FAILED_GET_DATA_FROM_DB = "Failed get data from db"
    }

    override fun insertMatchToFavorite(eventEntity: EventEntity) {
        super.addDisposable(favouriteRepository.insert(eventEntity)
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doAfterTerminate {
                    view.hideLoading()
                    idleListener.decrement()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { view.showMessage(FAILED_ADD_TO_FAVORITE) }
                .doOnSuccess { view.showMessage(ADDED_TO_FAVORITE) }
                .subscribe())
    }

    override fun deleteMatchFromFavorite(eventId: String?) {
        super.addDisposable(favouriteRepository.delete(eventId.orEmpty())
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doAfterTerminate {
                    view.hideLoading()
                    idleListener.decrement()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { view.showMessage(FAILED_TO_REMOVE_FROM_FAVORITE) }
                .doOnSuccess { view.showMessage(REMOVED_FROM_FAVORITE) }
                .subscribe())
    }

    override fun isExistFavoriteEvent(eventId: String?) {
        super.addDisposable(favouriteRepository.isExist(eventId.orEmpty())
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doAfterTerminate {
                    view.hideLoading()
                    idleListener.decrement()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { view.showMessage(FAILED_GET_DATA_FROM_DB) }
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
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doOnTerminate {
                    view.hideLoading()
                    idleListener.decrement()
                }
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