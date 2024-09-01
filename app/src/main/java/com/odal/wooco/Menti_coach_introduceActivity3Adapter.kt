package com.odal.wooco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivity3Adapter(private val itemList: List<Item>) : RecyclerView.Adapter<Menti_coach_introduceActivity3Adapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val date: String,
        var review: String,
        val stars: Double,          // 별점
        val respondSpeed: String,   // 답장 속도
        val satisfaction: String    // 만족도
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val dateTextView: TextView = itemView.findViewById(R.id.date)
        val reviewTextView: TextView = itemView.findViewById(R.id.review)
        val starViews = listOf(
            itemView.findViewById<ImageView>(R.id.star1),
            itemView.findViewById<ImageView>(R.id.star2),
            itemView.findViewById<ImageView>(R.id.star3),
            itemView.findViewById<ImageView>(R.id.star4),
            itemView.findViewById<ImageView>(R.id.star5)
        )
        val respondSpeedTextView: TextView = itemView.findViewById(R.id.respondSpeed)
        val satisfactionTextView: TextView = itemView.findViewById(R.id.satisfaction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_coach_introduce3_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.name
        holder.dateTextView.text = item.date
        holder.reviewTextView.text = item.review

        // 별점 개수에 따라 별 이미지를 설정
        for (i in 0 until 5) {
            if (i < item.stars.toInt()) {
                holder.starViews[i].setImageResource(R.drawable.starscore)  // 채워진 별 이미지
            } else {
                holder.starViews[i].setImageResource(R.drawable.star_score1)  // 빈 별 이미지
            }
        }

        // 해시태그 형식으로 respondSpeed와 satisfaction 표시
        holder.respondSpeedTextView.text = "#${item.respondSpeed}"
        holder.satisfactionTextView.text = "#${item.satisfaction}"
    }

    override fun getItemCount(): Int = itemList.size
}
