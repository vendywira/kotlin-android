package app.learn.hellokotlin.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.learn.hellokotlin.model.Constant
import app.learn.hellokotlin.model.Item
import app.learn.hellokotlin.ui.DetailView
import org.jetbrains.anko.setContentView

class DetailClubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val item = intent.getParcelableExtra<Item>(Constant.EXTRA_CONTENT.name)

        DetailView(item).setContentView(this)

    }
}
