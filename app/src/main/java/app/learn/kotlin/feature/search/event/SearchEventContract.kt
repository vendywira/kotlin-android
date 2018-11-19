package app.learn.kotlin.feature.search.event

import app.learn.kotlin.feature.base.BasePresenter
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.ListResponse

class SearchEventContract {

    interface  View : BaseView {
        fun setViewModel(data: ListResponse<Event>)

        fun search(query: String)
    }

    interface Presenter : BasePresenter {
        fun searchMatches(query: String)
    }
}