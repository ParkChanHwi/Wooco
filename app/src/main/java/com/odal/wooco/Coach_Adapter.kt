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

class Coach_Adapter(private val itemList: List<CoachDataModel>, private val context: Context) : RecyclerView.Adapter<Coach_Adapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nicknameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
        val schoolOrCompanyTextView: TextView = itemView.findViewById(R.id.schoolOrCompanyTextView)
        val interestTextView: TextView = itemView.findViewById(R.id.interestTextView)
        val starScoreTextView: TextView = itemView.findViewById(R.id.star_score) // 별점 TextView
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
        holder.starScoreTextView.text = item.starScore.toString() // 별점 표시

        holder.itemLayout.setOnClickListener {
            val uid = item.uid
            val name = item.name
            val school = item.school
            val interest = item.interest

            // Menti_coach_introduceActivity1로 가는 Intent (우선 이 Intent만 사용)
            val intent = Intent(context, Menti_coach_introduceActivity::class.java).apply {
                putExtra("uid", uid)
                putExtra("name", name)
                putExtra("school", school)
                putExtra("interest", interest)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
