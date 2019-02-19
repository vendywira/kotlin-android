package app.learn.kotlin.feature.favourite.event

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BaseIdleResource
import app.learn.kotlin.feature.base.BasePresenterImpl
import app.learn.kotlin.feature.base.BaseView
import app.learn.kotlin.feature.event.match.MatchContract
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.EventEntity
import app.learn.kotlin.repository.FavouriteMatchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavouriteEventPresenterImpl @Inject constructor(
        private val idleListener: BaseIdleListener,
        private val favouriteRepository: FavouriteMatchRepository
) : BasePresenterImpl(), FavouriteEventContract.Presenter {

    lateinit var view: FavouriteEventContract.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as FavouriteEventContract.View
    }

    override fun getListFavorite() {
        return super.addDisposable(favouriteRepository.findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showLoading()
                    idleListener.increment()
                }
                .doOnError { view.showMessage(Constant.FAILED_GET_DATA) }
                .onErrorReturn { EventEntity() }
                .doOnComplete {
                    view.hideLoading()
                    view.notifyDataChange()
                    idleListener.decrement()
                }
                .subscribe { view.setViewModel(it) })
    }

}