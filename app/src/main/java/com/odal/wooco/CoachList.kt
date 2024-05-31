package com.odal.wooco

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
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }



        homeBtn.setOnClickListener{
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, CoachList::class.java)
//            startActivity(intent)
        }

        // 아직 채팅방 형성 x
//        chatBtn.setOnClickListener{
//            val intent = Intent(this, coachChat::class.java)
//            startActivity(intent)
//        }

        calBtn.setOnClickListener{
            val intent = Intent(this, Coach_scheduleActivity::class.java)
            startActivity(intent)
        }

        // 아직 마이페이지 형성 x
//        profileBtn.setOnClickListener{
//            val intent = Intent(this, Coach_profileActivity::class.java)
//            startActivity(intent)
//        }



    }
}