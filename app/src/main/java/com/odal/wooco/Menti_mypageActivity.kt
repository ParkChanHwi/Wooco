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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Menti_mypageActivity : AppCompatActivity() {
    // declare auth
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_mypage)
        // auth initialize
        auth = FirebaseAuth.getInstance()
        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val transferBtn: Button = findViewById(R.id.coach_transfer)
        val purchaseBtn: Button = findViewById(R.id.purchase)
        val my_reviewBtn: Button = findViewById(R.id.my_review)


        // Firebase Realtime Database에서 UserInfo 이름 및 woocoin_buy 정보 가져오기
        val database = FirebaseDatabase.getInstance()
        val userInfoRef = database.getReference("userInfo")

        // 현재 로그인한 사용자의 이름을 가져오기
        val currentUser = auth.currentUser

        val myPageNameTextView: TextView = findViewById(R.id.mypage_name)
        val coinCountTextView: TextView = findViewById(R.id.coin_count)

        userInfoRef.child(currentUser?.uid ?: "").child("nickname").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nickname = snapshot.getValue(String::class.java)

                if (nickname != null) {
                    // 가져온 이름을 화면에 표시
                    myPageNameTextView.text = nickname
                    Log.d("UserDisplayName", "Current user display name: $nickname")

                    // 구매 버튼 클릭 시 우코인 액티비티로 이동하면서 유저 아이디와 닉네임을 전달
                    purchaseBtn.setOnClickListener {
                        val intent = Intent(this@Menti_mypageActivity, Menti_mypage_woocoinActivity::class.java).apply {
                            putExtra("userId", currentUser?.uid)
                            putExtra("userName", nickname)
                        }
                        startActivity(intent)
                    }
                } else {
                    Log.e("UserDisplayName", "Nickname is null")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        })

        // Firebase Realtime Database에서 UserInfo의 woocoin_buy 정보 가져오기
        userInfoRef.child(currentUser?.uid ?: "").child("woocoin_buy").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val woocoinBuy = snapshot.getValue(Int::class.java)

                if (woocoinBuy != null) {
                    // 가져온 woocoin_buy 정보를 화면에 표시
                    coinCountTextView.text = woocoinBuy.toString()
                } else {
                    Log.e("Firebase", "woocoin_buy is null")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        })

        homeBtn.setOnClickListener{
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        chatBtn.setOnClickListener{
            val intent = Intent(this, Menti_Classlist::class.java)
            startActivity(intent)
        }

        calBtn.setOnClickListener{
            val intent = Intent(this, Menti_scheduleActivity::class.java)
            startActivity(intent)
        }

        profileBtn.setOnClickListener{
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        transferBtn.setOnClickListener{
            val intent = Intent(this, Coach_mypageActivity::class.java)
            startActivity(intent)
            finish()
        }

        my_reviewBtn.setOnClickListener{
            val intent = Intent(this, Menti_mypage_record_starActivity::class.java)
            startActivity(intent)

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
