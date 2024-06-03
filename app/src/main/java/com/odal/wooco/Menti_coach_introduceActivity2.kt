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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coach_introduce2)

        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)
        val coachIntro1: TextView = findViewById(R.id.coach_category)
        val coachIntro2: TextView = findViewById(R.id.calss_inform)
        val coachIntro3: TextView = findViewById(R.id.review)
        val appointmentBtn: Button = findViewById(R.id.appointment_button)
        val ArrowImageView: ImageView = findViewById(R.id.ArrowImageView)
        val consultBtn: Button = findViewById(R.id.consult_button)


        val items = listOf(
            Menti_coach_introduceActivity2Adapter.Item("한 줄 소개", "수업 가능 과목", "차별점","수업 방식")

            // Add more items as needed

        )
        val adapter = Menti_coach_introduceActivity2Adapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

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



        appointmentBtn.setOnClickListener {
            val intent = Intent(this, MentiReserve::class.java)
            startActivity(intent)
        }

        ArrowImageView.setOnClickListener{
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        // 상담하기 버튼 아직 x
//        consultBtn.setOnClickListener {
//            val intent = Intent(this, ::class.java)
//            startActivity(intent)
//        }

    }
}