package com.odal.wooco

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Menti_mypage_record_starActivity : AppCompatActivity() {

    private lateinit var recordStarRecyclerView: RecyclerView
    private lateinit var recordStarAdapter: Menti_mypage_record_starActivityAdapter
    private val recordStarList = mutableListOf<PastClassItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_mypage_record_star)

        recordStarRecyclerView = findViewById(R.id.record_star_recycler_view)
        recordStarAdapter = Menti_mypage_record_starActivityAdapter(this, recordStarList)
        recordStarRecyclerView.layoutManager = LinearLayoutManager(this)
        recordStarRecyclerView.adapter = recordStarAdapter

        val userId = intent.getStringExtra("userId")

        if (userId != null) {
            fetchPastClassData(userId)
        } else {
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchPastClassData(userId: String) {
        val database = FirebaseDatabase.getInstance()
        val pastClassRef = database.getReference("finishClassInfo")

        pastClassRef.orderByChild("menti_uid").equalTo(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newItems = mutableListOf<PastClassItem>()
                for (dataSnapshot in snapshot.children) {
                    val pastClassItem = dataSnapshot.getValue(PastClassItem::class.java)
                    val visible = dataSnapshot.child("visible").getValue(Int::class.java) ?: 0

                    // visible 값이 0이거나 존재하지 않는 경우만 필터링
                    if (pastClassItem != null && visible == 0) {
                        newItems.add(pastClassItem)
                    }
                }
                recordStarAdapter.updateItems(newItems)  // 어댑터의 데이터를 갱신합니다.
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
                Toast.makeText(this@Menti_mypage_record_starActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
