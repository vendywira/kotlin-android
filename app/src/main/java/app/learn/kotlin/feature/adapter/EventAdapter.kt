package app.learn.kotlin.feature.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.R.id.btn_reminder
import app.learn.kotlin.R.id.tv_away_team_name
import app.learn.kotlin.R.id.tv_away_team_score
import app.learn.kotlin.R.id.tv_home_team_name
import app.learn.kotlin.R.id.tv_home_team_score
import app.learn.kotlin.R.id.tv_match_date
import app.learn.kotlin.R.id.tv_time
import app.learn.kotlin.helper.dateFormating
import app.learn.kotlin.helper.gone
import app.learn.kotlin.helper.timeFormating
import app.learn.kotlin.helper.visible
import app.learn.kotlin.model.vo.EventVo
import org.jetbrains.anko.find


class EventAdapter(private val listOfMatches: List<EventVo>,
                   private val onClick: (position: Int) -> Unit,
                   private val onReminderClick: (position: Int) -> Unit)
    : RecyclerView.Adapter<EventAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_match, parent, false))
    }

    override fun getItemCount(): Int = listOfMatches.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItem(listOfMatches[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }

        holder.btnReminder.setOnClickListener {
            onReminderClick(position)
        }
    }


    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvMatchDate: TextView = view.find(tv_match_date)
        private val tvTime: TextView = view.find(tv_time)
        private val tvHomeTeamName: TextView = view.find(tv_home_team_name)
        private val tvHomeTeamScore: TextView = view.find(tv_home_team_score)
        private val tvAwayTeamName: TextView = view.find(tv_away_team_name)
        private val tvAwayTeamScore: TextView = view.find(tv_away_team_score)
        internal val btnReminder: ImageView = view.find(btn_reminder)

        fun bindItem(match: EventVo) {
            tvMatchDate.text = dateFormating(match.dateEvent)
            tvTime.text = timeFormating(match.time.orEmpty())
            tvHomeTeamName.text = match.teamHomeName
            tvHomeTeamScore.text = match.teamHomeScore?.toString()
            tvAwayTeamName.text = match.teamAwayName
            tvAwayTeamScore.text = match.teamAwayScore?.toString()
            if (match.showReminder) btnReminder.visible()
            else btnReminder.gone()
        }
    }
}