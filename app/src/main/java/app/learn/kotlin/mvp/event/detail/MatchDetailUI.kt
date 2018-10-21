package app.learn.kotlin.mvp.event.detail

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import app.learn.kotlin.R
import org.jetbrains.anko.*

class MatchDetailUI : AnkoComponent<MatchDetailActivity> {

    lateinit var root: LinearLayout
    lateinit var tvStartDate: TextView
    lateinit var tvHomeTeamName: TextView
    lateinit var tvHomeScore: TextView
    lateinit var tvHomeForward: TextView
    lateinit var tvHomeDefense: TextView
    lateinit var tvHomeGoalKeeper: TextView
    lateinit var tvHomeGoals: TextView
    lateinit var tvHomeMidfield: TextView
    lateinit var tvHomeShots: TextView
    lateinit var tvHomeSubstitutes: TextView
    lateinit var tvHomeTeamFormation: TextView
    lateinit var ivHomeTeamIcon: ImageView
    lateinit var tvAwayDefense: TextView
    lateinit var tvAwayForward: TextView
    lateinit var tvAwayGoalKeeper: TextView
    lateinit var tvAwayGoals: TextView
    lateinit var tvAwayMidfield: TextView
    lateinit var tvAwayScore: TextView
    lateinit var tvAwayShots: TextView
    lateinit var tvAwaySubstitutes: TextView
    lateinit var tvAwayTeamFormation: TextView
    lateinit var tvAwayTeamName: TextView
    lateinit var ivAwayTeamIcon: ImageView
    lateinit var progressBar: ProgressBar

    override fun createView(ui: AnkoContext<MatchDetailActivity>): View {
        return with(ui) {
            scrollView {
                root = verticalLayout {
                    lparams(width = matchParent, height = matchParent)
                    progressBar = progressBar()
                    tvStartDate = textView {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }.lparams(width = matchParent, height = wrapContent) {
                        margin = dip(8)
                    }
                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.HORIZONTAL
                        verticalLayout {
                            lparams(width = dip(0), weight = 1f, height = wrapContent)
                            gravity = Gravity.CENTER
                            ivHomeTeamIcon = imageView().lparams {
                                width = dip(60)
                                height = dip(60)
                            }
                            tvHomeTeamName = textView {
                                id = R.id.tv_home_team_name
                                textColorResource = R.color.colorPrimary
                                textSize = 18f
                            }.lparams(width = wrapContent)
                            tvHomeTeamFormation = textView {
                                id = R.id.tv_home_team_line_up
                            }.lparams(width = wrapContent)
                        }

                        linearLayout {
                            lparams(width = dip(0), weight = 1f, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            tvHomeScore = textView {
                                id = R.id.tv_home_team_score
                                typeface = Typeface.DEFAULT_BOLD
                                textSize = 36f
                            }.lparams {
                                margin = dip(4)
                            }
                            textView(context.getString(R.string.vs)).lparams {
                                margin = dip(4)
                            }
                            tvAwayScore = textView {
                                id = R.id.tv_away_team_score
                                typeface = Typeface.DEFAULT_BOLD
                                textSize = 36f
                            }.lparams {
                                margin = dip(4)
                            }
                        }
                        verticalLayout {
                            lparams(width = dip(0), weight = 1f, height = wrapContent)
                            gravity = Gravity.CENTER
                            ivAwayTeamIcon = imageView().lparams {
                                width = dip(60)
                                height = dip(60)
                            }
                            tvAwayTeamName = textView {
                                id = R.id.tv_away_team_name
                                textColorResource = R.color.colorPrimary
                                textSize = 18f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }.lparams(width = wrapContent)
                            tvAwayTeamFormation = textView {
                                id = R.id.tv_away_team_line_up
                            }.lparams(width = wrapContent)
                        }
                    }
                    view {
                        backgroundColorResource = R.color.gray
                    }.lparams(height = dip(1), width = matchParent) {
                        margin = dip(4)
                    }

                    //Goals
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams {
                            topMargin = dip(8)
                            rightMargin = dip(16)
                            leftMargin = dip(16)
                            width = matchParent
                            height = wrapContent
                        }
                        tvHomeGoals = textView {

                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                        textView("Goals") {
                            gravity = Gravity.CENTER_HORIZONTAL
                            textColorResource = R.color.colorPrimary
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                        tvAwayGoals = textView {
                            gravity = Gravity.END
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                    }

                    // Shots
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams {
                            topMargin = dip(8)
                            rightMargin = dip(16)
                            leftMargin = dip(16)
                            width = matchParent
                            height = wrapContent
                        }
                        tvHomeShots = textView {

                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                        textView("Shoots") {
                            gravity = Gravity.CENTER_HORIZONTAL
                            textColorResource = R.color.colorPrimary
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)

                        tvAwayShots = textView {
                            gravity = Gravity.END
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                    }
                    view {
                        backgroundColorResource = R.color.gray
                    }.lparams(height = dip(1), width = matchParent) {
                        margin = dip(4)
                    }
                    textView(R.string.label_lineups) {
                        width = matchParent
                        gravity = Gravity.CENTER
                        textSize = 24f
                    }

                    // Goal Keeper
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams {
                            topMargin = dip(8)
                            rightMargin = dip(16)
                            leftMargin = dip(16)
                            width = matchParent
                            height = wrapContent
                        }
                        tvHomeGoalKeeper = textView {

                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                        textView(R.string.label_goal_keeper) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            textColorResource = R.color.colorPrimary
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                        tvAwayGoalKeeper = textView {
                            gravity = Gravity.END
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                    }

                    // Defense
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams {
                            topMargin = dip(8)
                            rightMargin = dip(16)
                            leftMargin = dip(16)
                            width = matchParent
                            height = wrapContent
                        }
                        tvHomeDefense = textView {

                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                        textView(R.string.label_defense) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            textColorResource = R.color.colorPrimary
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)

                        tvAwayDefense = textView {
                            gravity = Gravity.END
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                    }

                    //Midfield
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams {
                            topMargin = dip(8)
                            rightMargin = dip(16)
                            leftMargin = dip(16)
                            width = matchParent
                            height = wrapContent
                        }
                        tvHomeMidfield = textView {

                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                        textView(R.string.label_midfield) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            textColorResource = R.color.colorPrimary
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)

                        tvAwayMidfield = textView {
                            gravity = Gravity.END
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                    }

                    //Forward
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams {
                            topMargin = dip(8)
                            rightMargin = dip(16)
                            leftMargin = dip(16)
                            width = matchParent
                            height = wrapContent
                        }
                        tvHomeForward = textView {

                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                        textView(R.string.label_forward) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            textColorResource = R.color.colorPrimary
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)

                        tvAwayForward = textView {
                            gravity = Gravity.END
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                    }

                    //Substitutes
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams {
                            topMargin = dip(8)
                            rightMargin = dip(16)
                            leftMargin = dip(16)
                            width = matchParent
                            height = wrapContent
                        }
                        tvHomeSubstitutes = textView().lparams(width = dip(0), weight = 1f, height = wrapContent)
                        textView(R.string.label_substitutes) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            textColorResource = R.color.colorPrimary
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)

                        tvAwaySubstitutes = textView {
                            gravity = Gravity.END
                        }.lparams(width = dip(0), weight = 1f, height = wrapContent)
                    }
                }
            }
        }
    }
}