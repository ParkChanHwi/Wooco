package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.odal.wooco.R

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String

    lateinit var  mAuth: FirebaseAuth
    lateinit var  mDbRef: DatabaseReference
    private lateinit var receiverRoom: String // 받는 대화방
    private lateinit var senderRoom: String // 보낸 대화방

    // 채팅방 변수
    private lateinit var nameEditText: EditText
    private lateinit var schoolOrCompanyEditText: EditText
    private lateinit var majorOrPositionEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_chat)
        val otherName: TextView = findViewById(R.id.chat_coach_name)

        // 메세지 전송 버튼
        val sendBtn = findViewById<Button>(R.id.send_btn)
        //넘어온 데이터 변수에 담기
        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uid").toString()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        val senderUid = mAuth.currentUser?.uid

        senderRoom = receiverUid + senderUid // 보낸이 방의 키 값
        receiverRoom = senderUid + receiverUid // 받는이 방의 키 값

        //액션바에 상대방 이름 보여주기
        otherName.text = receiverName

        sendBtn.setOnClickListener {

        }


        val arrowImageView: ImageView = findViewById(R.id.arrow_3)
        arrowImageView.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

    }
}