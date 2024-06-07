package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Menti_Reserve1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_reserve1)

        val reserveButton: Button = findViewById(R.id.reserve_button)
        reserveButton.setOnClickListener {
            val category = intent.getStringExtra("category")
            Log.d("Reserve1", "Selected category: $category")

            val reserveId = intent.getStringExtra("reserveId")
            val reserveTime = intent.getStringExtra("reserve_time")
            val coachName = intent.getStringExtra("coach_receiverName")
            val mentiName = intent.getStringExtra("menti_name")


            // Now you can use these variables to populate your UI or perform other tasks

            val confirmationTextView: TextView = findViewById(R.id.reserve_selected_categori)
            confirmationTextView.text = "예약 ID: $reserveId\n예약 시간: $reserveTime\n코치 이름: $coachName\n멘티 이름: $mentiName\n카테고리: $category"
        }
    }
}
