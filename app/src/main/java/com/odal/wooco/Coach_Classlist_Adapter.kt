package com.odal.wooco

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.datamodels.Class

class Coach_Classlist_Adapter(private val mentiList: List<Class>) : RecyclerView.Adapter<Coach_Classlist_Adapter.MentiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_classlist_item, parent, false)
        return MentiViewHolder(view)
    }

    override fun onBindViewHolder(holder: MentiViewHolder, position: Int) {
        val classlist = mentiList[position]
        holder.bind(classlist)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra("uid", classlist.mentiUid)   // Menti name
                putExtra("name", classlist.mentiName)
                putExtra("chat_type", 0)  // Chat type 0 for class
            }
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return mentiList.size
    }

    inner class MentiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coachClassName: TextView = itemView.findViewById(R.id.menti_class_name)
        private val coachLastChat: TextView = itemView.findViewById(R.id.menti_last_chat)

        fun bind(menti: Class) {
            coachClassName.text = menti.mentiName
            coachLastChat.text = menti.lastMessage
        }
    }
}
