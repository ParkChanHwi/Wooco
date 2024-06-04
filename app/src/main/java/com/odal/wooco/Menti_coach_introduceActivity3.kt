package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivity3 : AppCompatActivity() {
    //넘어온 데이터 변수에 담기
    private lateinit var receiverName: String
    private lateinit var receiverUid: String
    private lateinit var receiverSchool: String
    private lateinit var receiverInterest: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coach_introduce3)

        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)

        //버튼 동작 변수
        val coachIntro1: TextView = findViewById(R.id.coach_category)
        val coachIntro2: TextView = findViewById(R.id.calss_inform)
        val coachIntro3: TextView = findViewById(R.id.review)
        val appointmentBtn: Button = findViewById(R.id.appointment_button)
        val ArrowImageView: ImageView = findViewById(R.id.ArrowImageView)
        val consultBtn: Button = findViewById(R.id.consult_button)
        //코치정보 qus
        val coachName: TextView = findViewById(R.id.user_class_text2)
        val coachSchool: TextView = findViewById(R.id.user_class_text3)
        val coachInterest: TextView = findViewById(R.id.user_class_text4)
        receiverUid = intent.getStringExtra("uid").toString()
        receiverName = intent.getStringExtra("name").toString()
        receiverSchool = intent.getStringExtra("school").toString()
        receiverInterest = intent.getStringExtra("interest").toString()

        val items = listOf(
            Menti_coach_introduceActivity3Adapter.Item("차우코", "2024-05-31", "감사합니다"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 8", "date8","review"),
            // Add more items as needed
        )
        val adapter = Menti_coach_introduceActivity3Adapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


//        Log.d("UserDisplayName", "receiverName: $receiverUid")
//        Log.d("UserDisplayName", "receiverName: $receiverName")
//        Log.d("UserDisplayName", "receiverName: $receiverSchool")
//        Log.d("UserDisplayName", "receiverName: $receiverInterest")

        coachName.text = receiverName
        coachSchool.text = receiverSchool
        coachInterest.text = receiverInterest

        coachIntro1.setOnClickListener {
            val intent = Intent(this, Menti_coach_introduceActivity::class.java)
            startActivity(intent)
        }


        coachIntro2.setOnClickListener {
            val intent = Intent(this, Menti_coach_introduceActivity2::class.java)
            startActivity(intent)
        }


        coachIntro3.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }



        // 예약하기 버튼에 클릭 리스너를 설정
        appointmentBtn.setOnClickListener {
            // 인텐트를 생성. MentiReserve 액티비티를 목적지로 지정
            val intent = Intent(this, MentiReserve::class.java).apply {
                // 코치 정보를 인텐트에 추가
                putExtra("coach_uid", receiverUid)         // 코치의 고유 식별자(uid)
                Log.d("UserDisplayName", "Current user display ID: $receiverUid")
                putExtra("coach_name", receiverName)       // 코치의 이름
                Log.d("UserDisplayName", "Current user display name: $receiverName")

                putExtra("coach_school", receiverSchool)   // 코치의 학교 또는 회사 정보
                putExtra("coach_interest", receiverInterest) // 코치의 특기 또는 관심사
            }
            // 액티비티를 시작. MentiReserve 액티비티로 이동하면서 코치 정보를 함께 전달.
            startActivity(intent)
        }



        ArrowImageView.setOnClickListener{
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

       //  상담하기 버튼 아직 x
        consultBtn.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
            }
            startActivity(intent)
        }


    }
}