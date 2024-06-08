package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.odal.wooco.datamodels.Consult

class Menti_Counsultinglist : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Menti_Consultinglist_Adater
    private val consultList = mutableListOf<Consult>()

    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_consultinglist)

        recyclerView = findViewById(R.id.menti_consulting_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        databaseRef = FirebaseDatabase.getInstance().reference.child("consults")
        // mDbRef.child("consult").child(senderRoom).child("messages")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                consultList.clear()
                for (consultSnapshot in snapshot.children) {
                    val consult = consultSnapshot.getValue(Consult::class.java)
                    if (consult != null) {
                        consultList.add(consult)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

//        val chatBtn = findViewById<Button>(R.id.send_btn)
//        chatBtn.setOnClickListener {
//            val intent = Intent(this, ChatActivity::class.java)
//            intent.putExtra("chat_type", 1) // 상담하기 버튼이므로 chat_type을 1로 설정
//            startActivity(intent)
//        }
    }
}
