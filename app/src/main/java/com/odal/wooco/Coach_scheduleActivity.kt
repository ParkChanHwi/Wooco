package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Coach_scheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_schedule)

        val recyclerView: RecyclerView = findViewById(R.id.coach_schedule_recycler_view)

        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)

        val items = listOf(
            CoachScheduleActivityAdapter.Item("차우코", "2024-05-31"),
            CoachScheduleActivityAdapter.Item("별명 2", "date2"),
            CoachScheduleActivityAdapter.Item("별명 3", "date3"),
            CoachScheduleActivityAdapter.Item("별명 4", "date4"),
            CoachScheduleActivityAdapter.Item("별명 5", "date5"),
            CoachScheduleActivityAdapter.Item("별명 6", "date6"),
            CoachScheduleActivityAdapter.Item("별명 7", "date7"),
           CoachScheduleActivityAdapter.Item("별명 8", "date8"),
            // Add more items as needed

        )
        val adapter = CoachScheduleActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        chatBtn.setOnClickListener {
            val intent = Intent(this, Coach_Classlist::class.java)
            startActivity(intent)
        }

        //코치 나의 일정
        calBtn.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        //코치 마이페이지
        profileBtn.setOnClickListener {
            val intent = Intent(this, Coach_mypageActivity::class.java)
            startActivity(intent)
        }

    }
}