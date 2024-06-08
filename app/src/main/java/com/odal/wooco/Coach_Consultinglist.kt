package com.odal.wooco

import Coach_Consultinglist_Adapter
import Consult
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Coach_Consultinglist : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Coach_Consultinglist_Adapter
    private val consultList = mutableListOf<Consult>()

    private lateinit var databaseRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_consultinglist)

        recyclerView = findViewById(R.id.coach_consultinglist_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Coach_Consultinglist_Adapter(consultList.map { consult ->
            Consult(
                mentiName = consult.mentiName,
                coachName = consult.coachName,
                coachUid = consult.coachUid,
                mentiUid = consult.mentiUid,
                mainID = consult.coachUid,
                lastMessage = consult.lastMessage
            )
        })
        recyclerView.adapter = adapter

        mAuth = FirebaseAuth.getInstance()
        val currentUserUid = mAuth.currentUser?.uid

        if (currentUserUid != null) {
            databaseRef = FirebaseDatabase.getInstance().reference.child("consultRooms")
            // 채팅방 목록 가져오기
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    consultList.clear()
                    for (consultSnapshot in snapshot.children) {
                        val consult = consultSnapshot.getValue(Consult::class.java)
                        if (consult != null && consult.mentiUid == currentUserUid) {
                            consultList.add(consult)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }
}
