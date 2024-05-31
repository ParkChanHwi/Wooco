package com.odal.wooco

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.utils.FirebaseRef

class CoachList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coachlist)

        val homeBtn:ImageView = findViewById(R.id.material_sy)
        val chatBtn:ImageView = findViewById(R.id.chat_1)
        val calBtn:ImageView = findViewById(R.id.uiw_date)
        val profileBtn:ImageView = findViewById(R.id.group_513866)


        findViewById<Button>(R.id.kategori1).setOnClickListener {
            val bottomSheet = BottomSheet1()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori2).setOnClickListener {
            val bottomSheet = BottomSheet2()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori3).setOnClickListener {
            val bottomSheet = BottomSheet3()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori4).setOnClickListener {
            val bottomSheet = BottomSheet4()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori5).setOnClickListener {
            val bottomSheet = BottomSheet5()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori6).setOnClickListener {
            val bottomSheet = BottomSheet6()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        val recyclerView: RecyclerView = findViewById(R.id.coachlist_recycleView)

        val items = listOf(
            Coach_Adapter.Item("차우코", "강원대 00학과", "자격증 - 기사/기능사, 진로", "4.9"),
            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
            Coach_Adapter.Item("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
            // Add more items as needed
        )

        val adapter = Coach_Adapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



        homeBtn.setOnClickListener{
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, CoachList::class.java)
//            startActivity(intent)
        }

        // 아직 채팅방 형성 x
//        chatBtn.setOnClickListener{
//            val intent = Intent(this, MentiChat::class.java)
//            startActivity(intent)
//        }

        //멘티 나의 일정
        calBtn.setOnClickListener{
            val intent = Intent(this, Menti_scheduleActivity::class.java)
            startActivity(intent)
        }


        //멘티 마이페이지
        profileBtn.setOnClickListener{
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }



    }



}