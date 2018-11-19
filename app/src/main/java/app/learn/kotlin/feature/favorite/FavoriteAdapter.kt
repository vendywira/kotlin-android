package app.learn.kotlin.feature.favorite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.helper.toSimpleString
import app.learn.kotlin.model.entity.FavoriteEventEntity
import org.jetbrains.anko.find

class FavoriteAdapter (private val listOfMatches: List<FavoriteEventEntity>,
private val onClick: (position: Int) -> Unit)
: RecyclerView.Adapter<FavoriteAdapter.MatchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
        return MatchHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_match, parent, false))
    }

    override fun getItemCount(): Int = listOfMatches.size

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        holder.bindItem(listOfMatches[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    class MatchHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var tvMatchDate: TextView = view.find(R.id.tv_match_date)
        private var tvHomeTeamName: TextView = view.find(R.id.tv_home_team_name)
        private var tvHomeTeamScore: TextView = view.find(R.id.tv_home_team_score)
        private var tvAwayTeamName: TextView = view.find(R.id.tv_away_team_name)
        private var tvAwayTeamScore: TextView = view.find(R.id.tv_away_team_score)

        fun bindItem(favorite: FavoriteEventEntity) {
            tvMatchDate.text = toSimpleString(favorite.strDate)
            tvHomeTeamName.text = favorite.homeTeamName
            tvHomeTeamScore.text = favorite.homeTeamScore?.toString()
            tvAwayTeamName.text = favorite.awayTeamName
            tvAwayTeamScore.text = favorite.awayTeamScore?.toString()
        }
    }
}