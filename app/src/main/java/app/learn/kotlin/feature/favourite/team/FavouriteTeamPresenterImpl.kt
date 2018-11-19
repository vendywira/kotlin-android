package app.learn.kotlin.feature.favourite.team

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.TeamEntity
import app.learn.kotlin.repository.FavoriteTeamRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavouriteTeamPresenterImpl @Inject constructor(
        private val view: FavouriteTeamContract.View,
        private val favoriteRepository: FavoriteTeamRepository
): BasePresenterImpl(), FavouriteTeamContract.Presenter {

    override fun getListFavorite() {
        return super.addDisposable(favoriteRepository.findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { TeamEntity() }
                .doOnComplete {
                    view.hideLoading()
                    view.notifyDataChange() }
                .subscribe { view.setViewModel(it) })
    }

}