package app.learn.kotlin.feature.team.player

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import app.learn.kotlin.R
import app.learn.kotlin.feature.base.BaseFragment
import app.learn.kotlin.helper.mapper
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Player
import app.learn.kotlin.model.vo.PlayerVo
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import javax.inject.Inject

class PlayerListFragment : BaseFragment<PlayerListContract.Presenter>(), PlayerListContract.View {

    @Inject
    internal lateinit var presenter: PlayerListContract.Presenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapterList: PlayerListAdapter
    private var listResponsePlayer: MutableList<PlayerVo> = mutableListOf()


    override fun getPresenter(): PlayerListContract.Presenter? = presenter

    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_player, container, false)
        progressBar = view.find(R.id.base_progress_bar_id)
        swipeRefresh = view.find(R.id.base_swipe_refresh)
        recyclerView = view.find(R.id.base_recycle_view_id)

        val teamId = arguments?.getString(Constant.TEAM_ID).toString()

        presenter.getListTeamPlayer(teamId)
        adapterList = PlayerListAdapter(ctx, listResponsePlayer) {
            Log.d("player", listResponsePlayer[it].toString())
            ctx.startActivity<PlayerDetailActivity>(
                    Constant.PLAYER_INTENT to listResponsePlayer[it])
        }

        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapterList

        swipeRefresh.onRefresh {
            presenter.getListTeamPlayer(teamId)
        }
        return view
    }

    override fun getProgressBar(): ProgressBar? = progressBar

    override fun setPlayerViewModel(listPlayer: ListResponse<Player>) {
        swipeRefresh.isRefreshing = false
        listResponsePlayer.clear()
        listPlayer.contents.let { players ->
            val playersVo = mutableListOf<PlayerVo>()

            players?.forEach {
                val playerVo = mapper.map(it, PlayerVo::class.java)
                Log.d("playerVo", playerVo.toString())
                Log.d("player", it.toString())
                playersVo.add(playerVo) }
            listResponsePlayer.addAll(playersVo)
        }
        adapterList.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = false
        super.showLoading()
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        super.hideLoading()
    }

}