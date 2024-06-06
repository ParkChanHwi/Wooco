package com.odal.wooco

import android.content.Context
import android.content.Intent // Intent 클래스를 import 해주세요.
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.ReserveDataModel

class Menti_sheduleActivityAdapter(private var itemList: List<ReserveDataModel>, val context: Context) : RecyclerView.Adapter<Menti_sheduleActivityAdapter.ItemViewHolder>() {

    data class Item(
        val name: String,
        val date: String
    )

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.menti_name2)
        val dateTextView: TextView = itemView.findViewById(R.id.class_day)
        val changeClassButton: View = itemView.findViewById(R.id.change_class) // 버튼을 참조하는 View 추가
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_schedule_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.coach_receiverName
        holder.dateTextView.text = item.reserve_time

        // 아이템의 버튼에 OnClickListener 설정
        holder.changeClassButton.setOnClickListener {
            // 코치 이름을 기반으로 코치 ID를 가져오기 위한 쿼리
            val coachName = item.coach_receiverName
            val coachInfoRef = FirebaseDatabase.getInstance().getReference("coachInfo")
                .orderByChild("name")
                .equalTo(coachName)

            coachInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // 쿼리 결과로부터 코치 ID 가져오기
                        val coachId = dataSnapshot.children.firstOrNull()?.key

                        // 예약 화면으로 이동
                        val intent = Intent(context, MentiReserve::class.java).apply{
                            putExtra("change", true) // "change"라는 키워드를 인텐트에 추가
                            putExtra("coach_uid", coachId) // 코치 ID를 전달
                            putExtra("coach_name", coachName) // 코치 이름을 전달
                        }
                        context.startActivity(intent)
                    } else {
                        // 해당하는 코치 정보가 없는 경우 처리
                        Toast.makeText(context, "해당하는 코치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 데이터베이스 오류 처리
                    Log.e("Menti_sheduleAdapter", "Failed to read coach data: ${databaseError.message}")
                    Toast.makeText(context, "코치 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }



    override fun getItemCount(): Int = itemList.size

    // 수업 시작 시간에 따라 itemList를 정렬하는 메서드
    fun sortByStartTime() {
        itemList = itemList.sortedBy { it.reserve_time }
        notifyDataSetChanged()
    }
}
