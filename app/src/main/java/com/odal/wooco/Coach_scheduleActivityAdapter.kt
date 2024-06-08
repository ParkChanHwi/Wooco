package com.odal.wooco

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.odal.wooco.datamodels.ReserveDataModel

class CoachScheduleActivityAdapter(private var itemList: List<ReserveDataModel>, val context: Context) : RecyclerView.Adapter<CoachScheduleActivityAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.coach_name2)
        val dateTextView: TextView = itemView.findViewById(R.id.class_day)
        val changeClassButton: View = itemView.findViewById(R.id.change_class2)
        val cancelClassButton: View = itemView.findViewById(R.id.cancel_class2)
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

                        // Add notification
                        val notificationRef = FirebaseDatabase.getInstance().getReference("notifications")
                        val notificationId = notificationRef.push().key
                        if (notificationId == null) {
                            Log.e("CoachScheduleAdapter", "Failed to get notification ID")
                            return
                        }
                        val notificationMessage =
                            "${item.coach_receiverName}(코치)님과 ${item.menti_name}(멘티)님의 ${item.reserve_time} 수업이 변경되었습니다."

                        val notificationData = mapOf(
                            "id" to notificationId,
                            "coach_receiverName" to item.coach_receiverName,
                            "menti_name" to item.menti_name,
                            "message" to notificationMessage,
                            "timestamp" to System.currentTimeMillis()
                        )

                        notificationRef.child(notificationId).setValue(notificationData)
                            .addOnSuccessListener {
                                Log.d("CoachScheduleAdapter", "Notification added successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.e("CoachScheduleAdapter", "Failed to add notification: ${e.message}")
                            }
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

        holder.cancelClassButton.setOnClickListener {
            val reserveId = item.reserveId
            if (reserveId != null) {
                // Show confirmation dialog
                val builder = AlertDialog.Builder(context)
                builder.setTitle("수업 취소")
                builder.setMessage("정말 수업을 취소하시겠습니까?")
                builder.setPositiveButton("예") { dialog, _ ->
                    // User clicked Yes button
                    val reserveInfoRef = FirebaseDatabase.getInstance().getReference("reserveInfo").child(reserveId)

                    reserveInfoRef.removeValue().addOnSuccessListener {
                        Toast.makeText(context, "예약이 성공적으로 취소되었습니다.", Toast.LENGTH_SHORT).show()
                        notifyItemRemoved(position)
                        itemList = itemList.filter { it.reserveId != reserveId }
                        notifyDataSetChanged()

                        // Add notification
                        val notificationRef = FirebaseDatabase.getInstance().getReference("notifications")
                        val notificationId = notificationRef.push().key
                        if (notificationId == null) {
                            Log.e("CoachScheduleAdapter", "Failed to get notification ID")
                            return@addOnSuccessListener
                        }
                        val notificationMessage =
                            "${item.coach_receiverName}(코치)님과 ${item.menti_name}(멘티)님의 ${item.reserve_time} 수업이 취소되었습니다."

                        val notificationData = mapOf(
                            "id" to notificationId,
                            "coach_receiverName" to item.coach_receiverName,
                            "menti_name" to item.menti_name,
                            "message" to notificationMessage,
                            "timestamp" to System.currentTimeMillis()
                        )

                        notificationRef.child(notificationId).setValue(notificationData)
                            .addOnSuccessListener {
                                Log.d("CoachScheduleAdapter", "Notification added successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.e("CoachScheduleAdapter", "Failed to add notification: ${e.message}")
                            }
                    }.addOnFailureListener { e ->
                        Toast.makeText(context, "예약을 취소하는 데 실패했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("CoachScheduleAdapter", "Failed to cancel reservation: ${e.message}")
                    }
                }
                builder.setNegativeButton("아니요") { dialog, _ ->
                    // User clicked No button, just dismiss the dialog
                    dialog.dismiss()
                }
                builder.create().show()
            } else {
                Toast.makeText(context, "예약 ID가 null입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun sortByStartTime() {
        itemList = itemList.sortedBy { it.reserve_time }
        notifyDataSetChanged()
    }
}
