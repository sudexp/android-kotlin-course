package com.example.recyclerviewexample

import android.content.AbstractThreadedSyncAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var names: MutableList<String> = mutableListOf("Pekka", "Matti", "Joe")
    lateinit var adapter: NamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NamesAdapter(names)
        recyclerView.adapter = adapter
    }

    fun addClicked(view: View) {
        val name: String = editText.text.toString()
        names.add(name)
        adapter.notifyDataSetChanged()
        editText.setText("")
    }

    fun clearClicked(view: View) {
        names.clear()
        adapter.notifyDataSetChanged()
    }
}
