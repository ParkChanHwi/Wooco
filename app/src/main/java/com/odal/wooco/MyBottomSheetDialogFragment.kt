package com.odal.wooco

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.CategoryDataModel

class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var database: DatabaseReference
    private lateinit var radioGroup: RadioGroup
    private lateinit var recyclerView: RecyclerView
    private val results = mutableListOf<String>()

    private fun performSearch(query: String) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {  
                val categoryData = dataSnapshot.getValue(CategoryDataModel::class.java)

                val filteredResults = mutableListOf<String>()
                categoryData?.univs?.let { univs ->
                    filteredResults.addAll(univs.filter { it.contains(query, ignoreCase = true) })
                }

                // Clear existing RadioButtons
                radioGroup.removeAllViews()

                if (filteredResults.isNotEmpty()) {
                    filteredResults.forEach { result ->
                        val radioButton = RadioButton(context).apply {
                            text = result
                            buttonTintList = ColorStateList.valueOf(Color.parseColor("#696969"))
                        }
                        radioGroup.addView(radioButton)
                    }
                    recyclerView.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.GONE
                }

                // Update RecyclerView
                results.clear()
                results.addAll(filteredResults)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })
    }

    class BottomSheet1 : BottomSheetDialogFragment() {

        private lateinit var searchButton: Button
        private lateinit var searchInput: EditText
        private lateinit var radioGroup: RadioGroup
        private lateinit var recyclerView: RecyclerView
        private lateinit var database: DatabaseReference
        private val results = mutableListOf<String>()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.menti_univ_bottom_sheet, container, false)

            searchButton = view.findViewById(R.id.search_button)
            searchInput = view.findViewById(R.id.search_input)
            radioGroup = view.findViewById(R.id.radio_group)
            recyclerView = view.findViewById(R.id.search_results_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context)
            val categorySettingButton: Button = view.findViewById(R.id.category_setting)

            database = FirebaseDatabase.getInstance().getReference("category")

            // searchButton.setOnClickListener {
            //     val query = searchInput.text.toString()
            //     if (query.isNotEmpty()) {
            //         performSearch(query)
            //     }
            // }

            searchInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 로그에 Enter 키가 눌렸음을 기록합니다.
                    Log.d("SearchInput", "Enter key pressed")

                    // searchButton의 클릭 이벤트에서 했던 것과 동일한 작업을 수행합니다.
                    val query = searchInput.text.toString()
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    }
                    return@OnKeyListener true
                }
                return@OnKeyListener false
            })

            categorySettingButton.setOnClickListener {
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId
                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    val selectedText = selectedRadioButton.text.toString()
                    Toast.makeText(requireContext(), "선택된 항목: $selectedText", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "항목이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            return view
        }

        private fun performSearch(query: String) {
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val categoryData = dataSnapshot.getValue(CategoryDataModel::class.java)

                    val filteredResults = mutableListOf<String>()
                    categoryData?.univs?.let { univs ->
                        filteredResults.addAll(univs.filter { it.contains(query, ignoreCase = true) })
                    }

                    // Clear existing RadioButtons
                    radioGroup.removeAllViews()

                    if (filteredResults.isNotEmpty()) {
                        filteredResults.forEach { result ->
                            val radioButton = RadioButton(context).apply {
                                text = result
                                buttonTintList = ColorStateList.valueOf(Color.parseColor("#696969"))
                            }
                            radioGroup.addView(radioButton)
                        }
                        recyclerView.visibility = View.VISIBLE
                    } else {
                        recyclerView.visibility = View.GONE
                    }

                    // Update RecyclerView
                    results.clear()
                    results.addAll(filteredResults)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors.
                }
            })
        }
    }

    class BottomSheet2() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // 부모 클래스의 onCreateView 메서드 호출
            super.onCreateView(inflater, container, savedInstanceState)
            // 레이아웃 초기화
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet, container, false)

            // 버튼 찾기
            val setButton = view.findViewById<Button>(R.id.setting)

            // 버튼에 클릭 리스너 설정
            setButton.setOnClickListener {
                // 선택된 라디오 그룹의 ID 가져오기
                val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId

                if (selectedRadioButtonId != -1) {
                    // 선택된 라디오 버튼 찾기
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    // 선택된 라디오 버튼의 텍스트 가져오기
                    val selectedText = selectedRadioButton.text.toString()

                    // 선택된 라디오 버튼의 텍스트 출력
                    Toast.makeText(requireContext(), "선택된 라디오 버튼: $selectedText", Toast.LENGTH_SHORT).show()
                } else {
                    // 선택된 라디오 버튼이 없을 경우 처리
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            // 완성된 뷰 반환
            return view
        }
    }


    class BottomSheet3() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // 부모 클래스의 onCreateView 메서드 호출
            super.onCreateView(inflater, container, savedInstanceState)
            // 레이아웃 초기화
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet2, container, false)

            // 버튼 찾기
            val setButton = view.findViewById<Button>(R.id.category_setting2)

            // 버튼에 클릭 리스너 설정
            setButton.setOnClickListener {
                // 각 라디오 버튼의 ID와 해당 텍스트를 매핑하는 맵
                val radioButtonIds = mapOf(
                    R.id.humanities to "인문사회",
                    R.id.arts to "예체능",
                    R.id.medicine to "보건",
                    R.id.engineering to "공학",
                    R.id.education to "교육",
                    R.id.nature to "자연"
                )

                val selectedTexts = mutableListOf<String>()

                // 각 라디오 버튼을 순회하며 선택된 버튼의 텍스트를 가져오기
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedTexts.add(text)
                    }
                }

                if (selectedTexts.isNotEmpty()) {
                    // 선택된 라디오 버튼의 텍스트 출력
                    val message = "선택된 라디오 버튼: ${selectedTexts.joinToString(", ")}"
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    // 선택된 라디오 버튼이 없을 경우 처리
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }


            // 완성된 뷰 반환
            return view
        }
    }


    class BottomSheet4 : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet3, container, false)


            // 버튼 찾기
            val setButton = view.findViewById<Button>(R.id.category_setting)
            setButton.setOnClickListener {
                // 각 라디오 버튼의 ID와 해당 텍스트를 매핑하는 맵
                val radioButtonIds = mapOf(
                    R.id.job to "편입",
                    R.id.company to "기업",
                    R.id.government to "공무원",
                    R.id.employment to "임용",
                    R.id.hospital to "병원",
                    R.id.etc to "기타"
                )

                val selectedTexts = mutableListOf<String>()

                // 각 라디오 버튼을 순회하며 선택된 버튼의 텍스트를 가져오기
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedTexts.add(text)
                    }
                }

                if (selectedTexts.isNotEmpty()) {
                    // 선택된 라디오 버튼의 텍스트 출력
                    val message = "선택된 라디오 버튼: ${selectedTexts.joinToString(", ")}"
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    // 선택된 라디오 버튼이 없을 경우 처리
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }


            // 완성된 뷰 반환
            return view
        }
    }

    class BottomSheet5() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet5, container, false)


            // 버튼 찾기
            val setButton = view.findViewById<Button>(R.id.category_setting)
            setButton.setOnClickListener {
                // 각 라디오 버튼의 ID와 해당 텍스트를 매핑하는 맵
                val radioButtonIds = mapOf(
                    R.id.marketing to "마케팅",
                    R.id.business to "기획",
                    R.id.indoor to "사무직",
                    R.id.special to "전문/특수/연구직",
                    R.id.finance to "무역/유통",
                    R.id.service to "서비스",
                    R.id.it to "IT",
                    R.id.design to "디자인",
                    R.id.sales to "영업",
                    R.id.education to "교육",
                    R.id.construction to "건설",
                    R.id.medicine to "의료"
                )

                // RadioGroup 찾기
                val radioGroup = view.findViewById<RadioGroup>(R.id.category_group2)

                val selectedTexts = mutableListOf<String>()

                // 각 라디오 버튼을 순회하며 선택된 버튼의 텍스트를 가져오기
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedTexts.add(text)
                    }
                }

                if (selectedTexts.isNotEmpty()) {
                    // 선택된 라디오 버튼의 텍스트 출력
                    val message = "선택된 라디오 버튼: ${selectedTexts.joinToString(", ")}"
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    // 선택된 라디오 버튼이 없을 경우 처리
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }


            // 완성된 뷰 반환
            return view
        }
    }


    class BottomSheet6() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet4, container, false)


            // 버튼 찾기
            val setButton = view.findViewById<Button>(R.id.category_setting)
            setButton.setOnClickListener {
                // 각 라디오 버튼의 ID와 해당 텍스트를 매핑하는 맵
                val radioButtonIds = mapOf(
                    R.id.category_radio1 to "기사/기능사",
                    R.id.category_radio2 to "어학",
                    R.id.category_radio3 to "컴퓨터활용능력",
                    R.id.category_radio4 to "한국사",
                    R.id.category_radio5 to "기타"
                )


                val selectedTexts = mutableListOf<String>()

                // 각 라디오 버튼을 순회하며 선택된 버튼의 텍스트를 가져오기
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedTexts.add(text)
                    }
                }

                if (selectedTexts.isNotEmpty()) {
                    // 선택된 라디오 버튼의 텍스트 출력
                    val message = "선택된 라디오 버튼: ${selectedTexts.joinToString(", ")}"
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    // 선택된 라디오 버튼이 없을 경우 처리
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }


            // 완성된 뷰 반환
            return view
        }
    }

}
