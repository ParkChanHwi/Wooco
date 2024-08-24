package com.odal.wooco

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Menti_mypage_record_star2Activity : AppCompatActivity() {

    private var selectedStarRating: String = "0.0" // 선택된 별점 값을 저장할 변수
    private var selectedSatisfactionButton: Button? = null
    private var selectedResponseSpeedButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_mypage_record_star2)

        // 코치 정보 설정
        val coachUid = intent.getStringExtra("COACH_UID")
        val nicknameTextView: TextView = findViewById(R.id.nicknameTextView)
        val schoolOrCompanyTextView: TextView = findViewById(R.id.schoolOrCompanyTextView)
        nicknameTextView.text = "차우코"  // 코치의 별명
        schoolOrCompanyTextView.text = "강원대 00학과"  // 코치의 학교/회사

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
}
