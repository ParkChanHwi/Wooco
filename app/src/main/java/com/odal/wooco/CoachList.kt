package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.CoachDataModel
import com.odal.wooco.datamodels.ReserveDataModel
import java.text.SimpleDateFormat
import java.util.*

class CoachList : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var coachAdapter: Coach_Adapter
    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<CoachDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coachlist)

        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)

        findViewById<Button>(R.id.kategori1).setOnClickListener {
            val bottomSheet = MyBottomSheetDialogFragment()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        // 다른 카테고리 버튼들의 클릭 리스너도 동일하게 추가

        // Firebase Database 초기화
        database = FirebaseDatabase.getInstance().reference

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.coachlist_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        coachAdapter = Coach_Adapter(itemList, this)
        recyclerView.adapter = coachAdapter

        // 과거 예약 정보를 이동하고 삭제하는 함수 호출
        moveToFinishClassInfoForPastReservations()

        // Firebase에서 데이터 가져오기
        database.child("coachInfo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (dataSnapshot in snapshot.children) {
                    val coach = dataSnapshot.getValue(CoachDataModel::class.java)
                    coach?.let {
                        itemList.add(coach)
                    }
                }
                coachAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error: ${error.message}")
            }
        })

        homeBtn.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        // 멘티 수업목록 - 클래스리스트
        chatBtn.setOnClickListener {
            val intent = Intent(this, Menti_Classlist::class.java)
            startActivity(intent)
        }

        //멘티 나의 일정
        calBtn.setOnClickListener {
            val intent = Intent(this, Menti_scheduleActivity::class.java)
            startActivity(intent)
        }

        //멘티 마이페이지
        profileBtn.setOnClickListener {
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun moveToFinishClassInfoForPastReservations() {
        val reserveInfoRef = database.child("reserveInfo")
        val finishClassInfoRef = database.child("finishClassInfo")
        reserveInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val reserve = dataSnapshot.getValue(ReserveDataModel::class.java)
                    reserve?.let {
                        val reserveTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(it.reserve_time)
                        val currentTime = Calendar.getInstance().time
                        if (reserveTime != null && reserveTime.before(currentTime)) {
                            // Move to finishClassInfo
                            val reserveId = dataSnapshot.key!!
                            finishClassInfoRef.child(reserveId).setValue(it).addOnSuccessListener {
                                // Remove from reserveInfo
                                reserveInfoRef.child(reserveId).removeValue().addOnSuccessListener {
                                    Log.d("CoachList", "Moved reservation to finishClassInfo: $reserveId")
                                }.addOnFailureListener { e ->
                                    Log.e("CoachList", "Failed to remove reservation: ${e.message}")
                                }
                            }.addOnFailureListener { e ->
                                Log.e("CoachList", "Failed to move reservation: ${e.message}")
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error: ${error.message}")
            }
        })
    }
}
