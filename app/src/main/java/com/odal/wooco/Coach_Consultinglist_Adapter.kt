package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Coach_Cunsultinglist_Adapter(private val mentiList: List<Consult_Menti>) : RecyclerView.Adapter<Coach_Cunsultinglist_Adapter.MentiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_consultinglist_item, parent, false)
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
        private val coachClassName: TextView = itemView.findViewById(R.id.menti_cosult_name)
        private val coachLastChat: TextView = itemView.findViewById(R.id.menti_consult_last_chat)

        fun bind(menti: Consult_Menti) {
            coachClassName.text = menti.className
            coachLastChat.text = menti.lastChat
        }
    }
}

data class Consult_Menti(val className: String, val lastChat: String)