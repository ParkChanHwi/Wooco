package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_mtpage_record_star3Adapter(private val items: List<ReviewItem>) :
    RecyclerView.Adapter<Menti_mtpage_record_star3Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nicknameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val schoolOrCompanyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val starScoreTextView: TextView = itemView.findViewById(R.id.star_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menti_mypage_record_star_item2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nicknameTextView.text = item.coachName
        holder.schoolOrCompanyTextView.text = item.category
        holder.dateTextView.text = item.reviewDate
        holder.starScoreTextView.text = item.starScore
    }

    override fun getItemCount(): Int = items.size
}
