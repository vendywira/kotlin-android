package app.learn.kotlin.feature.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar

abstract class BaseActivity<out T : BasePresenter> : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitView()

        getPresenter()?.run { this.onAttach() }
    }

    protected abstract fun onInitView()

    protected abstract fun getPresenter() : T?

    override fun showMessage(message: String) {
        this.contentView?.rootView?.let {
            snackbar(it, message)
        }
    }
}