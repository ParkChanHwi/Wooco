package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Coach_mypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_mypage)

        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val transferBtn: Button = findViewById(R.id.menti_transfer)
        val coachBtn: Button = findViewById(R.id.coach_register)



        chatBtn.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        calBtn.setOnClickListener{
            val intent = Intent(this, Coach_scheduleActivity::class.java)
            startActivity(intent)
        }


        profileBtn.setOnClickListener{
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }


        transferBtn.setOnClickListener{
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
            finish()
        }


        coachBtn.setOnClickListener{
            val intent = Intent(this, Coach_registerActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}