package app.learn.kotlin.feature.team.player

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.learn.kotlin.R
import app.learn.kotlin.helper.loadImageUrl
import app.learn.kotlin.model.vo.PlayerVo
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_list_player.view.*

class PlayerListAdapter (private val context: Context,
                         private val items: List<PlayerVo>,
                         private val listener: (position: Int) -> Unit)
    : RecyclerView.Adapter<PlayerListAdapter.PlayerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PlayerHolder(LayoutInflater.from(context).inflate(R.layout.item_list_player, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bindItem(items[position])

        holder.itemView.setOnClickListener {
            listener(position)
        }
    }

    class PlayerHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(items: PlayerVo) {
            view.tv_palyer_name.text = items.name
            view.tv_player_position.text = items.position
            view.img_player.loadImageUrl(items.playerImageUrl ?: items.thumbImageUrl.orEmpty()).apply{
                RequestOptions().override(140, 180)
            }
        }
    }
}