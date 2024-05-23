package com.odal.wooco

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView


class CoachList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_list)

        val universityTextView: TextView = findViewById(R.id.university_text_view)
        universityTextView.setOnClickListener {
            Log.d("MainActivity", "텍스트 뷰 클릭됨")
            val bottomSheetFragment = MyBottomSheetDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}