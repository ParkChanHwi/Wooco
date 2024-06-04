package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_mypage_pastclassActivityAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<Menti_mypage_pastclassActivityAdapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val school_company: String,
        val interest: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val school_companyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val interestTextView: TextView = itemView.findViewById(R.id.interestTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_mypage_pastclass_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = "${item.name}"
        holder.school_companyTextView.text = "${item.school_company}"
        holder.interestTextView.text = "${item.interest}"

    }


    override fun getItemCount(): Int = itemList.size
}