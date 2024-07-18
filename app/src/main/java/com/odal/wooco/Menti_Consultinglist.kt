package com.odal.wooco

import Consult
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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
        enableEdgeToEdge()
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
                            consultList.add(Consult(consult.mentiName, consult.coachName,consult.coachUid,consult.mentiUid,consult.mainID))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })

            val homeBtn: ImageView = findViewById(R.id.material_sy)
            val chatBtn: ImageView = findViewById(R.id.chat_1)
            val calBtn: ImageView = findViewById(R.id.uiw_date)
            val profileBtn: ImageView = findViewById(R.id.group_513866)
            val classList: TextView = findViewById(R.id.classList)
            val classListBox : LinearLayout = findViewById(R.id.classListBox)

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

            classList.setOnClickListener {
                val intent = Intent(this, Menti_Classlist::class.java)
                startActivity(intent)
            }
            classListBox.setOnClickListener {
                val intent = Intent(this, Menti_Classlist::class.java)
                startActivity(intent)
            }

        }
    }
}
