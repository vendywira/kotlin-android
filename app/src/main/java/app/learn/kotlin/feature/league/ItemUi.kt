package app.learn.kotlin.feature.league

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import app.learn.kotlin.R
import org.jetbrains.anko.*

class ItemUi : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.HORIZONTAL
            lparams(matchParent, wrapContent)
            padding = dip(16)

            imageView{
                id = R.id.image_id
                imageResource = R.drawable.img_barca
            }.lparams(dip(50), dip(50))

            textView {
                id = R.id.name_id
                text = resources.getString(R.string.defult_club_name)
            }.lparams(matchParent, wrapContent){
                margin = dip(10)
                gravity = Gravity.CENTER_VERTICAL
            }

        }
    }

}