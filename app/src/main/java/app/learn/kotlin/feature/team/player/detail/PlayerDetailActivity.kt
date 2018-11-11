package app.learn.kotlin.feature.team.player.detail

import android.widget.ProgressBar
import app.learn.kotlin.feature.base.BaseActivity

class PlayerDetailActivity : BaseActivity<PlayerDetailContract.Presenter>(), PlayerDetailContract.View {

    override fun onInitView() {

    }

    override fun getPresenter(): PlayerDetailContract.Presenter? = null

    override fun getProgressBar(): ProgressBar? = null
}