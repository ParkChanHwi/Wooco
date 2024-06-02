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

class Menti_mypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_mypage)
        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val transferBtn: Button = findViewById(R.id.coach_transfer)

            homeBtn.setOnClickListener{
                val intent = Intent(this, CoachList::class.java)
                startActivity(intent)
            }


            chatBtn.setOnClickListener{
            val intent = Intent(this, Menti_Classlist::class.java)
            startActivity(intent)
            }

            calBtn.setOnClickListener{
            val intent = Intent(this, Menti_scheduleActivity::class.java)
            startActivity(intent)
            }


            profileBtn.setOnClickListener{
                Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
            }


            transferBtn.setOnClickListener{
                val intent = Intent(this, Coach_mypageActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }
