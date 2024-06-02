package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Coach_Classlist_Adapter(private val mentiList: List<Menti>) : RecyclerView.Adapter<Coach_Classlist_Adapter.MentiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_classlist_item, parent, false)
        return MentiViewHolder(view)
    }

    override fun onBindViewHolder(holder: MentiViewHolder, position: Int) {
        val menti = mentiList[position]
        holder.bind(menti)
    }

    override fun getItemCount(): Int {
        return mentiList.size
    }

    inner class MentiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coachClassName: TextView = itemView.findViewById(R.id.menti_class_name)
        private val coachLastChat: TextView = itemView.findViewById(R.id.menti_last_chat)

        fun bind(menti: Menti) {
            coachClassName.text = menti.className
            coachLastChat.text = menti.lastChat
        }
    }
}

data class Menti(val className: String, val lastChat: String)