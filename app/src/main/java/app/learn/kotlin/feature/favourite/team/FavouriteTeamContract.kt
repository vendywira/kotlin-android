package app.learn.kotlin.feature.favourite.team

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.entity.TeamEntity

class FavouriteTeamContract {

    interface Presenter : BasePresenter {
        fun getListFavorite()
    }

    interface View : BaseView {
        fun setViewModel(data: TeamEntity)

        fun notifyDataChange()
    }
}