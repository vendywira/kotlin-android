package app.learn.kotlin.feature.favorite

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.entity.FavoriteEventEntity

class FavoriteContract {

    interface presenter : BasePresenter {
        fun getListEventFavorite()
    }

    interface view : BaseView {
        fun setViewModel(listFavoriteEvent: List<FavoriteEventEntity>)

        fun notifyFavoriteChange()
    }
}