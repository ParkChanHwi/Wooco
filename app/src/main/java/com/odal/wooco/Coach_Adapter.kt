package com.odal.wooco

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.datamodels.CoachDataModel

class Coach_Adapter(val itemList: List<CoachDataModel>, val context: Context) : RecyclerView.Adapter<Coach_Adapter.ItemViewHolder>() {


    data class Item(
        val nickname: String,
        val schoolOrCompany: String,
        val interest: String,
        val score: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nicknameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val schoolOrCompanyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val interestTextView: TextView = itemView.findViewById(R.id.interestTextView)
        val itemLayout: RelativeLayout = itemView.findViewById(R.id.coach_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_coachlist_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nicknameTextView.text = item.name
        holder.schoolOrCompanyTextView.text = item.school
        holder.interestTextView.text = item.interest

        holder.itemLayout.setOnClickListener {
            val intent = Intent(context, Menti_coach_introduceActivity::class.java).apply {
                putExtra("uid", item.uid)  // 수정: Uid 키를 추가
                putExtra("name", item.name)  // 수정: name 키를 추가
                putExtra("school", item.school)  // 수정: school 키를 추가
                putExtra("interest", item.interest)  // 수정: interest 키를 추가
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemList.size
}