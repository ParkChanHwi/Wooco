package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CoachList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_myself)

//        findViewById<Button>(R.id.kategori1).setOnClickListener {
//            val bottomSheet = BottomSheet1()
//            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
//        }
//        findViewById<Button>(R.id.kategori2).setOnClickListener {
//            val bottomSheet = BottomSheet2()
//            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
//        }
//        findViewById<Button>(R.id.kategori3).setOnClickListener {
//            val bottomSheet = BottomSheet3()
//            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
//        }
//        findViewById<Button>(R.id.kategori4).setOnClickListener {
//            val bottomSheet = BottomSheet4()
//            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
//        }
//        findViewById<Button>(R.id.kategori5).setOnClickListener {
//            val bottomSheet = BottomSheet5()
//            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
//        }
//        findViewById<Button>(R.id.kategori6).setOnClickListener {
//            val bottomSheet = BottomSheet6()
//            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
//        }
//
//        val recyclerView: RecyclerView = findViewById(R.id.coachlist_recycleView)
//
//        val items = listOf(
//            Coach_Adapter.Item("차우코", "강원대 00학과", "자격증 - 기사/기능사, 진로", "4.9"),
//            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
//            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
//            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
//            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
//            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
//            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
//            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
//            // Add more items as needed
//        )
//
//        val adapter = Coach_Adapter(items)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
    }
}