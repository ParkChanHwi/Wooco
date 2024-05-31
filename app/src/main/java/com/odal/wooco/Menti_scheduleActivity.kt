package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.utils.FirebaseRef

class Menti_scheduleActivity : AppCompatActivity() {
    private val TAG = "Menti_scheduleActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_schedule)


        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)



        homeBtn.setOnClickListener{
            val intent = Intent(this, CoachList::class.java)
           startActivity(intent)
        }

        // 아직 채팅방 형성 x
//        chatBtn.setOnClickListener{
//            val intent = Intent(this, Mebti_chatActivity::class.java)
//            startActivity(intent)
//        }

        calBtn.setOnClickListener{
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, Menti_scheduleActivity::class.java)
//            startActivity(intent)
        }


        profileBtn.setOnClickListener{
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }


    }



}