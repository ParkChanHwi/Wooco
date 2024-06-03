package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)



        // 코치 데이터 추가
        coachList.add(Cosult_Coach("학생 이름", "마지막 채팅 내용"))

        // 또 다른 코치 추가
        coachList.add(Cosult_Coach("다른 학생 이름", "다른 마지막 채팅 내용"))

        // 이후에 필요한만큼 코치를 추가할 수 있습니다.

        classlist.setOnClickListener {
            val intent = Intent(this, Menti_Classlist::class.java)
            startActivity(intent)
        }


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
    }
}
