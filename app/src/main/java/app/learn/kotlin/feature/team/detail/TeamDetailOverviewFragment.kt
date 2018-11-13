package app.learn.kotlin.feature.team.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.learn.kotlin.R
import app.learn.kotlin.model.Constant
import kotlinx.android.synthetic.main.fragment_team_overview.view.*

class TeamDetailOverviewFragment : Fragment() {

    lateinit var content: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = arguments?.getString(Constant.CONTENT_TEAM_OVERVIEW).toString()
        Log.d("content", content)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_team_overview, container, false)
        view.tv_overview.text = content
        return view
    }
}