package app.learn.kotlin.feature.favorite.event

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.repository.FavoriteMatchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavoriteEventPresenterImpl @Inject constructor(
        private val eventView: FavoriteEventContract.View,
        private val favoriteRepository: FavoriteMatchRepository
): BasePresenterImpl(), FavoriteEventContract.Presenter {

    override fun getListFavorite() {
        return super.addDisposable(favoriteRepository.findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { eventView.showLoading() }
                .doOnTerminate { eventView.hideLoading() }
                .doOnError { eventView.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { FavoriteEventEntity() }
                .doOnComplete { eventView.notifyDataChange() }
                .subscribe {
                    eventView.setViewModel(it)
                })
    }

}