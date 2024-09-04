package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Menti_mypage_record_star3Activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Menti_mtpage_record_star3Adapter
    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_mypage_recordstar3)

        recyclerView = findViewById(R.id.record_star_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val reviewsList = mutableListOf<ReviewItem>()
        adapter = Menti_mtpage_record_star3Adapter(reviewsList)
        recyclerView.adapter = adapter

        // 현재 사용자의 UID를 가져옵니다.
        val currentUserUid = auth.currentUser?.uid

        val ArrowImageView: ImageView = findViewById(R.id.ArrowImageView)
        ArrowImageView.setOnClickListener {
            finish()
        }

        if (currentUserUid != null) {
            // Firebase에서 데이터를 가져오기 전, mentiUid 값 출력
            Log.d("Menti_mypage_record_star3Activity", "Querying for mentiUid: $currentUserUid")

            // reviews 데이터베이스에서 현재 사용자의 UID와 일치하는 리뷰들을 가져옵니다.
            database.child("reviews")
                .orderByChild("mentiUid")
                .equalTo(currentUserUid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (!snapshot.exists()) {
                            // Realtime Database에서 아무 문서도 반환되지 않은 경우
                            Log.d("Menti_mypage_record_star3Activity", "No reviews found for this user.")
                        } else {
                            for (document in snapshot.children) {
                                Log.d("Menti_mypage_record_star3Activity", "Review found: ${document.key}")
                                val reviewItem = ReviewItem(
                                    coachName = document.child("coachName").getValue(String::class.java) ?: "알 수 없음",
                                    category = document.child("selected_category").getValue(String::class.java) ?: "알 수 없음",
                                    reviewDate = document.child("reserve_time").getValue(String::class.java) ?: "날짜 없음",
                                    starScore = document.child("stars").getValue(Double::class.java) ?: 0.0
                                )
                                reviewsList.add(reviewItem)
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Menti_mypage_record_star3Activity", "Error fetching reviews: ${error.message}")
                    }
                })
        } else {
            Log.d("Menti_mypage_record_star3Activity", "CurrentUserUid is null, user might not be authenticated.")
        }
    }
}

data class ReviewItem(
    val coachName: String,
    val category: String,
    val reviewDate: String,
    val starScore: Double
)
