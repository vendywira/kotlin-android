package app.learn.kotlin.feature.favourite.event

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.entity.EventEntity

class FavouriteEventContract {

    interface Presenter : BasePresenter {
        fun getListFavorite()
    }

    interface View : BaseView {
        fun setViewModel(data: EventEntity)

        fun notifyDataChange()
    }
}