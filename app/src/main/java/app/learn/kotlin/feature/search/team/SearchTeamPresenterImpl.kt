package app.learn.kotlin.feature.search.team

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.feature.search.event.SearchEventContract
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchTeamPresenterImpl @Inject constructor(
        private val idleListener: BaseIdleListener,
        private val apiService: TheSportDBApiService)
    : BasePresenterImpl(), SearchTeamContract.Presenter {

    lateinit var view: SearchTeamContract.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as SearchTeamContract.View
    }

    override fun searchTeams(query: String) {
        super.addDisposable(apiService.searchTeams(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doOnTerminate {
                    view.hideLoading()
                    idleListener.decrement()
                }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { ListResponse() }
                .subscribe {
                    view.setViewModel(it)
                })
    }
}