package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.odal.wooco.R

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_chat)

        //넘어온 데이터 변수에 담기
        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("Uid").toString()

        //액션바에 상대방 이름 보여주기
        supportActionBar?.title = receiverName
        val arrowImageView: ImageView = findViewById(R.id.arrow_3)
        arrowImageView.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

    }
}