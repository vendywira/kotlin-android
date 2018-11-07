package app.learn.kotlin.feature.team

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.model.response.Team
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

class TeamAdapter(
        private val context: Context,
        private val items: List<Team>,
        private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamAdapter.TeamHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TeamHolder(LayoutInflater.from(context).inflate(R.layout.item_list_team, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TeamHolder, position: Int) = holder.bindItem(items[position], listener)

    class TeamHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = itemView.findViewById<TextView>(R.id.name)
        val image = itemView.findViewById<ImageView>(R.id.image)

        fun bindItem(items : Team, listener: (Team) -> Unit) {
            name.text = items.name
            Glide.with(itemView.context).load(items.image).into(image)

            itemView.setOnClickListener {
                listener(items)
            }

        }
    }

}