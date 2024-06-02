package com.odal.wooco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Menti_Classlist : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Menti_Classlist_Adapter
    private val coachList = mutableListOf<Coach>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_classlist)

        recyclerView = findViewById(R.id.menti_classlist_recycleView)
        adapter = Menti_Classlist_Adapter(coachList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 코치 데이터 추가
        coachList.add(Coach("코치 이름", "마지막 채팅 내용"))

        // 또 다른 코치 추가
        coachList.add(Coach("다른 코치 이름", "다른 마지막 채팅 내용"))

        // 이후에 필요한만큼 코치를 추가할 수 있습니다.
    }
}