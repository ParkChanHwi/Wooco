package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivity3Adapter(private val itemList: List<Item>) : RecyclerView.Adapter<Menti_coach_introduceActivity3Adapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val date: String,
        var review: String

    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val dateTextView: TextView = itemView.findViewById(R.id.date)
        val reviewTextView: TextView=itemView.findViewById(R.id.review)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_coach_introduce3_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = "${item.name}"
        holder.dateTextView.text = "${item.date}"
        holder.reviewTextView.text ="${item.review}"
    }


    override fun getItemCount(): Int = itemList.size
}