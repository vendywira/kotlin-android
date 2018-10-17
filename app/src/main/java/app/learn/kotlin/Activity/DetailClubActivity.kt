package app.learn.kotlin.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.Item
import app.learn.kotlin.ui.DetailView
import org.jetbrains.anko.setContentView

class DetailClubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val item = intent.getParcelableExtra<Item>(Constant.EXTRA_CONTENT.name)

        DetailView(item).setContentView(this)

    }
}
