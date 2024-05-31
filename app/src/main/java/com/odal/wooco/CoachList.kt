package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CoachList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coachlist)

        // 대학교 카테고리
        findViewById<Button>(R.id.kategori1).setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        // 진로&기타고민 카테고리
        findViewById<Button>(R.id.kategori2).setOnClickListener {
            val bottomSheet = MyBottomSheetDialogFragment2()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        // 전공&과제 카테고리
        findViewById<Button>(R.id.kategori3).setOnClickListener {
            val bottomSheet = MyBottomSheetDialogFragment3()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        // 면접 카테고리
        findViewById<Button>(R.id.kategori4).setOnClickListener {
            val bottomSheet = MyBottomSheetDialogFragment4()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        // 자격증 카테고리
        findViewById<Button>(R.id.kategori6).setOnClickListener {
            val bottomSheet = MyBottomSheetDialogFragment5()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
}