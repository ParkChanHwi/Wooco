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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.ReserveDataModel
import com.odal.wooco.utils.FirebaseRef.Companion.userInfoRef

class Menti_scheduleActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var reserveQuery: Query
    private lateinit var reserveAdapter: Menti_sheduleActivityAdapter
    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<ReserveDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_schedule)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        recyclerView = findViewById(R.id.menti_schedule_recycler_view)

        // RecyclerView 초기화
        recyclerView.layoutManager = LinearLayoutManager(this)
        reserveAdapter = Menti_sheduleActivityAdapter(itemList, this)
        recyclerView.adapter = reserveAdapter

        currentUser?.let {
            val uid = it.uid

            // Firebase Database 쿼리 초기화
            val reserveInfoRef = FirebaseDatabase.getInstance().getReference("reserveInfo")
                .orderByChild("menti_uid")
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
            userInfoRef.child(uid).child("nickname").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nickname = snapshot.getValue(String::class.java)
                    val scheduleNameTextView: TextView = findViewById(R.id.user_class_text)
                    scheduleNameTextView.text = nickname
                    Log.d("UserDisplayName", "Current user display name: $nickname")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Failed to read value.", error.toException())
                }
            })
        }

        // 버튼 클릭 리스너 설정
        homeBtn.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        chatBtn.setOnClickListener {
            val intent = Intent(this, Menti_Classlist::class.java)
            startActivity(intent)
        }

        calBtn.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }
    }
}
