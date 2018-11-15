package app.learn.kotlin.feature.favourite.event

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.EventEntity
import app.learn.kotlin.repository.FavoriteMatchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavouriteEventPresenterImpl @Inject constructor(
        private val view: FavouriteEventContract.View,
        private val favoriteRepository: FavoriteMatchRepository
): BasePresenterImpl(), FavouriteEventContract.Presenter {

    override fun getListFavorite() {
        return super.addDisposable(favoriteRepository.findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { EventEntity() }
                .doOnComplete {
                    view.hideLoading()
                    view.notifyDataChange() }
                .subscribe { view.setViewModel(it) })
    }

}