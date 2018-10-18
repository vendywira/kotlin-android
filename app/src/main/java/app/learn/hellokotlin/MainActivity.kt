package app.learn.hellokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var items: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        club_list.layoutManager = LinearLayoutManager(this)
        club_list.adapter =  RecyclerViewAdapter(this, items) {
            val toast = Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    private fun initData() {
        val names = resources.getStringArray(R.array.club_name)
        val images = resources.obtainTypedArray(R.array.club_image)

        items.clear()
        for (i in names.indices) {
            items.add(Item(names[i], images.getResourceId(i, 0)))
        }

        images.recycle()
    }
}
