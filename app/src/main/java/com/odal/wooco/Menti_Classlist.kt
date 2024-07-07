package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.odal.wooco.datamodels.Class

class Menti_Classlist : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Menti_Classlist_Adapter
    private val classlist = mutableListOf<Class>()
    private lateinit var databaseRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_classlist)

        recyclerView = findViewById(R.id.menti_classlist_recycleView)
        adapter = Menti_Classlist_Adapter(classlist)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        mAuth = FirebaseAuth.getInstance()
        val currentUserUid = mAuth.currentUser?.uid

        if (currentUserUid != null) {
            databaseRef = FirebaseDatabase.getInstance().reference.child("MenticlassRooms")
            // 클래스 목록 가져오기
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    classlist.clear()
                    for (classSnapshot in snapshot.children) {
                        val classData = classSnapshot.getValue(Class::class.java)
                        if (classData != null && classData.mainID == currentUserUid) {
                            classlist.add(Class(classData.mentiName, classData.coachName,classData.coachUid,classData.mentiUid,classData.mainID,classData.lastMessage))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }

        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val consultlist: TextView = findViewById(R.id.consultList)

        homeBtn.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        chatBtn.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        calBtn.setOnClickListener {
            val intent = Intent(this, Menti_scheduleActivity::class.java)
            startActivity(intent)
        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }

        consultlist.setOnClickListener {
            val intent = Intent(this, Menti_Counsultinglist::class.java)
            startActivity(intent)
        }
    }
}
