package app.learn.kotlin.feature.favorite.event

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.model.entity.FavoriteTeamEntity

class FavoriteEventContract {

    interface Presenter : BasePresenter {
        fun getListFavorite()
    }

    interface View : BaseView {
        fun setViewModel(data: FavoriteEventEntity)

        fun notifyDataChange()
    }
}