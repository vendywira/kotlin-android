package app.learn.kotlin.feature.event.match

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import app.learn.kotlin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class MatchUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = dip(2)
                rightMargin = dip(2)
                topMargin = dip(5)
                bottomMargin = dip(5)

            }
            backgroundColor = Color.LTGRAY
            radius = dip(8).toFloat()

            linearLayout {
                lparams {
                    width = matchParent
                    height = wrapContent
                    bottomMargin = dip(8)
                    padding = dip(24)
                }
                backgroundColorResource = android.R.color.white
                orientation = LinearLayout.HORIZONTAL
                verticalLayout {
                    textView {
                        id = R.id.tv_match_date
                        text = "20 oktober 2018"
                        gravity = Gravity.CENTER_HORIZONTAL
                        textColorResource = R.color.colorPrimary
                    }.lparams(width = matchParent, height = wrapContent)
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            id = R.id.tv_home_team_name
                            text = "team homeShots"
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams(weight = 1f, width = dip(0), height = wrapContent)
                        linearLayout {
                            lparams(weight = 1f, width = dip(0), height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            textView {
                                id = R.id.tv_home_team_score
                                text = "3"
                                typeface = Typeface.DEFAULT_BOLD
                                textSize = 16f
                            }.lparams {
                                margin = dip(4)
                            }
                            textView {
                                textSize = 14f
                                text = context.getString(R.string.vs)
                            }.lparams {
                                margin = dip(4)
                            }
                            textView {
                                id = R.id.tv_away_team_score
                                text = "5"
                                typeface = Typeface.DEFAULT_BOLD
                                textSize = 16f
                            }.lparams {
                                margin = dip(4)
                            }
                        }
                        textView {
                            id = R.id.tv_away_team_name
                            text = "rifal team"
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams(weight = 1f, width = dip(0), height = wrapContent)
                    }
                }.lparams(width = dip(0), weight = 1f, height = wrapContent)
            }
        }
    }
}