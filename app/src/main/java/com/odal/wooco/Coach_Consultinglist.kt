package com.odal.wooco

import Coach_Consultinglist_Adapter
import Consult
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.odal.wooco.datamodels.UserDataModel

class Coach_Consultinglist : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Coach_Consultinglist_Adapter
    private val consultList = mutableListOf<Consult>()
    private lateinit var databaseRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_consultinglist)


        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val class_button : TextView = findViewById(R.id.class_button)
        val arrow_3 : ImageView = findViewById(R.id.arrow_3)

        recyclerView = findViewById(R.id.coach_consultinglist_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Coach_Consultinglist_Adapter(consultList)
        recyclerView.adapter = adapter

        mAuth = FirebaseAuth.getInstance()
        val currentUserUid = mAuth.currentUser?.uid

        if (currentUserUid != null) {
            val currentUserRef = FirebaseDatabase.getInstance().getReference("userInfo").child(currentUserUid)
            currentUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userDataModel = dataSnapshot.getValue(UserDataModel::class.java)
                        val currentUserName = userDataModel?.nickname

                        if (currentUserName != null) {
                            databaseRef = FirebaseDatabase.getInstance().reference.child("CoachconsultRooms")
                            // 상담방 목록 가져오기
                            databaseRef.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    consultList.clear()
                                    for (consultSnapshot in snapshot.children) {
                                        val consult = consultSnapshot.getValue(Consult::class.java)
                                        if (consult != null && consult.mainID == currentUserUid) {
                                            consultList.add(Consult(consult.mentiName, consult.coachName,consult.coachUid,consult.mentiUid,consult.mainID))
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

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                }
            })
        }

        class_button.setOnClickListener {
            val intent = Intent(this, Coach_Classlist::class.java)
            startActivity(intent)
        }


        //코치 나의 일정
        calBtn.setOnClickListener {
            val intent = Intent(this, Coach_scheduleActivity::class.java)
            startActivity(intent)
        }

        //코치 마이페이지
        profileBtn.setOnClickListener {
            val intent = Intent(this, Coach_mypageActivity::class.java)
            startActivity(intent)
        }

//        arrow_3.setOnClickListener {
//            val intent = Intent(this, Coach_mypageActivity::class.java)
//            startActivity(intent)
//        }

    }

}

