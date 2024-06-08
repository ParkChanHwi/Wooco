package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.*

class Coach_mypageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_mypage)

        auth = FirebaseAuth.getInstance()

        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val transferBtn: Button = findViewById(R.id.menti_transfer)
        val coachBtn: Button = findViewById(R.id.coach_register)
        val coachRequest: Button = findViewById(R.id.coach_request)
        var woocoinExchangeButton: Button? = null // 버튼을 미리 선언

        // Firebase Realtime Database에서 coachInfo의 이름 가져오기
        val database = FirebaseDatabase.getInstance()
        val coachInfoRef = database.getReference("coachInfo")

        // 현재 로그인한 사용자의 이름을 가져오기
        val currentUser = auth.currentUser

        coachInfoRef.child(currentUser?.uid ?: "").child("name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.getValue(String::class.java)

                // 가져온 이름을 화면에 표시
                val myPageNameTextView: TextView = findViewById(R.id.mypage_name)
                myPageNameTextView.text = name

                Log.d("UserDisplayName", "Current user display name: $name")

                // coachBtn 클릭 시 사용자 이름을 Coach_registerActivity로 전달
                coachBtn.setOnClickListener{
                    val intent = Intent(this@Coach_mypageActivity, Coach_registerActivity::class.java)
                    // 사용자 이름을 Coach_registerActivity로 전달
                    intent.putExtra("name", name)
                    startActivity(intent)
                    finish()
                }


                // woocoin_exchange 버튼 설정
                woocoinExchangeButton?.setOnClickListener {
                    if (name != null) {
                        val intent = Intent(this@Coach_mypageActivity, Coach_mypage_woocoin_exchangeActivity::class.java).apply {
                            putExtra("name", name)
                        }
                        startActivity(intent)
                    } else {
                        Log.e("Coach_mypageActivity", "Name is null")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        })

        woocoinExchangeButton = findViewById<Button>(R.id.woocoin_exchange) // 버튼 초기화

        coachRequest.setOnClickListener{
            val intent = Intent(this, Coach_menti_request::class.java)
            startActivity(intent)
        }
        chatBtn.setOnClickListener{
            val intent = Intent(this, Coach_Classlist::class.java)
            startActivity(intent)
        }

        calBtn.setOnClickListener{
            val intent = Intent(this, Coach_scheduleActivity::class.java)
            startActivity(intent)
        }


        profileBtn.setOnClickListener{
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }


        transferBtn.setOnClickListener{
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
            finish()
        }



        coachBtn.setOnClickListener{
            val intent = Intent(this, Coach_registerActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.logout).setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()

            // Redirect to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
