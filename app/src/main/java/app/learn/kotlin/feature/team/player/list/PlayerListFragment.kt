package app.learn.kotlin.feature.team.player.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Player
import kotlinx.android.synthetic.main.progress_bar.*

class PlayerListFragment : BaseFragment<PlayerListContract.Presenter>(), PlayerListContract.View {

    internal lateinit var presenter: PlayerListContract.Presenter
    private lateinit var progressBar: ProgressBar

    override fun getPresenter(): PlayerListContract.Presenter? = presenter

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_player, container, false)
        progressBar = base_progress_bar_id

        return view
    }

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun setPlayerViewModel(players: ListResponse<Player>) {

    }

}