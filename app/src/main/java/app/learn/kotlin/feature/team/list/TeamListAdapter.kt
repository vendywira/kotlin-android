package app.learn.kotlin.feature.team.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.helper.loadImageUrl
import app.learn.kotlin.model.vo.TeamVo

class TeamListAdapter(
        private val context: Context,
        private val items: List<TeamVo>,
        private val listener: (position: Int) -> Unit)
    : RecyclerView.Adapter<TeamListAdapter.TeamHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TeamHolder(LayoutInflater.from(context).inflate(R.layout.item_list_team, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        holder.bindItem(items[position])

        holder.itemView.setOnClickListener {
            listener(position)
        }
    }

    class TeamHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = itemView.findViewById<TextView>(R.id.tv_team_name)
        val image = itemView.findViewById<ImageView>(R.id.img_logo)

        fun bindItem(items : TeamVo) {
            name.text = items.teamName
            image.loadImageUrl(items.teamLogoUrl.orEmpty())
        }
    }

}