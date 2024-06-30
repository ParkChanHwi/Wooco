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

class CoachReserve : AppCompatActivity() {
    private lateinit var coach_receiverUid: String // Menti_coach_introduceActivity에서 받아온 코치 UID정보
    private lateinit var coach_receiverName: String
    private lateinit var coachName: String // 코치의 이름 변수 추가
    private lateinit var mentiName: String // 멘티의 이름 변수 추가

    private lateinit var auth: FirebaseAuth
    private lateinit var reserveInfoRef: DatabaseReference
    private lateinit var userInfoRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_reserve)

        auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        reserveInfoRef = database.getReference("reserveInfo")
        userInfoRef = database.getReference("userInfo")

        val currentUser = auth.currentUser
        currentUser?.let {
            val userId = it.uid
            userInfoRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val nickname = dataSnapshot.child("nickname").getValue(String::class.java)
                        nickname?.let {
                            coachName = it
                            Log.d("UserDisplayName", "User nickname: $coachName")
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
        mentiName = intent.getStringExtra("mentiName").toString()

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
                Toast.makeText(this, "당일 예약 변경은 불가합니다. 다른 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if selected date and time are in the past
            if (selectedCalendar.before(Calendar.getInstance())) {
                Toast.makeText(this, "올바른 날짜와 시간으로 예약해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val day = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedCalendar.time)
                val time = String.format("%02d:%02d", hour, minute)
                val datetime = "$day $time"
                val selectedCategory = intent.getStringExtra("selectedCategory") ?: "미선택" // 선택한 카테고리 가져오기

                // 예약 확인을 위한 다이얼로그
                AlertDialog.Builder(this).apply {
                    setTitle("예약 확인")
                    setMessage("선택한 날짜: $day\n선택한 시간: $time\n선택한 카테고리: $selectedCategory\n예약을 진행하시겠습니까?")
                    setPositiveButton("예") { _, _ ->
                        getMentiUidByName(mentiName) { mentiUid ->
                            if (mentiUid != null) {
                                checkForOverlappingReservation(mentiUid, selectedCalendar, selectedCalendar.timeInMillis + 30 * 60 * 1000, reserveId) { isOverlapping ->
                                    if (isOverlapping) {
                                        Toast.makeText(this@CoachReserve, "이미 예약된 시간이 있습니다.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        val dataMap = HashMap<String, Any>()
                                        dataMap["menti_uid"] = mentiUid
                                        dataMap["menti_name"] = mentiName
                                        dataMap["coach_receiverUid"] = coach_receiverUid
                                        dataMap["coach_receiverName"] = coach_receiverName
                                        dataMap["reserve_time"] = datetime
                                        dataMap["selected_category"] = selectedCategory // 선택한 카테고리 정보 추가

                                        if (isChange && reserveId != null) {
                                            // 예약 정보 업데이트
                                            dataMap["reserveId"] = reserveId // reserveId를 추가
                                            reserveInfoRef.child(reserveId).updateChildren(dataMap)
                                                .addOnSuccessListener {
                                                    Toast.makeText(this@CoachReserve, "예약이 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                                                    val intent = Intent(this@CoachReserve, Coach_scheduleActivity::class.java)
                                                    startActivity(intent)
                                                }
                                                .addOnFailureListener {
                                                    Toast.makeText(this@CoachReserve, "Firebase에 데이터를 업데이트하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                                                }
                                        } else {
                                            // 새로운 예약 추가
                                            val newReserveId = reserveInfoRef.push().key
                                            if (newReserveId != null) {
                                                dataMap["reserveId"] = newReserveId // reserveId를 추가
                                                reserveInfoRef.child(newReserveId).setValue(dataMap)
                                                    .addOnSuccessListener {
                                                        Toast.makeText(this@CoachReserve, "예약이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show()
                                                        val intent = Intent(this@CoachReserve, Coach_scheduleActivity::class.java)
                                                        startActivity(intent)
                                                    }
                                                    .addOnFailureListener {
                                                        Toast.makeText(this@CoachReserve, "Firebase에 데이터를 저장하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                                                    }
                                            } else {
                                                Log.e("CoachReserve", "Failed to generate reservation ID")
                                                Toast.makeText(this@CoachReserve, "예약 ID를 생성하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(this@CoachReserve, "멘티 UID를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
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
    }

    private fun checkForOverlappingReservation(mentiUid: String, startDateTime: Calendar, endDateTime: Long, excludeReserveId: String?, callback: (Boolean) -> Unit) {
        val queries = listOf(
            reserveInfoRef.orderByChild("coach_receiverUid").equalTo(coach_receiverUid),
            reserveInfoRef.orderByChild("menti_uid").equalTo(mentiUid)
        )

        var isOverlappingFound = false
        var pendingQueries = queries.size

        for (query in queries) {
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
                                    isOverlappingFound = true
                                    callback(true)
                                    return
                                }
                            }
                        }
                    }
                    pendingQueries -= 1
                    if (pendingQueries == 0 && !isOverlappingFound) {
                        callback(false)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("CoachReserve", "Failed to check for overlapping reservations: ${databaseError.message}")
                    pendingQueries -= 1
                    if (pendingQueries == 0 && !isOverlappingFound) {
                        callback(false)
                    }
                }
            })
        }
    }

    private fun getMentiUidByName(mentiName: String, callback: (String?) -> Unit) {
        userInfoRef.orderByChild("nickname").equalTo(mentiName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val uid = snapshot.key
                        callback(uid)
                        return
                    }
                }
                callback(null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("CoachReserve", "Failed to get Menti UID: ${databaseError.message}")
                callback(null)
            }
        })
    }
}
