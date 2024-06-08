package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.datamodels.CoachCategoryDataModel

class MentiCoachIntroduceAdapter(private val itemList: List<CoachCategoryDataModel>) : RecyclerView.Adapter<MentiCoachIntroduceAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTextView: TextView = itemView.findViewById(R.id.category1)
        val detailTextView: TextView = itemView.findViewById(R.id.category2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_coach_introduce_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.categoryTextView.text = item.category
        holder.detailTextView.text = item.detail
    }

    override fun getItemCount(): Int = itemList.size
}
