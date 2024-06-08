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
            val currentTime = Calendar.getInstance().time
            val day = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
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
            val time = String.format("%02d:%02d", hour, minute)
            val datetime = "$day $time"

            // 예약 날짜와 시간을 합쳐서 Date 객체로 변환
            val selectedDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(datetime)

            // 현재 시간과 선택한 시간을 비교하여 경고창 표시
            if (selectedDateTime != null && selectedDateTime.before(currentTime)) {
                Toast.makeText(this, "올바른 날짜와 시간으로 예약해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            currentUser?.let { user ->
                val mentiUid = user.uid
                val dataMap = HashMap<String, Any>()
                dataMap["coach_receiverUid"] = coach_receiverUid
                dataMap["coach_receiverName"] = coach_receiverName
                dataMap["reserve_time"] = datetime

                if (isChange && reserveId != null) {
                    // 예약 정보 업데이트
                    reserveInfoRef.child(reserveId).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val existingMentiUid = dataSnapshot.child("menti_uid").getValue(String::class.java)
                                existingMentiUid?.let { mentiUid ->
                                    // Retrieve the menti's name based on the existing mentiUid
                                    userInfoRef.child(mentiUid).addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(userSnapshot: DataSnapshot) {
                                            if (userSnapshot.exists()) {
                                                val existingMentiName = userSnapshot.child("nickname").getValue(String::class.java)
                                                existingMentiName?.let {
                                                    dataMap["menti_uid"] = mentiUid
                                                    dataMap["menti_name"] = it // Update menti name
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
                                                } ?: run {
                                                    Log.e("MentiReserve", "Menti name not found for UID: $mentiUid")
                                                    Toast.makeText(this@CoachReserve, "멘티 이름을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }

                                        override fun onCancelled(userError: DatabaseError) {
                                            Log.e("MentiReserve", "Failed to read menti user info: ${userError.message}")
                                            Toast.makeText(this@CoachReserve, "멘티 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                } ?: run {
                                    Log.e("MentiReserve", "Menti UID not found in reservation")
                                    Toast.makeText(this@CoachReserve, "예약에 멘티 UID가 없습니다.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Log.e("MentiReserve", "Reservation not found for ID: $reserveId")
                                Toast.makeText(this@CoachReserve, "예약 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("MentiReserve", "Failed to read reservation info: ${databaseError.message}")
                            Toast.makeText(this@CoachReserve, "예약 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    // 새로운 예약 추가
                    val newReserveId = reserveInfoRef.push().key
                    if (newReserveId != null) {
                        dataMap["menti_uid"] = mentiUid // 현재 사용자 ID 사용
                        dataMap["menti_name"] = mentiName
                        dataMap["reserveId"] = newReserveId // reserveId를 추가
                        reserveInfoRef.child(newReserveId).setValue(dataMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "예약이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, Menti_scheduleActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Firebase에 데이터를 저장하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Log.e("MentiReserve", "Failed to generate reservation ID")
                        Toast.makeText(this, "예약 ID를 생성하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
