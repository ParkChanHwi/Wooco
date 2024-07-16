package com.odal.wooco

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
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

    private lateinit var searchButton: Button
    private lateinit var searchInput: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var recyclerView: RecyclerView
    private val results = mutableListOf<String>()
    private lateinit var database: DatabaseReference

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

        database = FirebaseDatabase.getInstance().getReference("category")

        searchButton.setOnClickListener {
            val query = searchInput.text.toString()
            if (query.isNotEmpty()) {
                performSearch(query)
            }
        }

        return view
    }

    class BottomSheet1 : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.menti_univ_bottom_sheet, container, false)
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
                    R.id.medicine to "의학"
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


    class BottomSheet4() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet3, container, false)
        }
    }
    class BottomSheet5() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet5, container, false)
        }
    }
    class BottomSheet6() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet4, container, false)
        }
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
