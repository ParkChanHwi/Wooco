package com.odal.wooco

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.datamodels.Class

class Menti_Classlist_Adapter(private val coachList: List<Class>) : RecyclerView.Adapter<Menti_Classlist_Adapter.CoachViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_classlist_item, parent, false)
        return CoachViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        val classlist = coachList[position]
        holder.bind(classlist)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra("uid", classlist.coachUid)   // Menti name
                putExtra("name", classlist.coachName)
                putExtra("chat_type", 0)  // Chat type 0 for class
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return coachList.size
    }

    inner class CoachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coachClassName: TextView = itemView.findViewById(R.id.coach_class_name)
        private val coachLastChat: TextView = itemView.findViewById(R.id.coach_last_chat)

        fun bind(coach: Class) {
            coachClassName.text = coach.coachName
            coachLastChat.text = coach.lastMessage
        }
    }
}
