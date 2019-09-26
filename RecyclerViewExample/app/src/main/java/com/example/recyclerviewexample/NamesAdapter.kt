package com.example.recyclerviewexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.name_item.view.*

class NamesAdapter(private var names: MutableList<String>)
    :RecyclerView.Adapter<NamesAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // purpose: how names_item and ViewHolder works?
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.name_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = names[position] // "Pekka"
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // view id's here
        val textView: TextView = view.textView
    }
}