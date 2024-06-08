package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Coach_registerActivityAdapter(private val itemList: MutableList<Item>) : RecyclerView.Adapter<Coach_registerActivityAdapter.ItemViewHolder>() {

    data class Item(
        val category3: String,
        val category4: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category3TextView: TextView = itemView.findViewById(R.id.category1)
        val category4TextView: TextView = itemView.findViewById(R.id.category2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_register_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.category3TextView.text = item.category3
        holder.category4TextView.text = item.category4
    }

    override fun getItemCount(): Int = itemList.size

    fun addItem(item: Item) {
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    fun getItemList(): List<Item> = itemList
}
