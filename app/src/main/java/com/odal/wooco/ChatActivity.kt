package com.odal.wooco

import Consult
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
                        val consult = Consult(
                            mentiName = mentiName!!,
                            coachName = receiverName,
                            coachUid = receiverUid,  // Add this field
                            mentiUid = senderUid,
                            mainID = senderUid,
                            lastMessage = ""
                        )

                        val consult2 = Consult(
                            mentiName = mentiName!!,
                            coachName = receiverName,
                            coachUid = receiverUid,
                            mainID = receiverUid,// Add this field
                            mentiUid = senderUid,
                            lastMessage = ""
                        )

                        senderRoom = receiverUid + senderUid
                        receiverRoom = senderUid + receiverUid
                        mDbRef.child("MenticonsultRooms").child(senderRoom!!).setValue(consult)
                        mDbRef.child("CoachconsultRooms").child(receiverRoom).setValue(consult2)

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
                            if (senderUid != null && chatType == 1 && mentiName != null && senderRoom != null) {
                                val message = Message(chatMsg, senderUid)

                                FirebaseRef.consultRef.child(senderRoom!!).child("messages").push().setValue(message)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "chats1 added to Firebase.")
                                        mDbRef.child("MenticonsultRooms").child(senderRoom!!).child("lastMessage").setValue(chatMsg)

                                        FirebaseRef.consultRef.child(receiverRoom).child("messages").push().setValue(message)
                                            .addOnSuccessListener {
                                                Log.d(TAG, "chats2 added to Firebase.")
                                                mDbRef.child("CoachconsultRooms").child(receiverRoom).child("lastMessage").setValue(chatMsg)
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e(TAG, "Error adding chats2 to Firebase.", e)
                                            }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(TAG, "Error adding chats1 to Firebase.", e)
                                    }

                                chatInput.setText("")
                            }

                            else  if (senderUid != null && chatType == 0 && mentiName != null && senderRoom != null) {
                                val message = Message(chatMsg, senderUid)

                                FirebaseRef.classRef.child(senderRoom!!).child("messages").push().setValue(message)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "chats1 added to Firebase.")
                                        mDbRef.child("MenticlassRooms").child(senderRoom!!).child("lastMessage").setValue(chatMsg)

                                        FirebaseRef.classRef.child(receiverRoom).child("messages").push().setValue(message)
                                            .addOnSuccessListener {
                                                Log.d(TAG, "chats2 added to Firebase.")
                                                mDbRef.child("MenticlassRooms").child(receiverRoom).child("lastMessage").setValue(chatMsg)
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e(TAG, "Error adding chats2 to Firebase.", e)
                                            }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(TAG, "Error adding chats1 to Firebase.", e)
                                    }

                                chatInput.setText("")
                            }
                        }

                        if (chatType == 1) {
                            mDbRef.child("consult").child(senderRoom!!).child("messages")
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
                       else if (chatType == 0) {
                            mDbRef.child("class").child(senderRoom!!).child("messages")
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