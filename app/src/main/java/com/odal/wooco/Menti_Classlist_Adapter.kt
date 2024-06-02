package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_Classlist_Adapter(private val coachList: List<Coach>) : RecyclerView.Adapter<Menti_Classlist_Adapter.CoachViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_classlist_item, parent, false)
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

        fun bind(coach: Coach) {
            coachClassName.text = coach.className
            coachLastChat.text = coach.lastChat
        }
    }
}

data class Coach(val className: String, val lastChat: String)