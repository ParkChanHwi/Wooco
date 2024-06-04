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
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Locale

class MentiReserve : AppCompatActivity() {
    private lateinit var coach_receiverUid: String // Menti_coach_introduceActivity에서 받아온 코치 UID정보
    private lateinit var coach_receiverName: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_reserve)

        // auth initialize
        auth = FirebaseAuth.getInstance()

        // Firebase Realtime Database에서 UserInfo 이름 가져오기
        val database = FirebaseDatabase.getInstance()
        val userInfoRef = database.getReference("userInfo")

        // 현재 로그인한 사용자의 정보를 가져오기
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // 현재 사용자의 uid 잘 가져오는지 검토하는 코드
            Log.d("UserDisplayName", "Current user display UID: ${currentUser.uid}")
        }

        val calendarView: MaterialCalendarView = findViewById(R.id.calendar_view)
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val reserveButton: Button = findViewById(R.id.reserve_button)
        
        // 뒤로가기 버튼 활성화 (6/4(화))
        val arrowButton: ImageView = findViewById(R.id.arrow_3)
        arrowButton.setOnClickListener {
            onBackPressed()
        }

        coach_receiverUid = intent.getStringExtra("coach_uid").toString()
        coach_receiverName = intent.getStringExtra("coach_name").toString()


        reserveButton.setOnClickListener {
            val selectedDate = calendarView.selectedDate?.date ?: Calendar.getInstance().time
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

            Toast.makeText(this, "선택한 날짜: $day\n선택한 시간: $time", Toast.LENGTH_LONG).show()

            // Firebase에 코치 정보 추가
            if (currentUser != null) {
                val menti_uid = currentUser.uid
                val menti_name = currentUser.uid // 멘티 이름 가져오는 걸로 코드 수정 필요

                val dataMap = HashMap<String, Any>()
                dataMap["menti_uid"] = menti_uid
                dataMap["menti_name"] = menti_name
                dataMap["coach_receiverUid"] = coach_receiverUid
                dataMap["coach_receiverName"] = coach_receiverName
                dataMap["reserve_time"] = time


                val reserveInfoRef = database.getReference("reserveInfo").child(menti_uid)
                reserveInfoRef.setValue(dataMap).addOnSuccessListener {
                    // 데이터 전송이 성공한 경우
                    // 추가 작업이 필요하다면 여기에 작성하세요
                    val intent = Intent(this, Menti_scheduleActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    // 데이터 전송이 실패한 경우
                    // 실패 처리 작업을 여기에 작성하세요
                    Toast.makeText(this, "Failed to save data to Firebase", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}
