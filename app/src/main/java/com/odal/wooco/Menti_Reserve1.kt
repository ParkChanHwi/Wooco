package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Menti_Reserve1 : AppCompatActivity(), Menti_Reserve1_bottom_sheet1.OnCategorySelectedListener, Menti_Reserve1_bottom_sheet2.OnCategorySelectedListener, Menti_Reserve1_bottom_sheet3.OnCategorySelectedListener, Menti_Reserve1_bottom_sheet4.OnCategorySelectedListener, Menti_Reserve1_bottom_sheet5.OnCategorySelectedListener{
    private var selectedCategory: String? = null
    private var coach_uid: String? = null
    private var coachName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_reserve1)

        val arrowImageView: ImageView = findViewById(R.id.arrow_3)
        arrowImageView.setOnClickListener {
            finish()
        }

        coach_uid = intent.getStringExtra("coach_uid")
        coachName = intent.getStringExtra("coach_name")

        val reserveButton: Button = findViewById(R.id.reserve_button)

        reserveButton.setOnClickListener {
            val category = selectedCategory

            // 다음 액티비티로 인텐트 생성
            val intent = Intent(this, MentiReserve::class.java)
            intent.putExtra("selectedCategory", category) // selectedCategory 추가
            Log.d("selected Category", "selected Category : $category")
            intent.putExtra("coach_uid", coach_uid)
            intent.putExtra("coach_name", coachName)
            startActivity(intent)
        }



        val kategori2: Button = findViewById(R.id.kategori2)
        kategori2.setOnClickListener {
            showBottomSheet(Menti_Reserve1_bottom_sheet1())
        }

        val kategori3: Button = findViewById(R.id.kategori3)
        kategori3.setOnClickListener {
            showBottomSheet(Menti_Reserve1_bottom_sheet2())
        }

        val kategori4: Button = findViewById(R.id.kategori4)
        kategori4.setOnClickListener {
            showBottomSheet(Menti_Reserve1_bottom_sheet3())
        }

        val kategori5: Button = findViewById(R.id.kategori5)
        kategori5.setOnClickListener {
            showBottomSheet(Menti_Reserve1_bottom_sheet4())
        }

        val kategori6: Button = findViewById(R.id.kategori6)
        kategori6.setOnClickListener {
            showBottomSheet(Menti_Reserve1_bottom_sheet5())
        }

        updateSelectedCategoryText()
    }

    private fun showBottomSheet(bottomSheet: BottomSheetDialogFragment) {
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    override fun onCategorySelected(category: String) {
        selectedCategory = category
        updateSelectedCategoryText()
    }

    private fun updateSelectedCategoryText() {
        val confirmationTextView: TextView = findViewById(R.id.reserve_selected_categori)
        val text = " 코치 이름: $coachName       카테고리 : ${selectedCategory ?: "위에서 선택"}"
        confirmationTextView.text = text
    }
}