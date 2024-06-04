package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.odal.wooco.utils.FirebaseRef
import com.odal.wooco.R


class Coach_myselfActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_myself)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        val uid = intent.getStringExtra("uid") ?: ""
        val name = intent.getStringExtra("name") ?: ""
        val school = intent.getStringExtra("school") ?: ""
        val interest = intent.getStringExtra("interest") ?: ""

        val backImageView = findViewById<ImageView>(R.id.back)
        backImageView.setOnClickListener {
            val intent = Intent(this, Coach_registerActivity::class.java)
            startActivity(intent)
        }

        val registerBtn = findViewById<Button>(R.id.main)
        registerBtn.setOnClickListener {
            val myself1 = findViewById<EditText>(R.id.myself1).text.toString()
            val myself2 = findViewById<EditText>(R.id.myself2).text.toString()
            val myself3 = findViewById<EditText>(R.id.myself3).text.toString()
            val myself4 = findViewById<EditText>(R.id.myself4).text.toString()

            // Firebase에 코치 정보 추가
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val uid = currentUser.uid

                val dataMap = HashMap<String, Any>()
                dataMap["uid"] = uid
                dataMap["name"] = name
                dataMap["school"] = school
                dataMap["interest"] = interest
                dataMap["myself1"] = myself1
                dataMap["myself2"] = myself2
                dataMap["myself3"] = myself3
                dataMap["myself4"] = myself4

                val coachInfoRef = database.getReference("coachInfo").child(uid)
                coachInfoRef.setValue(dataMap).addOnSuccessListener {
                    // 데이터 전송이 성공한 경우
                    // 추가 작업이 필요하다면 여기에 작성하세요
                    val intent = Intent(this, Coach_mypageActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    // 데이터 전송이 실패한 경우
                    // 실패 처리 작업을 여기에 작성하세요
                    Toast.makeText(this, "Failed to save data to Firebase", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}