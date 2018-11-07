package app.learn.kotlin.feature.favorite

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.entity.FavoriteEventEntity

class FavoriteContract {

    interface Presenter : BasePresenter {
        fun getListEventFavorite()
    }

    interface View : BaseView {
        fun setViewModel(favorite: FavoriteEventEntity)

        fun notifyDataChange()
    }
}