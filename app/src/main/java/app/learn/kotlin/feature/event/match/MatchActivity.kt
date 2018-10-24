package app.learn.kotlin.feature.event.match

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.learn.kotlin.R
import app.learn.kotlin.R.layout.activity_main

class MatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        val matchTabFragment = MatchTabFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.content_layout, matchTabFragment, matchTabFragment.tag)
                .commit()
    }
}