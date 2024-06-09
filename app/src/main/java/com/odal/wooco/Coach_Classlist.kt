package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.Class
import com.odal.wooco.datamodels.UserDataModel

class Coach_Classlist : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Coach_Classlist_Adapter
    private val mentiList = mutableListOf<Class>()
    private lateinit var databaseRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_classlist)

        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val consultTsf : TextView = findViewById(R.id.consult_button)
        val consult_button : TextView = findViewById(R.id.consult_button)

        recyclerView = findViewById(R.id.coach_classlist_recycleView)
        adapter = Coach_Classlist_Adapter(mentiList)
        recyclerView.layoutManager = LinearLayoutManager(this)
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
                            databaseRef = FirebaseDatabase.getInstance().reference.child("classRooms")
                            // 클래스 목록 가져오기
                            databaseRef.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    mentiList.clear()
                                    for (classSnapshot in snapshot.children) {
                                        val classData = classSnapshot.getValue(Class::class.java)
                                        if (classData != null && classData.mainID == currentUserUid && classData.coachName == currentUserName) {
                                            mentiList.add(Class(classData.mentiName, classData.coachName, classData.coachUid, classData.mentiUid, classData.mainID, classData.lastMessage))
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

        consult_button.setOnClickListener {
            val intent = Intent(this, Coach_Consultinglist::class.java)
            startActivity(intent)
        }

        chatBtn.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
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

        consultTsf.setOnClickListener {
            val intent = Intent(this, Coach_Consultinglist::class.java)
            startActivity(intent)
        }

    }
}
