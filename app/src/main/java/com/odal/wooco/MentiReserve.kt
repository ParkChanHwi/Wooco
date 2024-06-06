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
            val userId = currentUser.uid
            userInfoRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val nickname = dataSnapshot.child("nickname").getValue(String::class.java)
                        if (nickname != null) {
                            // 사용자의 닉네임을 가져왔으므로 이를 활용할 수 있습니다.
                            mentiName = nickname // 멘티의 이름 변수에 저장
                            Log.d("UserDisplayName", "User nickname: $mentiName")
                            // 이후의 작업 코드 작성
                            // 예: 데이터베이스에 값을 저장하는 등의 작업
                        } else {
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
        } else {
            Log.e("UserDisplayName", "Current user is null")
        }

        // 현재 사용자의 uid 잘 가져오는지 검토하는 코드
        currentUser?.let { Log.d("UserDisplayName", "Current user display UID: ${it.uid}") }

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
            val datetime = "$day $time" // 날짜와 시간을 합친 문자열 생성
            Toast.makeText(this, "선택한 날짜: $day\n선택한 시간: $time", Toast.LENGTH_LONG).show()

            // Firebase에 코치 정보 추가
            currentUser?.let { user ->
                val mentiUid = user.uid
                val dataMap = HashMap<String, Any>()
                dataMap["menti_uid"] = mentiUid
                dataMap["menti_name"] = mentiName // 멘티의 이름 데이터 추가
                dataMap["coach_receiverUid"] = coach_receiverUid
                dataMap["coach_receiverName"] = coach_receiverName
                dataMap["reserve_time"] = datetime

                // Firebase에서 식별자 생성
                // 중복을 피하기 위해 push() 메서드를 사용하여 새로운 노드 추가
                val reserveInfoRef = database.getReference("reserveInfo").push()
                val reserveId = reserveInfoRef.key

                // 생성된 식별자를 사용하여 예약 정보 추가
                if (reserveId != null) {
                    reserveInfoRef.setValue(dataMap).addOnSuccessListener {
                        // 데이터 전송이 성공한 경우
                        // 추가 작업이 필요하다면 여기에 작성하세요
                        val intent = Intent(this, Menti_scheduleActivity::class.java)
                        intent.putExtra("reserveData", dataMap) // dataMap을 reserveData라는 이름으로 전달
                        startActivity(intent)
                    }.addOnFailureListener {
                        // 데이터 전송이 실패한 경우
                        // 실패 처리 작업을 여기에 작성하세요
                        Toast.makeText(this, "Failed to save data to Firebase", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // 식별자 생성에 실패한 경우
                    Log.e("MentiReserve", "Failed to generate reservation ID")
                    Toast.makeText(this, "Failed to generate reservation ID", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

}
