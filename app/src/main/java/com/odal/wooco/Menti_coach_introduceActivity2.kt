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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
            val intent = Intent(this, Menti_Reserve1::class.java).apply {
                putExtra("coach_uid", receiverUid)
                putExtra("coach_name", receiverName)
                putExtra("coach_school", receiverSchool)
                putExtra("coach_interest", receiverInterest)
            }
            startActivity(intent)
        }

        // 상담하기 버튼 클릭 리스너
        val consultBtn: Button = findViewById(R.id.consult_button)
        consultBtn.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("chat_type", 1)  // 1 for consultation
            }
            startActivity(intent)
        }

        // Firebase에서 데이터 가져오기
        getMyselfInfoFromDatabase(receiverUid)

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

    private fun getMyselfInfoFromDatabase(uid: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("coachInfo").child(uid)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val myself1 = dataSnapshot.child("myself1").getValue(String::class.java) ?: ""
                val myself2 = dataSnapshot.child("myself2").getValue(String::class.java) ?: ""
                val myself3 = dataSnapshot.child("myself3").getValue(String::class.java) ?: ""
                val myself4 = dataSnapshot.child("myself4").getValue(String::class.java) ?: ""

                val items = listOf(
                    Menti_coach_introduceActivity2Adapter.Item(myself1, myself2, myself3, myself4)
                )

                updateMyselfInfoUI(items)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Menti_coach_introduceActivity2, "Error fetching data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateMyselfInfoUI(items: List<Menti_coach_introduceActivity2Adapter.Item>) {
        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)
        val adapter = Menti_coach_introduceActivity2Adapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
