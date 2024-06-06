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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
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


        // Firebase Realtime Database에서 UserInfo 이름 가져오기
        val database = FirebaseDatabase.getInstance()
        val userInfoRef = database.getReference("userInfo")

        // 현재 로그인한 사용자의 이름을 가져오기
        val currentUser = auth.currentUser

        userInfoRef.child(currentUser?.uid ?: "").child("nickname").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nickname = snapshot.getValue(String::class.java)

                // 가져온 이름을 화면에 표시
                val myPageNameTextView: TextView = findViewById(R.id.mypage_name)
                myPageNameTextView.text = nickname

                Log.d("UserDisplayName", "Current user display name: $nickname")
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

        }
    }
