package app.learn.kotlin.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.learn.kotlin.R
import app.learn.kotlin.model.Item
import app.learn.kotlin.ui.MainView
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    companion object {
        var items: MutableList<Item> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainView().setContentView(this)
        initData()
    }

    private fun initData() {
        val names = resources.getStringArray(R.array.club_name)
        val images = resources.obtainTypedArray(R.array.club_image)
        val contents = resources.getStringArray(R.array.club_content)

        items.clear()
        for (i in names.indices) {
            items.add(Item(names[i], images.getResourceId(i, 0), contents[i]))
        }

        images.recycle()
    }
}
