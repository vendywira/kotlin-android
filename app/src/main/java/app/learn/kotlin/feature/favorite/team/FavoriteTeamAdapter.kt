package app.learn.kotlin.feature.favorite.team

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.R.id.img_team_logo
import app.learn.kotlin.helper.loadImageUrl
import app.learn.kotlin.model.entity.FavoriteTeamEntity
import org.jetbrains.anko.find

class FavoriteTeamAdapter (private val listOfTeams: List<FavoriteTeamEntity>,
                           private val onClick: (position: Int) -> Unit)
: RecyclerView.Adapter<FavoriteTeamAdapter.TeamHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        return TeamHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_team, parent, false))
    }

    override fun getItemCount(): Int = listOfTeams.size

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        holder.bindItem(listOfTeams[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    class TeamHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgTeamLogo: ImageView = view.find(img_team_logo)
        private val tvTeamName: TextView = view.find(R.id.tv_team_name)

        fun bindItem(favorite: FavoriteTeamEntity) {
            imgTeamLogo.loadImageUrl(favorite.teamLogoUrl.orEmpty())
            tvTeamName.text = favorite.teamName
        }
    }
}