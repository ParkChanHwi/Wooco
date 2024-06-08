package com.odal.wooco

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.Message
import com.odal.wooco.utils.FirebaseRef
import kotlin.math.absoluteValue

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String

    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference
    private lateinit var receiverRoom: String // 받는 대화방
    private lateinit var senderRoom: String // 보낸 대화방
    // message 배열 생성
    private lateinit var messageList: ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_chat) // 채팅창 레이아웃
        // intent로 넘어온 데이터 상담하기 버튼을 눌러서 넘어온 경우 1, 기본값 0, 수업
        val chatType = intent.getIntExtra("chat_type", 0)
        val otherName: TextView = findViewById(R.id.chat_coach_name)  // 코치 이름이 아닌 채팅 상대방의 이름

        // 메세지 전송 버튼
        val sendBtn = findViewById<Button>(R.id.send_btn) // 메세지 전송 버튼
        // 채팅방 변수
        val chatInput = findViewById<EditText>(R.id.chat_input) // 채팅 입력 받는 값
        // 초기화
        messageList = ArrayList()
        val messageAdapter: MessageAdapter = MessageAdapter(this, messageList)

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.menti_chat_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter

        // 넘어온 데이터 변수에 담기
        receiverName = intent.getStringExtra("name").toString() // 코치 이름 (상대방)
        receiverUid = intent.getStringExtra("uid").toString() //코치의 UID (상대방)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        val senderUid = mAuth.currentUser?.uid // 현재 유저의 uid

        senderRoom = receiverUid + senderUid // 보낸이 방의 키 값
        receiverRoom = senderUid + receiverUid // 받는이 방의 키 값

        // 액션바에 상대방 이름 보여주기
        otherName.text = receiverName

        val arrowImageView: ImageView = findViewById(R.id.arrow_3)
        arrowImageView.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        sendBtn.setOnClickListener {
            val chatMsg = chatInput.text.toString().trim()
            if (chatMsg.isEmpty()) {
                return@setOnClickListener
            }
            val senderUid = mAuth.currentUser?.uid
            if (senderUid != null && chatType == 1) {
                val message = Message(chatMsg, senderUid)

                // Firebase에 채팅방 정보 추가
                FirebaseRef.consultRef.child("consult").child(senderRoom).child(receiverUid).setValue(message)
                    .addOnSuccessListener {
                        // Firebase에 보낸이 방의 메세지 추가 성공
                        Log.d(TAG, "chats1 added to Firebase.")

                        FirebaseRef.consultRef.child("consult").child(receiverRoom).child(senderUid).setValue(message)
                            .addOnSuccessListener {
                                // Firebase에 받는이 방의 메세지 추가 성공
                                Log.d(TAG, "chats2 added to Firebase.")
                            }
                            .addOnFailureListener { e ->
                                // Firebase에 받는이 방의 메세지 추가 실패
                                Log.e(TAG, "Error adding chats2 to Firebase.", e)
                            }
                    }
                    .addOnFailureListener { e ->
                        // Firebase에 보낸이 방의 메세지 추가 실패
                        Log.e(TAG, "Error adding chats1 to Firebase.", e)
                    }

                // 메시지 전송 후 입력 필드 초기화
                chatInput.setText("")
            }
        }

        // 메시지 가져오기 이것도 상담방이랑 수업 리스트 나눠야함

        if(chatType == 1){
            mDbRef.child("consult").child(senderRoom).child(receiverUid).child("messages")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // 데이터 변경을 처리하고 UI를 업데이트합니다.
                        messageList.clear()

                        for (postSnapshot in snapshot.children) {
                            val message = postSnapshot.getValue(Message::class.java)
                            if (message != null) {
                                messageList.add(message)
                            }
                        }
                        // RecyclerView 업데이트
                        messageAdapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // 데이터 읽기가 취소되거나 실패했을 때 처리
                        Log.e(TAG, "Failed to read messages", error.toException())
                    }
                })
        }

    }
}


