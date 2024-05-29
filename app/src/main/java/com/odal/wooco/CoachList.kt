package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity





class CoachList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_coachlist)
        findViewById<Button>(R.id.kategori1).setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
}

