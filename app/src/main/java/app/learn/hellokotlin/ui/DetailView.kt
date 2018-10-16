package app.learn.hellokotlin.ui

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import app.learn.hellokotlin.Activity.DetailClubActivity
import app.learn.hellokotlin.R
import app.learn.hellokotlin.model.Item
import com.bumptech.glide.Glide
import org.jetbrains.anko.*

class DetailView(var item : Item) : AnkoComponent<DetailClubActivity> {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun createView(ui: AnkoContext<DetailClubActivity>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            lparams(matchParent, matchParent)

            imageView() {
                Glide.with(this).load(item.image).into(this)
                id = R.id.detail_image
                padding = dip(20)
            }.lparams(dip(100), dip(100)){
                gravity = Gravity.CENTER_HORIZONTAL
            }

            textView {
                id = R.id.detail_name
                textSize = sp(10).toFloat()
                gravity = Gravity.CENTER_HORIZONTAL
                padding = dip(10)
                text = item.name
            }

            textView {
                id = R.id.detail_content
                text = item.content
                gravity = Gravity.CENTER_HORIZONTAL
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                padding = dip(20)
            }

        }
    }
}