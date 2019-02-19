package app.learn.kotlin.feature.favourite.team

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.feature.favourite.event.FavouriteEventContract
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.TeamEntity
import app.learn.kotlin.repository.FavoriteTeamRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavouriteTeamPresenterImpl @Inject constructor(
        private val idleListener: BaseIdleListener,
        private val favoriteRepository: FavoriteTeamRepository
) : BasePresenterImpl(), FavouriteTeamContract.Presenter {

    lateinit var view: FavouriteTeamContract.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as FavouriteTeamContract.View
    }

    override fun getListFavorite() {
        return super.addDisposable(favoriteRepository.findAll()
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
                .onErrorReturn { TeamEntity() }
                .doOnComplete {
                    view.hideLoading()
                    view.notifyDataChange()
                }
                .subscribe { view.setViewModel(it) })
    }

}