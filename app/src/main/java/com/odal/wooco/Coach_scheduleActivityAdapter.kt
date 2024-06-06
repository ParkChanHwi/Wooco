package com.odal.wooco

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.datamodels.ReserveDataModel

class CoachScheduleActivityAdapter(private var itemList: List<ReserveDataModel>, val context: Context) : RecyclerView.Adapter<CoachScheduleActivityAdapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val date: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.coach_name2)
        val dateTextView: TextView = itemView.findViewById(R.id.class_day)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_schedule_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = "${item.menti_name}"
        holder.dateTextView.text = "${item.reserve_time}"
    }

    override fun getItemCount(): Int = itemList.size

    // 수업 시작 시간에 따라 itemList를 정렬하는 메서드
    fun sortByStartTime() {
        itemList = itemList.sortedBy { it.reserve_time }
        notifyDataSetChanged()
    }
}
