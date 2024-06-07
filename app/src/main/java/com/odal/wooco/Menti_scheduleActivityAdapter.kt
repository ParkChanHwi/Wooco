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

class Menti_sheduleActivityAdapter(private var itemList: List<ReserveDataModel>, val context: Context) : RecyclerView.Adapter<Menti_sheduleActivityAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.menti_name2)
        val dateTextView: TextView = itemView.findViewById(R.id.class_day)
        val changeClassButton: View = itemView.findViewById(R.id.change_class)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menti_schedule_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.coach_receiverName
        holder.dateTextView.text = item.reserve_time

        holder.changeClassButton.setOnClickListener {
            val coachName = item.coach_receiverName
            val coachInfoRef = FirebaseDatabase.getInstance().getReference("coachInfo")
                .orderByChild("name")
                .equalTo(coachName)

            coachInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val coachId = dataSnapshot.children.firstOrNull()?.key
                        val reserveId = item.reserveId

                        Log.d("Menti_sheduleAdapter", "reserve_id to send: $reserveId")

                        val intent = Intent(context, MentiReserve::class.java).apply {
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
                    Log.e("Menti_sheduleAdapter", "Failed to read coach data: ${databaseError.message}")
                    Toast.makeText(context, "코치 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun sortByStartTime() {
        itemList = itemList.sortedBy { it.reserve_time }
        notifyDataSetChanged()
    }
}
