package app.learn.kotlin.feature.favorite.team

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.favorite.event.FavoriteTeamContract
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteTeamEntity
import app.learn.kotlin.repository.FavoriteTeamRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavoriteTeamPresenterImpl @Inject constructor(
        private val view: FavoriteTeamContract.View,
        private val favoriteRepository: FavoriteTeamRepository
): BasePresenterImpl(), FavoriteTeamContract.Presenter {

    override fun getListFavorite() {
        return super.addDisposable(favoriteRepository.findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { FavoriteTeamEntity() }
                .doOnComplete {
                    view.hideLoading()
                    view.notifyDataChange() }
                .subscribe { view.setViewModel(it) })
    }

}