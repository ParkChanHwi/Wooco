package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val classTsf : TextView = findViewById(R.id.class_button)

        recyclerView = findViewById(R.id.coach_consultinglist_recycleView)
        adapter = Coach_Cunsultinglist_Adapter(mentiList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 코치 데이터 추가
        mentiList.add(Consult_Menti("학생 이름", "마지막 채팅 내용"))

        // 또 다른 코치 추가
        mentiList.add(Consult_Menti("다른 학생 이름", "멘티컨설트리스트"))

        // 이후에 필요한만큼 코치를 추가할 수 있습니다.


        chatBtn.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        //코치 나의 일정
        calBtn.setOnClickListener {
            val intent = Intent(this, Coach_scheduleActivity::class.java)
            startActivity(intent)
        }

        //코치 마이페이지
        profileBtn.setOnClickListener {
            val intent = Intent(this, Coach_mypageActivity::class.java)
            startActivity(intent)
        }

        classTsf.setOnClickListener{
            val intent = Intent(this, Coach_Consultinglist::class.java)
            startActivity(intent)
        }
    }
}