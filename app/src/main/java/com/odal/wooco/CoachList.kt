package com.odal.wooco

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CoachList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_list)

        findViewById<Button>(R.id.kategori1).setOnClickListener {
            Log.d("MainActivity", "TextView clicked")
            val bottomSheet = MyBottomSheetDialogFragment()
            bottomSheet.show(supportFragmentManager, "MyBottomSheetDialogFragment")
        }
    }
}
// 아래 bottom sheet 띄우려다가 실패한 코드