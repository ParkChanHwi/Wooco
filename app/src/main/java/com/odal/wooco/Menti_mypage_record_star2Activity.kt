package com.odal.wooco

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class Menti_mypage_record_star2Activity : AppCompatActivity() {

    private var selectedStarRating: String = "0.0" // 선택된 별점 값을 저장할 변수
    private var selectedSatisfactionButton: Button? = null
    private var selectedResponseSpeedButton: Button? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var finishClassRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_mypage_record_star2)

        // 하단 검정 바 없애기
        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            val defaultInsets = view.onApplyWindowInsets(insets)
            insets.replaceSystemWindowInsets(
                defaultInsets.systemWindowInsetLeft,
                defaultInsets.systemWindowInsetTop,
                defaultInsets.systemWindowInsetRight,
                0 // 하단 inset을 0으로 설정하여 검정 바 없앰
            )
        }

        // Firebase 초기화
        database = FirebaseDatabase.getInstance()
        finishClassRef = database.getReference("finishClassInfo")

        // 코치 정보 설정
        val reserveId = intent.getStringExtra("reserveId")
        val nicknameTextView: TextView = findViewById(R.id.nicknameTextView)
        val schoolOrCompanyTextView: TextView = findViewById(R.id.categoryTextView)

        if (reserveId != null) {
            loadReserveInfo(reserveId, nicknameTextView, schoolOrCompanyTextView)
        } else {
            Toast.makeText(this, "예약 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 별점 이미지뷰들
        val star1: ImageView = findViewById(R.id.star1)
        val star2: ImageView = findViewById(R.id.star2)
        val star3: ImageView = findViewById(R.id.star3)
        val star4: ImageView = findViewById(R.id.star4)
        val star5: ImageView = findViewById(R.id.star5)

        // 별점 클릭 리스너 설정
        star1.setOnClickListener { selectStarRating(1) }
        star2.setOnClickListener { selectStarRating(2) }
        star3.setOnClickListener { selectStarRating(3) }
        star4.setOnClickListener { selectStarRating(4) }
        star5.setOnClickListener { selectStarRating(5) }

        // 만족도 버튼들
        val verySatisfiedButton: Button = findViewById(R.id.verySatisfiedButton)
        val averageButton: Button = findViewById(R.id.averageButton)
        val dissatisfiedButton: Button = findViewById(R.id.dissatisfiedButton)

        // 답장 속도 버튼들
        val fastResponseButton: Button = findViewById(R.id.fastResponseButton)
        val averageResponseButton: Button = findViewById(R.id.averageResponseButton)
        val slowResponseButton: Button = findViewById(R.id.slowResponseButton)

        // 만족도 버튼 클릭 리스너 설정
        verySatisfiedButton.setOnClickListener {
            selectSatisfactionButton(verySatisfiedButton)
        }

        averageButton.setOnClickListener {
            selectSatisfactionButton(averageButton)
        }

        dissatisfiedButton.setOnClickListener {
            selectSatisfactionButton(dissatisfiedButton)
        }

        // 답장 속도 버튼 클릭 리스너 설정
        fastResponseButton.setOnClickListener {
            selectResponseSpeedButton(fastResponseButton)
        }

        averageResponseButton.setOnClickListener {
            selectResponseSpeedButton(averageResponseButton)
        }

        slowResponseButton.setOnClickListener {
            selectResponseSpeedButton(slowResponseButton)
        }

        // 리뷰 제출 버튼 설정
        findViewById<Button>(R.id.submit_review_button).setOnClickListener {
            if (reserveId != null) {
                submitReview(reserveId)
            } else {
                Toast.makeText(this, "예약 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 리뷰 입력 시 엔터 키로 저장
        val reviewEditText: EditText = findViewById(R.id.reviewEditText)
        reviewEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (reserveId != null) {
                    submitReview(reserveId)
                }
                true
            } else {
                false
            }
        }
    }

    private fun loadReserveInfo(reserveId: String, nicknameTextView: TextView, schoolOrCompanyTextView: TextView) {
        finishClassRef.child(reserveId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val coachName = snapshot.child("coach_receiverName").getValue(String::class.java)
                val category = snapshot.child("selected_category").getValue(String::class.java)

                nicknameTextView.text = coachName ?: "정보 없음"
                schoolOrCompanyTextView.text = category ?: "정보 없음"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Menti_mypage_record_star2Activity, "데이터 로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun selectStarRating(starCount: Int) {
        // 별점에 따라 이미지와 텍스트 설정
        selectedStarRating = when (starCount) {
            1 -> "1.0"
            2 -> "2.0"
            3 -> "3.0"
            4 -> "4.0"
            5 -> "5.0"
            else -> "0.0"
        }

        // 별점에 따라 별 이미지 색상 변경
        val starImages = listOf(
            findViewById<ImageView>(R.id.star1),
            findViewById<ImageView>(R.id.star2),
            findViewById<ImageView>(R.id.star3),
            findViewById<ImageView>(R.id.star4),
            findViewById<ImageView>(R.id.star5)
        )

        for (i in starImages.indices) {
            if (i < starCount) {
                starImages[i].setColorFilter(Color.parseColor("#F89B00")) // 주황색으로 설정
            } else {
                starImages[i].setColorFilter(Color.parseColor("#C0C0C0")) // 기본 색상 (회색)으로 설정
            }
        }
    }

    private fun selectSatisfactionButton(button: Button) {
        // 선택된 버튼의 배경을 유지하고 다른 버튼의 배경을 초기화
        selectedSatisfactionButton?.isSelected = false
        button.isSelected = true
        selectedSatisfactionButton = button

        resetButtonBackground(
            findViewById(R.id.verySatisfiedButton),
            findViewById(R.id.averageButton),
            findViewById(R.id.dissatisfiedButton)
        )
        button.setBackgroundResource(R.drawable.button_background_selector)
    }

    private fun selectResponseSpeedButton(button: Button) {
        // 선택된 버튼의 배경을 유지하고 다른 버튼의 배경을 초기화
        selectedResponseSpeedButton?.isSelected = false
        button.isSelected = true
        selectedResponseSpeedButton = button

        resetButtonBackground(
            findViewById(R.id.fastResponseButton),
            findViewById(R.id.averageResponseButton),
            findViewById(R.id.slowResponseButton)
        )
        button.setBackgroundResource(R.drawable.button_background_selector)
    }

    private fun resetButtonBackground(vararg buttons: Button) {
        for (button in buttons) {
            if (!button.isSelected) {
                button.setBackgroundResource(R.drawable.rectangle_9833)  // 기본 배경으로 설정
            }
        }
    }

    private fun submitReview(reserveId: String) {
        // 리뷰 데이터를 수집
        val reviewText = findViewById<EditText>(R.id.reviewEditText).text.toString()
        if (selectedStarRating == "0.0" || reviewText.isEmpty() || selectedSatisfactionButton == null || selectedResponseSpeedButton == null) {
            Toast.makeText(this, "모든 항목을 선택하고 리뷰를 작성해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 새로운 리뷰 데이터 생성
        val reviewData = hashMapOf(
            "reserveId" to reserveId,
            "stars" to selectedStarRating,
            "satisfaction" to selectedSatisfactionButton?.text.toString(),
            "responseSpeed" to selectedResponseSpeedButton?.text.toString(),
            "reviewText" to reviewText
        )

        // 리뷰 데이터를 Firebase에 저장
        val reviewRef = database.getReference("reviews").push()
        reviewRef.setValue(reviewData)

        // finishClassInfo의 visible 필드 업데이트
        finishClassRef.child(reserveId).child("visible").setValue(1)
        Toast.makeText(this, "리뷰가 저장되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }
}
