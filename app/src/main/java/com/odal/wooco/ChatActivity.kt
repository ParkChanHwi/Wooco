package com.odal.wooco

import Consult
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.odal.wooco.datamodels.Message
import com.odal.wooco.datamodels.UserDataModel
import com.odal.wooco.utils.FirebaseRef

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String
    private var mentiName: String? = null

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var receiverRoom: String
    private var senderRoom: String? = null
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_chat)

        val chatType = intent.getIntExtra("chat_type", 0)
        val otherName: TextView = findViewById(R.id.chat_coach_name)

        val sendBtn = findViewById<Button>(R.id.send_btn)
        val chatInput = findViewById<EditText>(R.id.chat_input)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        val recyclerView = findViewById<RecyclerView>(R.id.menti_chat_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter

        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uid").toString()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        val senderUid = mAuth.currentUser?.uid

        // Retrieve current user's nickname
        val currentUserRef = senderUid?.let { FirebaseDatabase.getInstance().getReference("userInfo").child(it) }
        currentUserRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userDataModel = dataSnapshot.getValue(UserDataModel::class.java)
                    mentiName = userDataModel?.nickname ?: ""

                    if (senderUid != null) {
                        senderRoom = receiverUid + senderUid
                        receiverRoom = senderUid + receiverUid

                        if (chatType == 1) {
                            // 상담 정보 저장
                            val mentiConsult = Consult(
                                mentiName = mentiName!!,
                                coachName = receiverName,
                                coachUid = receiverUid,
                                mentiUid = senderUid,
                                mainID = senderUid,
                                lastMessage = ""
                            )
                            mDbRef.child("MenticonsultRooms").child(senderRoom!!).setValue(mentiConsult)

                            val coachConsult = Consult(
                                mentiName = mentiName!!,
                                coachName = receiverName,
                                coachUid = receiverUid,
                                mainID = receiverUid,
                                mentiUid = senderUid,
                                lastMessage = ""
                            )
                            mDbRef.child("CoachconsultRooms").child(receiverRoom).setValue(coachConsult)
                        } else if (chatType == 0) {
                            // 수업 정보 저장
                            val mentiClass = Consult(
                                mentiName = mentiName!!,
                                coachName = receiverName,
                                coachUid = receiverUid,
                                mentiUid = senderUid,
                                mainID = senderUid,
                                lastMessage = ""
                            )
                            mDbRef.child("MenticlassRooms").child(senderRoom!!).setValue(mentiClass)

                            val coachClass = Consult(
                                mentiName = mentiName!!,
                                coachName = receiverName,
                                coachUid = receiverUid,
                                mainID = receiverUid,
                                mentiUid = senderUid,
                                lastMessage = ""
                            )
                            mDbRef.child("CoachclassRooms").child(receiverRoom).setValue(coachClass)
                        }

                        otherName.text = receiverName

                        val arrowImageView: ImageView = findViewById(R.id.arrow_3)
                        arrowImageView.setOnClickListener {
                            val intent = Intent(this@ChatActivity, CoachList::class.java)
                            startActivity(intent)
                        }
                        Log.d(TAG, "Value of mentiName: $mentiName, senderUid: $senderUid")

                        sendBtn.setOnClickListener {
                            val chatMsg = chatInput.text.toString().trim()
                            if (chatMsg.isEmpty()) {
                                return@setOnClickListener
                            }
                            val senderUid = mAuth.currentUser?.uid
                            if (senderUid != null && mentiName != null && senderRoom != null) {
                                val message = Message(chatMsg, senderUid)

                                val messageRef = if (chatType == 1) FirebaseRef.consultRef else FirebaseRef.classRef
                                val messagePath = if (chatType == 1) "consults" else "classes"

                                messageRef.child(senderRoom!!).child("messages").push().setValue(message)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "Message added to Firebase.")
                                        mDbRef.child(messagePath).child(senderRoom!!).child("lastMessage").setValue(chatMsg)

                                        messageRef.child(receiverRoom).child("messages").push().setValue(message)
                                            .addOnSuccessListener {
                                                Log.d(TAG, "Message added to Firebase.")
                                                mDbRef.child(messagePath).child(receiverRoom).child("lastMessage").setValue(chatMsg)
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e(TAG, "Error adding message to Firebase.", e)
                                            }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(TAG, "Error adding message to Firebase.", e)
                                    }

                                chatInput.setText("")
                            }
                        }

                        val messagePath = if (chatType == 1) "consults" else "classes"
                        mDbRef.child(messagePath).child(senderRoom!!).child("messages")
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
                } else {
                    Log.e(TAG, "Current user data does not exist in the database.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Failed to read current user data.", databaseError.toException())
            }
        })
    }

    companion object {
        private const val TAG = "ChatActivity"
    }
}
