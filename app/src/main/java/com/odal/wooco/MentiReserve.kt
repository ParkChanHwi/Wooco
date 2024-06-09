package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import java.util.Calendar
import java.text.SimpleDateFormat
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Locale

class MentiReserve : AppCompatActivity() {
    private lateinit var coach_receiverUid: String // Menti_coach_introduceActivity에서 받아온 코치 UID정보
    private lateinit var coach_receiverName: String
    private lateinit var mentiName: String // 멘티의 이름 변수 추가
    private lateinit var auth: FirebaseAuth
    private lateinit var reserveInfoRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_reserve)

        auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        reserveInfoRef = database.getReference("reserveInfo")

        val currentUser = auth.currentUser
        currentUser?.let {
            val userId = it.uid
            val userInfoRef = database.getReference("userInfo")
            userInfoRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val nickname = dataSnapshot.child("nickname").getValue(String::class.java)
                        nickname?.let {
                            mentiName = it
                            Log.d("UserDisplayName", "User nickname: $mentiName")
                        } ?: run {
                            Log.e("UserDisplayName", "User nickname is null")
                        }
                    } else {
                        Log.e("UserDisplayName", "UserInfo for $userId does not exist")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("UserDisplayName", "Failed to read user info: ${databaseError.message}")
                }
            })
        } ?: run {
            Log.e("UserDisplayName", "Current user is null")
        }

        val calendarView: MaterialCalendarView = findViewById(R.id.calendar_view)
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val reserveButton: Button = findViewById(R.id.reserve_button)
        val arrowButton: ImageView = findViewById(R.id.arrow_3)

        arrowButton.setOnClickListener {
            onBackPressed()
        }

        coach_receiverUid = intent.getStringExtra("coach_uid").toString()
        coach_receiverName = intent.getStringExtra("coach_name").toString()

        // Intent에서 "change" 플래그 확인
        val isChange = intent.getBooleanExtra("change", false)
        Log.d("change", "change : $isChange") // 로그 추가

        val reserveId = intent.getStringExtra("reserve_id")
        Log.d("MentiReserve", "Reserve ID: $reserveId") // 로그 추가

        reserveButton.setOnClickListener {
            val selectedDate = calendarView.selectedDate?.date ?: Calendar.getInstance().time
            val hour = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                timePicker.hour
            } else {
                timePicker.currentHour
            }
            val minute = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                timePicker.minute
            } else {
                timePicker.currentMinute
            }

            // Combine selected date and time
            val selectedCalendar = Calendar.getInstance().apply {
                time = selectedDate
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }

            // Check if selected date is today
            val today = Calendar.getInstance()
            if (selectedCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                selectedCalendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                Toast.makeText(this, "당일 예약은 불가합니다. 다른 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if selected date and time are at least 13 hours in the future
            val currentTime = Calendar.getInstance().timeInMillis
            val thirteenHoursInMillis = 13 * 60 * 60 * 1000
            if (selectedCalendar.timeInMillis < currentTime + thirteenHoursInMillis) {
                Toast.makeText(this, "현재 시간으로부터 13시간 후의 시간만 예약 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Continue with the reservation process
            val day = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedCalendar.time)
            val time = String.format("%02d:%02d", hour, minute)
            val datetime = "$day $time"
            // Toast.makeText(this, "선택한 날짜: $day\n선택한 시간: $time", Toast.LENGTH_LONG).show()

            // 예약 확인을 위한 다이얼로그
            AlertDialog.Builder(this).apply {
                setTitle("예약 확인")
                setMessage("선택한 날짜: $day\n선택한 시간: $time\n예약을 진행하시겠습니까?")
                setPositiveButton("예") { _, _ ->
                    checkForOverlappingReservation(selectedCalendar, selectedCalendar.timeInMillis + 30 * 60 * 1000, reserveId) { isOverlapping ->
                        if (isOverlapping) {
                            Toast.makeText(this@MentiReserve, "이미 예약된 시간이 있습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            currentUser?.let { user ->
                                val mentiUid = user.uid
                                val dataMap = HashMap<String, Any>()
                                dataMap["menti_uid"] = mentiUid
                                dataMap["menti_name"] = mentiName
                                dataMap["coach_receiverUid"] = coach_receiverUid
                                dataMap["coach_receiverName"] = coach_receiverName
                                dataMap["reserve_time"] = datetime

                                if (isChange && reserveId != null) {
                                    // 예약 정보 업데이트
                                    dataMap["reserveId"] = reserveId // reserveId를 추가
                                    reserveInfoRef.child(reserveId).updateChildren(dataMap)
                                        .addOnSuccessListener {
                                            Toast.makeText(this@MentiReserve, "예약이 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this@MentiReserve, Menti_scheduleActivity::class.java)
                                            startActivity(intent)
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(this@MentiReserve, "Firebase에 데이터를 업데이트하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                                        }
                                } else {
                                    // 새로운 예약 추가
                                    val newReserveId = reserveInfoRef.push().key
                                    if (newReserveId != null) {
                                        dataMap["reserveId"] = newReserveId // reserveId를 추가
                                        reserveInfoRef.child(newReserveId).setValue(dataMap)
                                            .addOnSuccessListener {
                                                Toast.makeText(this@MentiReserve, "예약이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(this@MentiReserve, Menti_scheduleActivity::class.java)
                                                startActivity(intent)
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(this@MentiReserve, "Firebase에 데이터를 저장하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                                            }
                                    } else {
                                        Log.e("MentiReserve", "Failed to generate reservation ID")
                                        Toast.makeText(this@MentiReserve, "예약 ID를 생성하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
                setNegativeButton("아니오") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }
    }

    private fun checkForOverlappingReservation(startDateTime: Calendar, endDateTime: Long, excludeReserveId: String?, callback: (Boolean) -> Unit) {
        val query = reserveInfoRef.orderByChild("coach_receiverUid").equalTo(coach_receiverUid)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (reservationSnapshot in dataSnapshot.children) {
                    val reserveId = reservationSnapshot.child("reserveId").getValue(String::class.java)
                    if (reserveId == excludeReserveId) continue

                    val reserveTime = reservationSnapshot.child("reserve_time").getValue(String::class.java)
                    if (reserveTime != null) {
                        val reserveDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(reserveTime)
                        reserveDateTime?.let {
                            val reserveCalendar = Calendar.getInstance().apply { time = it }
                            val reserveEndDateTime = reserveCalendar.timeInMillis + 30 * 60 * 1000
                            if (startDateTime.timeInMillis < reserveEndDateTime && endDateTime > reserveCalendar.timeInMillis) {
                                callback(true)
                                return
                            }
                        }
                    }
                }
                callback(false)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("MentiReserve", "Failed to read reservation info: ${databaseError.message}")
                callback(false)
            }
        })
    }
}
