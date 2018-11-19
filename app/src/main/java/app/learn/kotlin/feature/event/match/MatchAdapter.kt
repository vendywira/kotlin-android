package app.learn.kotlin.feature.event.match

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.R.id.*
import app.learn.kotlin.helper.toSimpleString
import app.learn.kotlin.model.vo.MatchVO
import org.jetbrains.anko.find


class MatchAdapter(private val listOfMatches: List<MatchVO>,
                   private val onClick: (position: Int) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.MatchHolder>() {

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

        private var tvMatchDate: TextView = view.find(tv_match_date)
        private var tvHomeTeamName: TextView = view.find(tv_home_team_name)
        private var tvHomeTeamScore: TextView = view.find(tv_home_team_score)
        private var tvAwayTeamName: TextView = view.find(tv_away_team_name)
        private var tvAwayTeamScore: TextView = view.find(tv_away_team_score)

        fun bindItem(match: MatchVO) {
            tvMatchDate.text = toSimpleString(match.strDate)
            tvHomeTeamName.text = match.homeTeamName
            tvHomeTeamScore.text = match.homeTeamScore?.toString()
            tvAwayTeamName.text = match.awayTeamName
            tvAwayTeamScore.text = match.awayTeamScore?.toString()
        }
    }
}