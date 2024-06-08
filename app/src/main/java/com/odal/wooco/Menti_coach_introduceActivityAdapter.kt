package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.datamodels.CoachCategoryDataModel

class Menti_coach_introduceActivityAdapter(private val itemList: List<CoachCategoryDataModel>) : RecyclerView.Adapter<Menti_coach_introduceActivityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_coach_introduce_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryTextView: TextView = itemView.findViewById(R.id.category1)
        private val detailTextView: TextView = itemView.findViewById(R.id.category2)

        fun bind(coachCategoryDataModel: CoachCategoryDataModel) {
            categoryTextView.text = coachCategoryDataModel.category
            detailTextView.text = coachCategoryDataModel.detail
        }
    }
}
