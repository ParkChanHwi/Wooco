package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_mypage_record_starActivityAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<Menti_mypage_record_starActivityAdapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val school_company: String,
        val interest: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val school_companyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val interestTextView: TextView = itemView.findViewById(R.id.interestTextView)
        val recordStarButton: Button = itemView.findViewById(R.id.record_star_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_mypage_record_star_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.name
        holder.school_companyTextView.text = item.school_company
        holder.interestTextView.text = item.interest
        holder.recordStarButton.setOnClickListener {
            // 여기에 클릭 시 수행할 로직 추가
        }
    }

    override fun getItemCount(): Int = itemList.size
}
