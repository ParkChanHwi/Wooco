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

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String
    private lateinit var chatKey: String
    private var chatType: Int = 0

    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference
    private lateinit var receiverRoom: String
    private lateinit var senderRoom: String

    private lateinit var messageList: ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_chat)
        val otherName: TextView = findViewById(R.id.chat_coach_name)

        val sendBtn = findViewById<Button>(R.id.send_btn)
        val chatInput = findViewById<EditText>(R.id.chat_input)
        messageList = ArrayList()
        val messageAdapter: MessageAdapter = MessageAdapter(this, messageList)

        val recyclerView = findViewById<RecyclerView>(R.id.menti_chat_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter

        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uid").toString()
        chatKey = intent.getStringExtra("chat_key").toString()
        chatType = intent.getIntExtra("chat_type", 0)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        val senderUid = mAuth.currentUser?.uid
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

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
            if (senderUid != null) {
                val message = Message(chatMsg, senderUid)

                val chatPath = if (chatType == 1) "consult" else "class"

                // Firebase에 채팅방 정보 추가
                FirebaseRef.chats.child(chatPath).child(senderRoom).child("messages").push().setValue(message)
                    .addOnSuccessListener {
                        Log.d(TAG, "Message added to senderRoom in Firebase.")

                        FirebaseRef.chats.child(chatPath).child(receiverRoom).child("messages").push().setValue(message)
                            .addOnSuccessListener {
                                Log.d(TAG, "Message added to receiverRoom in Firebase.")
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Error adding message to receiverRoom in Firebase.", e)
                            }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error adding message to senderRoom in Firebase.", e)
                    }

                chatInput.setText("")
            }
        }

        val chatPath = if (chatType == 1) "consult" else "class"
        mDbRef.child("chats").child(chatPath).child(senderRoom).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        if (message != null) {
                            messageList.add(message)
                        }
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Failed to read messages", error.toException())
                }
            })
    }
}
