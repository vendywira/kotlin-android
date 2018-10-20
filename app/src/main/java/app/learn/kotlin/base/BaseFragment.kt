package app.learn.kotlin.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<out T : BasePresenterSevice> : Fragment(), BaseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        this.getPresenter()?.run {
            this.onAttach()
        }

        return onInitView(inflater, container, savedInstanceState)
    }

    abstract fun getPresenter(): T?

    protected abstract fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
}