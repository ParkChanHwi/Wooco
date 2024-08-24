package com.odal.wooco

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_mypage_record_starActivityAdapter(
    private val itemList: List<Menti_mypage_record_starActivityAdapter.Item>,
    private val context: Context
) : RecyclerView.Adapter<Menti_mypage_record_starActivityAdapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val school_company: String,
        val interest: String,
        val coachUid: String // 코치의 UID를 추가합니다
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val school_companyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val interestTextView: TextView = itemView.findViewById(R.id.interestTextView)
        val recordStarButton: Button = itemView.findViewById(R.id.record_star_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_mypage_record_star_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.name
        holder.school_companyTextView.text = item.school_company
        holder.interestTextView.text = item.interest

        // 버튼 클릭 리스너 설정
        holder.recordStarButton.setOnClickListener {
            val intent = Intent(context, Menti_mypage_record_star2Activity::class.java)
            intent.putExtra("COACH_UID", item.coachUid) // 코치 UID를 전달합니다
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
