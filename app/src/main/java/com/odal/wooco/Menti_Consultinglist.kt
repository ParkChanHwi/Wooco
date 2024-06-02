package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Menti_Consultinglist : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Menti_Consultinglist_Adater
    private val coachList = mutableListOf<Cosult_Coach>()

    private lateinit var classlist: TextView // 클래스 멤버 변수로 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_consultinglist)

        // findViewById 호출 위치 변경
        classlist = findViewById(R.id.classList)
        recyclerView = findViewById(R.id.menti_consulting_recycleView)
        adapter = Menti_Consultinglist_Adater(coachList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 코치 데이터 추가
        coachList.add(Cosult_Coach("학생 이름", "마지막 채팅 내용"))

        // 또 다른 코치 추가
        coachList.add(Cosult_Coach("다른 학생 이름", "다른 마지막 채팅 내용"))

        // 이후에 필요한만큼 코치를 추가할 수 있습니다.

        classlist.setOnClickListener {
            val intent = Intent(this, Menti_Classlist::class.java)
            startActivity(intent)
        }
    }
}
