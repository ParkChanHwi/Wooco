package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_Consultinglist_Adater(private val coachList: List<Cosult_Coach>) : RecyclerView.Adapter<Menti_Consultinglist_Adater.CoachViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_consultinglist_item, parent, false)
        return CoachViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        val coach = coachList[position]
        holder.bind(coach)
    }

    override fun getItemCount(): Int {
        return coachList.size
    }

    inner class CoachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coachClassName: TextView = itemView.findViewById(R.id.coach_class_name)
        private val coachLastChat: TextView = itemView.findViewById(R.id.coach_last_chat)

        fun bind(coach: Cosult_Coach) {
            coachClassName.text = coach.className
            coachLastChat.text = coach.lastChat
        }
    }
}

data class Cosult_Coach(val className: String, val lastChat: String)