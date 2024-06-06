package com.odal.wooco

import android.app.ProgressDialog
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
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.CoachDataModel
import com.odal.wooco.datamodels.ReserveDataModel
import com.odal.wooco.utils.FirebaseRef.Companion.userInfoRef

class Menti_scheduleActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var reserveAdapter: Menti_sheduleActivityAdapter // Adapter 변수 추가
    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<ReserveDataModel>() // itemList 선언 및 초기화

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

        // Firebase Database 초기화
        // pathString은 예약목록으로 변경
        database = FirebaseDatabase.getInstance().reference.child("reserveInfo")


        // RecyclerView 초기화
        recyclerView = findViewById(R.id.menti_schedule_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        reserveAdapter = Menti_sheduleActivityAdapter(itemList, this) // Context 추가
        recyclerView.adapter = reserveAdapter

        // Firebase에서 데이터 가져오기
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (dataSnapshot in snapshot.children) {
                    val reserve = dataSnapshot.getValue(ReserveDataModel::class.java)
                    if (reserve != null) {
                        itemList.add(reserve)
                    }
                }
                reserveAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error: ${error.message}")
            }
        })

        userInfoRef.child(currentUser?.uid ?: "").child("nickname").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nickname = snapshot.getValue(String::class.java)

                // 가져온 이름을 화면에 표시
                val scheduleNameTextView: TextView = findViewById(R.id.user_class_text)
                scheduleNameTextView.text = nickname

                Log.d("UserDisplayName", "Current user display name: $nickname")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        })




        // 버튼 클릭 리스너 설정
        homeBtn.setOnClickListener{
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        chatBtn.setOnClickListener{
            val intent = Intent(this, Menti_Classlist::class.java)
            startActivity(intent)
        }

        calBtn.setOnClickListener{
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        profileBtn.setOnClickListener{
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }
    }
}
