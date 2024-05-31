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

class Coach_scheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_schedule)
        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)


//        chatBtn.setOnClickListener{
//            val intent = Intent(this, Coach_::class.java)
//            startActivity(intent)
//        }

        calBtn.setOnClickListener{
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }


        profileBtn.setOnClickListener{
            val intent = Intent(this, Coach_mypageActivity::class.java)
            startActivity(intent)
        }




    }
}