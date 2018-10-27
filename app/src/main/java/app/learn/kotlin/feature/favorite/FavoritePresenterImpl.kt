package app.learn.kotlin.feature.favorite

import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.repository.FavoriteMatchRepository
import javax.inject.Inject

class FavoritePresenterImpl @Inject constructor(
        private val view: FavoriteContract.view,
        private val favoriteRepository: FavoriteMatchRepository)
    : BasePresenterImpl(), FavoriteContract.presenter {

    override fun getListEventFavorite() {
        view.showLoading()
        view.setViewModel(favoriteRepository.getEventAll())
        view.hideLoading()
    }

}