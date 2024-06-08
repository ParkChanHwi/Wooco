package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Coach_Classlist : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Coach_Classlist_Adapter
    private val mentiList = mutableListOf<Menti>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_classlist)

        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val consultTsf : TextView = findViewById(R.id.consult_button)
        val consult_button : TextView = findViewById(R.id.consult_button)

        recyclerView = findViewById(R.id.coach_classlist_recycleView)
        adapter = Coach_Classlist_Adapter(mentiList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 코치 데이터 추가
        mentiList.add(Menti("코치 이름", "마지막 채팅 내용"))

        // 또 다른 코치 추가
        mentiList.add(Menti("다른 코치 이름", "코치클래스리스트"))

        // 이후에 필요한만큼 코치를 추가할 수 있습니다.

        consult_button.setOnClickListener {
            val intent = Intent(this, Coach_Consultinglist::class.java)
            startActivity(intent)
        }

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

        consultTsf.setOnClickListener{
            val intent = Intent(this, Coach_Consultinglist::class.java)
            startActivity(intent)
        }

    }
}