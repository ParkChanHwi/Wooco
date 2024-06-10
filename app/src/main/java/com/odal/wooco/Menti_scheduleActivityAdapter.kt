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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.ReserveDataModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Menti_sheduleActivityAdapter(private var itemList: List<ReserveDataModel>, val context: Context) :
    RecyclerView.Adapter<Menti_sheduleActivityAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.menti_name2)
        val dateTextView: TextView = itemView.findViewById(R.id.class_day)
        val changeClassButton: View = itemView.findViewById(R.id.change_class)
        val cancelClassButton: View = itemView.findViewById(R.id.cancel_class)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menti_schedule_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.coach_receiverName
        holder.dateTextView.text = item.reserve_time

        holder.changeClassButton.setOnClickListener {
            val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val reserveDateTime = dateTimeFormat.parse(item.reserve_time)
            val currentTime = Calendar.getInstance().time
            val timeDifference = reserveDateTime.time - currentTime.time

            if (timeDifference < 12 * 60 * 60 * 1000) {
                // 경고창 표시
                val builder = AlertDialog.Builder(context)
                builder.setTitle("변경 불가")
                builder.setMessage("수업 시작 12시간 전까지만 변경 가능합니다.")
                builder.setPositiveButton("확인") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()
            } else {
                val coachName = item.coach_receiverName
                Log.d("coach_receiverName", "coach_receiverName: $coachName")

                val coachInfoRef = FirebaseDatabase.getInstance().getReference("coachInfo")
                    .orderByChild("name")
                    .equalTo(coachName)

                coachInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val coachId = dataSnapshot.children.firstOrNull()?.key
                            Log.d("coachId", "coachId: $coachId")

                            val reserveId = item.reserveId

                            val intent = Intent(context, MentiReserve::class.java).apply {
                                putExtra("change", true)
                                putExtra("coach_uid", coachId)
                                putExtra("coach_name", coachName)
                                putExtra("reserve_id", reserveId)
                            }
                            context.startActivity(intent)

                            // 알림 추가
                            val notificationRef = FirebaseDatabase.getInstance().getReference("notifications")
                            val notificationId = notificationRef.push().key
                            if (notificationId == null) {
                                Log.e("Menti_sheduleAdapter", "Failed to get notification ID")
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
                                    Log.d("Menti_sheduleAdapter", "Notification added successfully")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Menti_sheduleAdapter", "Failed to add notification: ${e.message}")
                                }
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

        holder.cancelClassButton.setOnClickListener {
            val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val reserveDateTime = dateTimeFormat.parse(item.reserve_time)
            val currentTime = Calendar.getInstance().time
            val timeDifference = reserveDateTime.time - currentTime.time

            if (timeDifference < 12 * 60 * 60 * 1000) {
                // 경고창 표시
                val builder = AlertDialog.Builder(context)
                builder.setTitle("수업 취소")
                builder.setMessage("지금 취소하시면 우코인 환불이 불가합니다.\n그래도 계속 진행하시겠습니까?")

                builder.setPositiveButton("예") { dialog, _ ->
                    val reserveId = item.reserveId
                    if (reserveId != null) {
                        val reserveInfoRef = FirebaseDatabase.getInstance().getReference("reserveInfo").child(reserveId)

                        reserveInfoRef.removeValue().addOnSuccessListener {
                            Toast.makeText(context, "예약이 성공적으로 취소되었습니다.", Toast.LENGTH_SHORT).show()
                            notifyItemRemoved(position)
                            itemList = itemList.filter { it.reserveId != reserveId }
                            notifyDataSetChanged()

                            // 알림 추가
                            val notificationRef = FirebaseDatabase.getInstance().getReference("notifications")
                            val notificationId = notificationRef.push().key
                            if (notificationId == null) {
                                Log.e("Menti_sheduleAdapter", "Failed to get notification ID")
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
                                    Log.d("Menti_sheduleAdapter", "Notification added successfully")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Menti_sheduleAdapter", "Failed to add notification: ${e.message}")
                                }
                        }.addOnFailureListener { e ->
                            Toast.makeText(context, "예약을 취소하는 데 실패했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                            Log.e("Menti_sheduleAdapter", "Failed to cancel reservation: ${e.message}")
                        }
                    } else {
                        Toast.makeText(context, "예약 ID가 null입니다.", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }
                builder.setNegativeButton("아니요") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()
            } else {
                val reserveId = item.reserveId
                if (reserveId != null) {
                    // 확인 다이얼로그 표시
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("수업 취소")
                    builder.setMessage("정말 수업을 취소하시겠습니까?")
                    builder.setPositiveButton("예") { dialog, _ ->
                        // 사용자가 '예' 버튼을 클릭함
                        val reserveInfoRef = FirebaseDatabase.getInstance().getReference("reserveInfo").child(reserveId)

                        reserveInfoRef.removeValue().addOnSuccessListener {
                            Toast.makeText(context, "예약이 성공적으로 취소되었습니다.", Toast.LENGTH_SHORT).show()
                            notifyItemRemoved(position)
                            itemList = itemList.filter { it.reserveId != reserveId }
                            notifyDataSetChanged()

                            // 알림 추가
                            val notificationRef = FirebaseDatabase.getInstance().getReference("notifications")
                            val notificationId = notificationRef.push().key
                            if (notificationId == null) {
                                Log.e("Menti_sheduleAdapter", "Failed to get notification ID")
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
                                    Log.d("Menti_sheduleAdapter", "Notification added successfully")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Menti_sheduleAdapter", "Failed to add notification: ${e.message}")
                                }
                        }.addOnFailureListener { e ->
                            Toast.makeText(context, "예약을 취소하는 데 실패했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                            Log.e("Menti_sheduleAdapter", "Failed to cancel reservation: ${e.message}")
                        }
                    }
                    builder.setNegativeButton("아니요") { dialog, _ ->
                        dialog.dismiss()
                    }
                    builder.create().show()
                } else {
                    Toast.makeText(context, "예약 ID가 null입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun sortByStartTime() {
        itemList = itemList.sortedBy { it.reserve_time }
        notifyDataSetChanged()
    }
}
