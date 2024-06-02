package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val consultlist: TextView = findViewById(R.id.consultList)

        // 코치 데이터 추가
        coachList.add(Coach("코치 이름", "마지막 채팅 내용"))

        // 또 다른 코치 추가
        coachList.add(Coach("다른 코치 이름", "다른 마지막 채팅 내용"))

        // 이후에 필요한만큼 코치를 추가할 수 있습니다.


        homeBtn.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        // 멘티 수업목록 - 클래스리스트
        chatBtn.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        //멘티 나의 일정
        calBtn.setOnClickListener {
            val intent = Intent(this, Menti_scheduleActivity::class.java)
            startActivity(intent)
        }

        //멘티 마이페이지
        profileBtn.setOnClickListener {
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }

        consultlist.setOnClickListener {
            val intent = Intent(this, Menti_Consultinglist::class.java)
            startActivity(intent)
        }

    }
}