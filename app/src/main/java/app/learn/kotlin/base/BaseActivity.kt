package app.learn.kotlin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.learn.kotlin.helper.invisible
import app.learn.kotlin.helper.visible
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar

abstract class BaseActivity<out T : BasePresenterSevice> : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onReadyView()

        getPresenter()?.run { this.onAttach() }
    }

    protected abstract fun onReadyView()

    protected abstract fun getPresenter() : T?

    override fun showLoading() {
        getProgressBar()?.visible()
    }

    override fun hideLoading() {
        getProgressBar()?.invisible()
    }

    override fun showMessage(message: String) {
        this.contentView?.rootView?.let {
            snackbar(it, message)
        }
    }
}