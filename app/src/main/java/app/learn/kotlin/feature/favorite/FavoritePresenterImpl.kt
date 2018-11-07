package app.learn.kotlin.feature.favorite

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.repository.FavoriteMatchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavoritePresenterImpl @Inject constructor(
        private val view: FavoriteContract.View,
        private val favoriteRepository: FavoriteMatchRepository)
    : BasePresenterImpl(), FavoriteContract.Presenter {

    override fun getListEventFavorite() {
        return super.addDisposable(favoriteRepository.getEventAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { FavoriteEventEntity() }
                .doOnComplete { view.notifyDataChange() }
                .subscribe {
                    view.setViewModel(it)
                })
    }

}