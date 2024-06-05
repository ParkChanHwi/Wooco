package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Coach_mypage_pastclassActivityAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<Coach_mypage_pastclassActivityAdapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val date: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView) // 수정된 부분: datetTextView -> dateTextView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_mypage_pastclass_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = "${item.name}"
        holder.dateTextView.text = "${item.date}"

    }


    override fun getItemCount(): Int = itemList.size
}