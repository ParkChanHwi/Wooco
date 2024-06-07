package com.odal.wooco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Coach_menti_request : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Coach_menti_request_Adapter
    private val RequestList = mutableListOf<Request_Menti>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_menti_request)

        recyclerView = findViewById(R.id.coach_request_recylerView)
        adapter = Coach_menti_request_Adapter(RequestList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 코치 데이터 추가
        RequestList.add(Request_Menti("요청 학생 이름", "요청 카테고리","오늘"))

        // 또 다른 코치 추가
        RequestList.add(Request_Menti("다른 학생 이름", "다른 요청 카테고리","내일"))

        // 이후에 필요한만큼 코치를 추가할 수 있습니다.
    }
}