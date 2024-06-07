package com.odal.wooco

import android.content.Context
import android.content.Intent
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

class CoachScheduleActivityAdapter(private var itemList: List<ReserveDataModel>, val context: Context) : RecyclerView.Adapter<CoachScheduleActivityAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.coach_name2)
        val dateTextView: TextView = itemView.findViewById(R.id.class_day)
        val changeClassButton: View = itemView.findViewById(R.id.change_class2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coach_schedule_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.menti_name
        holder.dateTextView.text = item.reserve_time

        holder.changeClassButton.setOnClickListener {
            val mentiName = item.menti_name
            val coachName = item.coach_receiverName
            Log.d("CoachScheduleAdapter", "coachName: $coachName")
            val coachInfoRef = FirebaseDatabase.getInstance().getReference("coachInfo")
                .orderByChild("name")
                .equalTo(coachName)

            coachInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("CoachScheduleAdapter", "dataSnapshot: $dataSnapshot")
                    if (dataSnapshot.exists()) {
                        val coachId = dataSnapshot.children.firstOrNull()?.key
                        val reserveId = item.reserveId

                        Log.d("CoachScheduleAdapter", "reserve_id to send: $reserveId, coach_id: $coachId")

                        val intent = Intent(context, CoachReserve::class.java).apply {
                            putExtra("change", true)
                            putExtra("coach_uid", coachId)
                            putExtra("coach_name", coachName)
                            putExtra("reserve_id", reserveId)
                        }
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "해당하는 코치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("CoachScheduleAdapter", "Failed to read coach data: ${databaseError.message}")
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
