package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.odal.wooco.datamodels.ReserveDataModel
import com.odal.wooco.utils.FirebaseRef

class Coach_scheduleActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var reserveAdapter: CoachScheduleActivityAdapter
    private val itemList = mutableListOf<ReserveDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_schedule)

        // Firebase 인증 및 데이터베이스 초기화
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        database = FirebaseDatabase.getInstance().reference.child("reserveInfo")

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.coach_schedule_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        reserveAdapter = CoachScheduleActivityAdapter(itemList, this)
        recyclerView.adapter = reserveAdapter

        // Firebase에서 데이터 가져오기
        currentUser?.let {
            val uid = it.uid

            // Firebase Database 쿼리 초기화
            val reserveInfoRef = FirebaseDatabase.getInstance().getReference("reserveInfo")
                .orderByChild("coach_receiverUid")
                .equalTo(uid)

            // Firebase에서 데이터 가져오기
            reserveInfoRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    itemList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val reserve = dataSnapshot.getValue(ReserveDataModel::class.java)
                        reserve?.let {
                            itemList.add(it)
                        }
                    }
                    itemList.sortBy { it.reserve_time } // 시작 시간으로 정렬
                    reserveAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Database error: ${error.message}")
                }
            })

            // 현재 사용자의 닉네임 가져와서 표시
            FirebaseRef.coachInfoRef.child(currentUser?.uid ?: "").child("name")
                .addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val name = snapshot.getValue(String::class.java)
                        val scheduleNameTextView: TextView = findViewById(R.id.user_class_text)
                        scheduleNameTextView.text = name
                        Log.d("UserDisplayName", "Current user display name: $name")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Firebase", "Failed to read value.", error.toException())
                    }
                })

            // 채팅 버튼 클릭 시 코치 클래스 목록 액티비티로 이동
            val chatBtn: ImageView = findViewById(R.id.chat_1)
            chatBtn.setOnClickListener {
                val intent = Intent(this, Coach_Classlist::class.java)
                startActivity(intent)
            }

            // 코치 나의 일정 버튼 클릭 시 안내 메시지 토스트 출력
            val calBtn: ImageView = findViewById(R.id.uiw_date)
            calBtn.setOnClickListener {
                Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
            }

            // 코치 마이페이지 버튼 클릭 시 코치 마이페이지 액티비티로 이동
            val profileBtn: ImageView = findViewById(R.id.group_513866)
            profileBtn.setOnClickListener {
                val intent = Intent(this, Coach_mypageActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
