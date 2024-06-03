package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Coach_Adapter(private val itemList: List<Item>) : RecyclerView.Adapter<Coach_Adapter.ItemViewHolder>() {

    data class Item(
        val nickname: String,
        val schoolOrCompany: String,
        val interest: String,
        val score: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nicknameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val schoolOrCompanyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val interestTextView: TextView = itemView.findViewById(R.id.interestTextView)
        val scoreTextView: TextView = itemView.findViewById(R.id.star_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_coachlist_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nicknameTextView.text = " ${item.nickname}"
        holder.schoolOrCompanyTextView.text = " ${item.schoolOrCompany}"
        holder.interestTextView.text = " ${item.interest}"
        holder.scoreTextView.text = item.score
    }

    override fun getItemCount(): Int = itemList.size
}