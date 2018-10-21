package app.learn.kotlin.mvp.event.match

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.model.MatchModelVO
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class MatchAdapter(private val listOfMatch: List<MatchModelVO>,
                   private val onClick: (position: Int) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.MatchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
        return MatchHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = listOfMatch.size

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        holder.bindItem(listOfMatch[position])
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

        fun bindItem(match: MatchModelVO) {
            tvMatchDate.text = match.startDate
            tvHomeTeamName.text = match.homeTeamName
            tvHomeTeamScore.text = match.homeTeamScore
            tvAwayTeamName.text = match.awayTeamName
            tvAwayTeamScore.text = match.awayTeamScore
        }
    }
}