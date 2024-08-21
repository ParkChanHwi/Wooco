package com.odal.wooco

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Menti_mypage_pastclassActivity : AppCompatActivity() {

    private lateinit var pastClassRecyclerView: RecyclerView
    private lateinit var pastClassAdapter: Menti_mypage_pastclassActivityAdapter
    private val pastClassList = mutableListOf<PastClassItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_mypage_pastclass)

        pastClassRecyclerView = findViewById(R.id.pastclass_recycler_view)
        pastClassAdapter = Menti_mypage_pastclassActivityAdapter(pastClassList)
        pastClassRecyclerView.layoutManager = LinearLayoutManager(this)
        pastClassRecyclerView.adapter = pastClassAdapter

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
                pastClassList.clear()
                for (dataSnapshot in snapshot.children) {
                    val pastClassItem = dataSnapshot.getValue(PastClassItem::class.java)
                    if (pastClassItem != null) {
                        pastClassList.add(pastClassItem)
                    }
                }
                pastClassAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        })
    }
}
