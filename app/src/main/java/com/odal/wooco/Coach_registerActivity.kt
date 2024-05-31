package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.odal.wooco.utils.FirebaseRef

class Coach_registerActivity : AppCompatActivity() {

    private val TAG = "Coach_registerActivity"

    private lateinit var auth: FirebaseAuth

    private lateinit var nameEditText: EditText
    private lateinit var schoolOrCompanyEditText: EditText
    private lateinit var majorOrPositionEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach_register)
        auth = FirebaseAuth.getInstance()

        nameEditText = findViewById(R.id.editText)
        schoolOrCompanyEditText = findViewById(R.id.editText2)
        majorOrPositionEditText = findViewById(R.id.editText3)

        // 뒤로가기 버튼
        val arrowImageView: ImageView = findViewById(R.id.back)
        arrowImageView.setOnClickListener {
            val intent = Intent(this, Coach_mypageAvtivity::class.java)
            startActivity(intent)
        }

        val registerBtn = findViewById<Button>(R.id.next_myself) // 코치 등록 버튼
        registerBtn.setOnClickListener {
            val name = nameEditText.text.toString()
            val schoolOrCompany = schoolOrCompanyEditText.text.toString()
            val majorOrPosition = majorOrPositionEditText.text.toString()

            if (name.isBlank() || schoolOrCompany.isBlank() || majorOrPosition.isBlank()) {
                Toast.makeText(this, "모든 필드를 채워주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val firestore = FirebaseFirestore.getInstance()

            val currentUser = auth.currentUser
            if (currentUser != null) {
                // 현재 로그인한 사용자의 UID 가져오기
                val uid = currentUser.uid

                // 코치 정보 생성
                val coachInfo = hashMapOf(
                    "name" to name,
                    "school_or_company" to schoolOrCompany,
                    "major_or_position" to majorOrPosition
                )

                // Firestore에 코치 정보 추가
                FirebaseRef.coachInfoRef.child(uid).setValue(coachInfo)
                    .addOnSuccessListener {
                        // Firestore에 코치 정보 추가 성공
                        Log.d(TAG, "Coach info added to Firestore.")

                        val intent = Intent(this, Coach_myselfActivity::class.java)
                        startActivity(intent)
                        finish() // 현재 액티비티 종료

                        // 추가적인 작업 수행 혹은 화면 이동 등
                    }
                    .addOnFailureListener { e ->
                        // Firestore에 코치 정보 추가 실패
                        Log.e(TAG, "Error adding coach info to Firestore.", e)
                    }

            }
        }
    }
}
