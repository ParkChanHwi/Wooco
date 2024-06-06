package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivityAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<Menti_coach_introduceActivityAdapter.ItemViewHolder>() {

    data class Item(
        val category1: String,
        val category2: String

    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category1TextView: TextView = itemView.findViewById(R.id.category1)
        val category2TextView: TextView = itemView.findViewById(R.id.category2)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_coach_introduce_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.category1TextView.text = "${item.category1}"
        holder.category2TextView.text = "${item.category2}"
    }


    override fun getItemCount(): Int = itemList.size
}