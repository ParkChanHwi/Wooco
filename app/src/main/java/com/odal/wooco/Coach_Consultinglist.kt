package com.odal.wooco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Coach_Consultinglist : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Coach_Cunsultinglist_Adapter
    private val mentiList = mutableListOf<Consult_Menti>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_consultinglist)

        recyclerView = findViewById(R.id.coach_consultinglist_recycleView)
        adapter = Coach_Cunsultinglist_Adapter(mentiList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 코치 데이터 추가
        mentiList.add(Consult_Menti("학생 이름", "마지막 채팅 내용"))

        // 또 다른 코치 추가
        mentiList.add(Consult_Menti("다른 학생 이름", "멘티컨설트리스트"))

        // 이후에 필요한만큼 코치를 추가할 수 있습니다.
    }
}