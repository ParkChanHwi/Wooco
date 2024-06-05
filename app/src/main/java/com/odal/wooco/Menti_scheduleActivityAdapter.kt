package com.odal.wooco

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.datamodels.ReserveDataModel

class Menti_sheduleActivityAdapter(var itemList: List<ReserveDataModel>, val context: Context) : RecyclerView.Adapter<Menti_sheduleActivityAdapter.ItemViewHolder>() {

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
        holder.nameTextView.text = item.coach_receiverName
        holder.dateTextView.text = item.reserve_time
    }


    override fun getItemCount(): Int = itemList.size
}