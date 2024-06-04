package com.odal.wooco

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.odal.wooco.utils.FirebaseRef

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String

    lateinit var  mAuth: FirebaseAuth
    lateinit var  mDbRef: DatabaseReference
    private lateinit var receiverRoom: String // 받는 대화방
    private lateinit var senderRoom: String // 보낸 대화방



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_chat)
        val otherName: TextView = findViewById(R.id.chat_coach_name)

        // 메세지 전송 버튼
        val sendBtn = findViewById<Button>(R.id.send_btn)
        // 채팅방 변수
        val chat_input = findViewById<EditText>(R.id.chat_input)

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



        val arrowImageView: ImageView = findViewById(R.id.arrow_3)
        arrowImageView.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }



        sendBtn.setOnClickListener {
            val chat_msg = chat_input.text.toString().trim()
            if (chat_msg.isEmpty()) {
                return@setOnClickListener
            }
        if (senderUid != null) {
            // 현재 로그인한 사용자의 UID 가져오기
            val sendId = senderUid

            // 메세지 정보 생성
            val chats = hashMapOf(
                "message" to chat_msg,
                "sendId" to sendId
            )

            // Firebase에 채팅방 정보 추가
            FirebaseRef.chats.child(senderRoom).child("messages").setValue(chats)
                .addOnSuccessListener {
                    // Firebase에 코치 정보 추가 성공
                    Log.d(TAG, "chats1 added to Firebase.")


                    FirebaseRef.chats.child(receiverRoom).child("messages").setValue(chats)
                        .addOnSuccessListener {
                            // Firebase에 코치 정보 추가 성공
                            Log.d(TAG, "chats2 added to Firebase.")

//                    val intent = Intent(this, Coach_myselfActivity::class.java)
//                    intent.putExtra("uid", uid)
//                    intent.putExtra("name", name)
//                    intent.putExtra("school", schoolOrCompany)
//                    intent.putExtra("interest", majorOrPosition)
//                    startActivity(intent)


                    // 추가적인 작업 수행 혹은 화면 이동 등
                }
                .addOnFailureListener { e ->
                    // Firebase에 코치 정보 추가 실패
                    Log.e(TAG, "Error adding chats to Firebase.", e)
                }
             }

        }
    }


    }
}