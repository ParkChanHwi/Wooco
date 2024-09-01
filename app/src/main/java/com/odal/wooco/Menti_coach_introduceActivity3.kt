package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Menti_coach_introduceActivity3 : AppCompatActivity() {
    private lateinit var receiverUid: String
    private lateinit var receiverName: String
    private lateinit var receiverSchool: String
    private lateinit var receiverInterest: String
    private lateinit var database: DatabaseReference
    private lateinit var reviewAdapter: Menti_coach_introduceActivity3Adapter
    private val reviewList = mutableListOf<Menti_coach_introduceActivity3Adapter.Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coach_introduce3)

        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)

        receiverUid = intent.getStringExtra("uid").toString()
        receiverName = intent.getStringExtra("name").toString()
        receiverSchool = intent.getStringExtra("school").toString()
        receiverInterest = intent.getStringExtra("interest").toString()
        database = FirebaseDatabase.getInstance().reference

        reviewAdapter = Menti_coach_introduceActivity3Adapter(reviewList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = reviewAdapter

        loadReviews()
        loadAverageStarScore() // 평균 별점 로드

        // 버튼과 뷰 설정
        val coachName: TextView = findViewById(R.id.user_class_text2)
        val coachSchool: TextView = findViewById(R.id.user_class_text3)
        val coachInterest: TextView = findViewById(R.id.user_class_text4)
        val coachIntro1: TextView = findViewById(R.id.coach_category)
        val coachIntro2: TextView = findViewById(R.id.calss_inform)
        val coachIntro3: TextView = findViewById(R.id.review)
        val ArrowImageView: ImageView = findViewById(R.id.ArrowImageView)

        coachName.text = receiverName
        coachSchool.text = receiverSchool
        coachInterest.text = receiverInterest

        coachIntro1.setOnClickListener {
            val intent1 = Intent(this, Menti_coach_introduceActivity::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("school", receiverSchool)
                putExtra("interest", receiverInterest)
            }
            startActivity(intent1)
        }

        coachIntro2.setOnClickListener {
            val intent2 = Intent(this, Menti_coach_introduceActivity2::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("school", receiverSchool)
                putExtra("interest", receiverInterest)
            }
            startActivity(intent2)
        }

        coachIntro3.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        ArrowImageView.setOnClickListener {
            finish()
        }
    }

    private fun loadReviews() {
        database.child("reviews")
            .orderByChild("coachUid")
            .equalTo(receiverUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    reviewList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val name = dataSnapshot.child("mentiName").getValue(String::class.java) ?: "Unknown"
                        val date = dataSnapshot.child("reserve_time").getValue(String::class.java) ?: "Unknown"
                        val reviewText = dataSnapshot.child("reviewText").getValue(String::class.java) ?: ""
                        val stars = dataSnapshot.child("stars").getValue(Double::class.java) ?: 0.0
                        val respondSpeed = dataSnapshot.child("responseSpeed").getValue(String::class.java) ?: "Unknown"
                        val satisfaction = dataSnapshot.child("satisfaction").getValue(String::class.java) ?: "Unknown"

                        val reviewItem = Menti_coach_introduceActivity3Adapter.Item(
                            name, date, reviewText, stars, respondSpeed, satisfaction
                        )
                        reviewList.add(reviewItem)
                    }
                    reviewAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Menti_coach_introduceActivity3", "Error loading reviews: ${error.message}")
                }
            })
    }

    private fun loadAverageStarScore() {
        val starScoreTextView: TextView = findViewById(R.id.star_score_text)

        database.child("reviews")
            .orderByChild("coachUid")
            .equalTo(receiverUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var totalStars = 0.0
                    var reviewCount = 0

                    for (dataSnapshot in snapshot.children) {
                        val stars = dataSnapshot.child("stars").getValue(Double::class.java) ?: 0.0
                        totalStars += stars
                        reviewCount++
                    }

                    val averageStars = if (reviewCount > 0) totalStars / reviewCount else 0.0
                    starScoreTextView.text = String.format("%.1f", averageStars)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Menti_coach_introduceActivity3", "Error calculating average star score: ${error.message}")
                }
            })
    }
}
