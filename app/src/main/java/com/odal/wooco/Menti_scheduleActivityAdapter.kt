package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_sheduleActivityAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<Menti_sheduleActivityAdapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val date: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.menti_name2)
        val dateTextView: TextView = itemView.findViewById(R.id.class_day)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_schedule_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = "${item.name}"
        holder.dateTextView.text = "${item.date}"
    }


    override fun getItemCount(): Int = itemList.size
}