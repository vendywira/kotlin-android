package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.feature.search.team.SearchTeamContract
import app.learn.kotlin.model.entity.TeamEntity
import app.learn.kotlin.repository.FavoriteTeamRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TeamDetailPresenterImpl @Inject constructor(
        private val idleListener: BaseIdleListener,
        private val favoriteRepository: FavoriteTeamRepository)
    : BasePresenterImpl(), TeamDetailContract.Presenter {

    companion object {
        const val FAILED_ADD_TO_FAVORITE = "Failed add to favorite"
        const val ADDED_TO_FAVORITE = "Added to favorite"
        const val FAILED_TO_REMOVE_FROM_FAVORITE = "Failed to remove from favorite"
        const val REMOVED_FROM_FAVORITE = "Removed from favorite"
        const val FAILED_GET_DATA_FROM_DB = "Failed get data from db"
    }

    lateinit var view: TeamDetailContract.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as TeamDetailContract.View
    }

    override fun insertTeamToFavorite(teamEntity: TeamEntity) {
        super.addDisposable(favoriteRepository.insert(teamEntity)
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

    override fun deleteMatchFromFavorite(teamId: String?) {
        super.addDisposable(favoriteRepository.delete(teamId.orEmpty())
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

    override fun isExistFavoriteTeam(teamId: String?) {
        super.addDisposable(favoriteRepository.isExist(teamId.orEmpty())
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
                .doOnSuccess { i -> view.isExistFavoriteTeam(i) }
                .subscribe())
    }
}