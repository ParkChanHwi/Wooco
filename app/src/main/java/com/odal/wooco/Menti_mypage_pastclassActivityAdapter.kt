package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_mypage_pastclassActivityAdapter(private val pastClassList: List<PastClassItem>) : RecyclerView.Adapter<Menti_mypage_pastclassActivityAdapter.PastClassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastClassViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_mypage_pastclass_item, parent, false)
        return PastClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: PastClassViewHolder, position: Int) {
        val pastClassItem = pastClassList[position]
        holder.nicknameTextView.text = "${pastClassItem.coach_receiverName}"
        holder.schoolOrCompanyTextView.text = "${pastClassItem.schoolOrCompany}"
        holder.dateTextView.text = "${pastClassItem.reserve_time}"
    }

    override fun getItemCount(): Int {
        return pastClassList.size
    }

    class PastClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nicknameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val schoolOrCompanyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)

        init {
            // findViewById 호출 결과를 확인
            if (nicknameTextView == null || schoolOrCompanyTextView == null || dateTextView == null) {
                throw NullPointerException("View references are null in ViewHolder")
            }
        }
    }
}
