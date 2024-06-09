package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivity2 : AppCompatActivity() {
    private lateinit var receiverName: String
    private lateinit var receiverUid: String
    private lateinit var receiverSchool: String
    private lateinit var receiverInterest: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coach_introduce2)

        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)
        val coachIntro1: TextView = findViewById(R.id.coach_category)
        val coachIntro2: TextView = findViewById(R.id.calss_inform)
        val coachIntro3: TextView = findViewById(R.id.review)

        // ArrowImageView 클릭 리스너
        val ArrowImageView: ImageView = findViewById(R.id.ArrowImageView)
        ArrowImageView.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        val coachName: TextView = findViewById(R.id.user_class_text2)
        val coachSchool: TextView = findViewById(R.id.user_class_text3)
        val coachInterest: TextView = findViewById(R.id.user_class_text4)

        receiverUid = intent.getStringExtra("uid").toString()
        receiverName = intent.getStringExtra("name").toString()
        receiverSchool = intent.getStringExtra("school").toString()
        receiverInterest = intent.getStringExtra("interest").toString()

        coachName.text = receiverName
        coachSchool.text = receiverSchool
        coachInterest.text = receiverInterest


        // 예약하기 버튼에 클릭 리스너를 설정
        val appointmentBtn: Button = findViewById(R.id.appointment_button)
        appointmentBtn.setOnClickListener {
            // 인텐트를 생성. Menti_Reserve1 액티비티를 목적지로 지정
            val intent = Intent(this, Menti_Reserve1::class.  java).apply {
                // 코치 정보를 인텐트에 추가
                putExtra("coach_uid", receiverUid)         // 코치의 고유 식별자(uid)
                putExtra("coach_name", receiverName)       // 코치의 이름
                putExtra("coach_school", receiverSchool)   // 코치의 학교 또는 회사 정보
                putExtra("coach_interest", receiverInterest) // 코치의 특기 또는 관심사
            }
            // 액티비티를 시작. MentiReserve 액티비티로 이동하면서 코치 정보를 함께 전달.
            startActivity(intent)
        }

        // 상담하기 버튼 클릭 리스너
        val consultBtn: Button = findViewById(R.id.consult_button)
        consultBtn.setOnClickListener {
            // 상담 목록 업데이트 함수 호출
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("chat_type", 1)  // 1 for consultation
            }
            startActivity(intent)
        }


        val items = listOf(
            Menti_coach_introduceActivity2Adapter.Item("한 줄 소개", "수업 가능 과목", "차별점","수업 방식")

            // Add more items as needed

        )
        val adapter = Menti_coach_introduceActivity2Adapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        coachIntro1.setOnClickListener {
            val intent1 = Intent(this, Menti_coach_introduceActivity::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("school", receiverSchool)
                putExtra("interest", receiverInterest)
            }
            startActivity(intent1)
        }


        coachIntro2.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }


        coachIntro3.setOnClickListener {
            val intent3 = Intent(this, Menti_coach_introduceActivity3::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("school", receiverSchool)
                putExtra("interest", receiverInterest)
            }
            startActivity(intent3)
        }
    }
}