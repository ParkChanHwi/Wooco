package com.odal.wooco

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class Menti_mypage_record_star3Activity : AppCompatActivity() {

    private lateinit var nicknameTextView: TextView
    private lateinit var schoolOrCompanyTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var starScoreTextView: TextView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_mypage_record_star_item2)

        // View 초기화
        nicknameTextView = findViewById(R.id.nicknameTextView)
        schoolOrCompanyTextView = findViewById(R.id.schoolOrCompanyTextView)
        dateTextView = findViewById(R.id.dateTextView)
        starScoreTextView = findViewById(R.id.star_score)  // 별점 텍스트뷰
        // starImageView는 이제 사용하지 않으므로 제거할 수 있습니다.

        // Firestore에서 데이터 가져오기
        val coachUid = intent.getStringExtra("COACH_UID") // 전달된 코치 UID를 가져옵니다.
        if (coachUid != null) {
            db.collection("coaches").document(coachUid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // 데이터베이스에서 값을 가져와서 UI에 반영합니다.
                        nicknameTextView.text = document.getString("nickname") ?: "알 수 없음"
                        schoolOrCompanyTextView.text = document.getString("schoolOrCompany") ?: "알 수 없음"
                        dateTextView.text = document.getString("courseDate") ?: "날짜 없음"
                        starScoreTextView.text = document.getDouble("starScore")?.toString() ?: "0.0"  // 별점 값을 텍스트로 설정
                    } else {
                        // 데이터가 없는 경우 처리
                        nicknameTextView.text = "데이터 없음"
                    }
                }
                .addOnFailureListener { e ->
                    // 실패 시 처리
                    nicknameTextView.text = "데이터 가져오기 실패"
                }
        }
    }
}
