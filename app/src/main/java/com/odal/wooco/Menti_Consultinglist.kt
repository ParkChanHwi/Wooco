package com.odal.wooco

import Consult
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Menti_Counsultinglist : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Menti_Consultinglist_Adapter
    private val consultList = mutableListOf<Consult>()

    private lateinit var databaseRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_consultinglist)

        recyclerView = findViewById(R.id.menti_consulting_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Menti_Consultinglist_Adapter(consultList)
        recyclerView.adapter = adapter

        mAuth = FirebaseAuth.getInstance()
        val currentUserUid = mAuth.currentUser?.uid

        if (currentUserUid != null) {
            databaseRef = FirebaseDatabase.getInstance().reference.child("MenticonsultRooms")
            // 채팅방 목록 가져오기
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    consultList.clear()
                    for (consultSnapshot in snapshot.children) {
                        val consult = consultSnapshot.getValue(Consult::class.java)
                        if (consult != null && consult.mainID == currentUserUid) {
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
