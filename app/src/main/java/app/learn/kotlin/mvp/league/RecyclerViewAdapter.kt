package app.learn.kotlin.mvp.league

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.learn.kotlin.R
import app.learn.kotlin.model.Team
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

class RecyclerViewAdapter(
        private val context: Context,
        private val items: List<Team>,
        private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(ItemUi().createView(AnkoContext.create(context, parent)), null)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(items[position], listener)

    class ViewHolder(view: View, override val containerView: View?) : RecyclerView.ViewHolder(view), LayoutContainer {

        val name = itemView.findViewById<TextView>(R.id.name_id)
        val image = itemView.findViewById<ImageView>(R.id.image_id)

        fun bindItem(items : Team, listener: (Team) -> Unit) {
            name.text = items.name
            Glide.with(itemView.context).load(items.image).into(image)

            itemView.setOnClickListener {
                listener(items)
            }

        }
    }

}