package com.odal.wooco

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.R
import com.odal.wooco.datamodels.CoachDataModel
import com.odal.wooco.Menti_coach_introduceActivity3

class Coach_Adapter(val itemList: List<CoachDataModel>, val context: Context) : RecyclerView.Adapter<Coach_Adapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nicknameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val schoolOrCompanyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val interestTextView: TextView = itemView.findViewById(R.id.interestTextView)
        //val scoreTextView: TextView = itemView.findViewById(R.id.star_score)
        // 코치 클릭 가능하게
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
        //holder.scoreTextView.text = item.score?.toString() ?: "N/A"

        holder.itemLayout.setOnClickListener {
            val intent = Intent(context, Menti_coach_introduceActivity3::class.java).apply {
                putExtra("coachId", item.uid)  // item.uid를 사용해서 데이터를 전달
                // 필요한 경우 다른 데이터도 추가로 전달 가능
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemList.size
}