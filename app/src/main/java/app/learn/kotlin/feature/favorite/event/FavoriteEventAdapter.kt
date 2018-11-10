package app.learn.kotlin.feature.favorite.event

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.helper.gone
import app.learn.kotlin.helper.toSimpleString
import app.learn.kotlin.model.entity.FavoriteEventEntity
import org.jetbrains.anko.find

class FavoriteEventAdapter (private val listOfMatches: List<FavoriteEventEntity>,
                            private val onClick: (position: Int) -> Unit)
: RecyclerView.Adapter<FavoriteEventAdapter.MatchHolder>() {

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

        private val tvMatchDate: TextView = view.find(R.id.tv_match_date)
        private val tvHomeTeamName: TextView = view.find(R.id.tv_home_team_name)
        private val tvHomeTeamScore: TextView = view.find(R.id.tv_home_team_score)
        private val tvAwayTeamName: TextView = view.find(R.id.tv_away_team_name)
        private val tvAwayTeamScore: TextView = view.find(R.id.tv_away_team_score)
        private val btnReminder: ImageView = view.find(R.id.btn_reminder)

        fun bindItem(favorite: FavoriteEventEntity) {
            tvMatchDate.text = toSimpleString(favorite.strDate)
            tvHomeTeamName.text = favorite.homeTeamName
            tvHomeTeamScore.text = favorite.homeTeamScore?.toString()
            tvAwayTeamName.text = favorite.awayTeamName
            tvAwayTeamScore.text = favorite.awayTeamScore?.toString()
            btnReminder.gone()
        }
    }
}