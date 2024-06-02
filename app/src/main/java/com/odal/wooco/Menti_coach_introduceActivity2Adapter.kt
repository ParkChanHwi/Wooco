package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivity2Adapter(private val itemList: List<Item>) : RecyclerView.Adapter<Menti_coach_introduceActivity2Adapter.ItemViewHolder>() {

    data class Item(
        val explain: String,
        val explain2: String,
        val explain3: String,
        val explain4: String

    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val explainTextView: TextView = itemView.findViewById(R.id.myself1)
        val explain2TextView: TextView = itemView.findViewById(R.id.myself2)
        val explain3TextView: TextView = itemView.findViewById(R.id.myself3)
        val explain4TextView: TextView = itemView.findViewById(R.id.myself4)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_coach_introduce2_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.explainTextView.text = "${item.explain}"
        holder.explain2TextView.text = "${item.explain2}"
        holder.explain3TextView.text = "${item.explain3}"
        holder.explain4TextView.text = "${item.explain4}"
    }


    override fun getItemCount(): Int = itemList.size
}