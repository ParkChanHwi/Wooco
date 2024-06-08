package com.odal.wooco

import Request_Menti
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Coach_menti_request : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Coach_menti_request_Adapter
    private val requestList = mutableListOf<Request_Menti>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_menti_request)

        // ArrowImageView에 클릭 리스너 추가
        val backArrow: ImageView = findViewById(R.id.ArrowImageView)
        backArrow.setOnClickListener {
            onBackPressed()
        }

        recyclerView = findViewById(R.id.coach_request_recylerView)
        adapter = Coach_menti_request_Adapter(requestList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Firebase에서 데이터 불러오기
        fetchNotificationsFromFirebase()
    }

    private fun fetchNotificationsFromFirebase() {
        val database = FirebaseDatabase.getInstance()
        val notificationRef = database.getReference("notifications")

        // 로그인한 사용자의 정보를 가져옴
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName = currentUser?.displayName

        // 사용자의 코치 이름을 가져오기 위해 coachInfo 레퍼런스를 참조
        val coachInfoRef = database.getReference("coachInfo")

        // 코치 정보에서 현재 사용자의 UID와 일치하는 코치의 이름을 가져오는 쿼리 작성
        val query = coachInfoRef.orderByChild("uid").equalTo(currentUser?.uid)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 현재 사용자의 코치 정보를 가져옴
                    val userCoachName = dataSnapshot.children.first().child("name").getValue(String::class.java)

                    // notifications 레퍼런스에서 데이터를 가져옴
                    notificationRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val newList = mutableListOf<Request_Menti>()
                            for (snapshot in dataSnapshot.children) {
                                val notification = snapshot.getValue(Request_Menti::class.java)
                                // userName 또는 userCoachName과 일치하는 데이터를 필터링하여 추가
                                if (notification != null && (notification.menti_request_name == userName || notification.coach_receiverName == userCoachName)) {
                                    newList.add(0, notification)  // 리스트의 맨 앞에 추가
                                }
                            }
                            adapter.updateList(newList)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // 에러 처리
                        }
                    })
                } else {
                    // 사용자의 코치 정보를 찾을 수 없음
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
            }
        })
    }
}
