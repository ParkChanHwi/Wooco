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
            val bottomSheet = BottomSheet1()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori2).setOnClickListener {
            val bottomSheet = BottomSheet2()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori3).setOnClickListener {
            val bottomSheet = BottomSheet3()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori4).setOnClickListener {
            val bottomSheet = BottomSheet4()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori5).setOnClickListener {
            val bottomSheet = BottomSheet5()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        findViewById<Button>(R.id.kategori6).setOnClickListener {
            val bottomSheet = BottomSheet6()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        // Firebase Database 초기화
        // pathString은 코치목록으로 변경
        database = FirebaseDatabase.getInstance().reference.child("coachInfo")

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.coachlist_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        coachAdapter = Coach_Adapter(itemList)
        recyclerView.adapter = coachAdapter

        // Firebase에서 데이터 가져오기
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (dataSnapshot in snapshot.children) {
                    val coach = dataSnapshot.getValue(CoachDataModel::class.java)
                    if (coach != null) {
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

        // 아직 채팅방 형성 x
        chatBtn.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
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
}