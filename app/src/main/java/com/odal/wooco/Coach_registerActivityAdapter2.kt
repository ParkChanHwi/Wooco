package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class Coach_registerActivityAdapter2(private val itemList: List<Item>) : RecyclerView.Adapter<Coach_registerActivityAdapter2.ItemViewHolder>() {

    data class Item(
        val document1: Int,
        val document2: Int,
        val document3: Int
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val documentImageView1: ImageView = itemView.findViewById(R.id.document1)
        val documentImageView2: ImageView = itemView.findViewById(R.id.document2)
        val documentImageView3: ImageView = itemView.findViewById(R.id.document3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_register2_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.documentImageView1.setImageResource(item.document1)
        holder.documentImageView2.setImageResource(item.document2)
        holder.documentImageView3.setImageResource(item.document3)
    }

    override fun getItemCount(): Int = itemList.size
}
