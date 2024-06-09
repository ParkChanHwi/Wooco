package com.odal.wooco

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.odal.wooco.utils.FirebaseRef.Companion.database

class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var searchButton: Button
    private lateinit var searchInput: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var recyclerView: RecyclerView
    private val results = mutableListOf<String>()
    private lateinit var database: DatabaseReference


    interface OnCategorySelectedListener {
        fun onCategorySelected(category: String)
    }

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

        val setButton = view.findViewById<Button>(R.id.category_setting)
        setButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                val selectedText = selectedRadioButton.text.toString()
                // 선택된 카테고리 전달
                val intent = Intent().apply {
                    putExtra("SELECTED_CATEGORY", selectedText)
                }
                targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                dismiss()

            } else {
                Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }


    class BottomSheet2() : BottomSheetDialogFragment() {

        private var listener: OnCategorySelectedListener? = null
        fun setOnCategorySelectedListener(listener: OnCategorySelectedListener) {
            this.listener = listener
        }

        private fun onCategorySelected(category: String) {
            listener?.onCategorySelected(category)
        }


        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet, container, false)

            val setButton = view.findViewById<Button>(R.id.category_setting)
            setButton.setOnClickListener {
                val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId

                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    val selectedText2 = selectedRadioButton.text.toString()
                    onCategorySelected(selectedText2)
                    Log.d("새로 만든 건데 보내긴 보내질듯 :", selectedText2)
                    // 선택된 카테고리 전달
                    val intent = Intent().apply {
                        putExtra("SELECTED_CATEGORY2", selectedText2)
                    }
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            return view
        }
    }

    class BottomSheet3() : BottomSheetDialogFragment() {

        private var listener: OnCategorySelectedListener? = null
        fun setOnCategorySelectedListener(listener: OnCategorySelectedListener) {
            this.listener = listener
        }

        private fun onCategorySelected(category: String) {
            listener?.onCategorySelected(category)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet2, container, false)

            val setButton = view.findViewById<Button>(R.id.category_setting2)
            setButton.setOnClickListener {
                val radioButtonIds = mapOf(
                    R.id.humanities to "인문사회",
                    R.id.arts to "예체능",
                    R.id.medicine to "의학",
                    R.id.engineering to "공학",
                    R.id.education to "교육",
                    R.id.nature to "자연"
                )

                val selectedTexts = mutableListOf<String>()

                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedTexts.add(text)
                    }
                }

                //여기부터
                val selectedRadioButtonId = RadioButton.generateViewId()



                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    val selectedText3 = selectedRadioButton.text.toString()
                    onCategorySelected(selectedText3)
                    Log.d("새로 만든 건데 보내긴 보내질듯 :", selectedText3)
                    // 선택된 카테고리 전달
                    val intent = Intent().apply {
                        putExtra("SELECTED_CATEGORY3", selectedText3)

                    }
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
                //여기
            }

            return view
        }
    }

    class BottomSheet4() : BottomSheetDialogFragment() {

        private var listener: OnCategorySelectedListener? = null
        fun setOnCategorySelectedListener(listener: OnCategorySelectedListener) {
            this.listener = listener
        }

        private fun onCategorySelected(category: String) {
            listener?.onCategorySelected(category)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet3, container, false)

            val setButton = view.findViewById<Button>(R.id.category_setting)
            setButton.setOnClickListener {
                val radioButtonIds = mapOf(
                    R.id.transition to "편입",
                    R.id.company to "기업",
                    R.id.publicoffice to "공무원",
                    R.id.education to "임용",
                    R.id.medicine to "병원",
                    R.id.etc to "기타"
                )

                val selectedTexts = mutableListOf<String>()

                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedTexts.add(text)
                    }
                }

                val radioGroup = view.findViewById<RadioGroup>(R.id.radio_group)
                val radioButton1 = view.findViewById<RadioButton>(R.id.transition)
                val radioButton2 = view.findViewById<RadioButton>(R.id.company)
                val radioButton3 = view.findViewById<RadioButton>(R.id.medicine)
                val radioButton4 = view.findViewById<RadioButton>(R.id.education)
                val radioButton5 = view.findViewById<RadioButton>(R.id.publicoffice)
                val radioButton6 = view.findViewById<RadioButton>(R.id.etc)

                val allRadioButtons = listOf(radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6)
                val selectedRadioButton = allRadioButtons.firstOrNull { it.isChecked }
                if (selectedRadioButton != null) {
                    val selectedText4 = selectedRadioButton.text.toString()
                    onCategorySelected(selectedText4)
                    Log.d("새로 만든 건데 보내긴 보내질듯 :", selectedText4)
                    // 선택된 카테고리 전달
                    val intent = Intent().apply {
                        putExtra("SELECTED_CATEGORY4", selectedText4)

                    }
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            return view
        }
    }

    class BottomSheet5() : BottomSheetDialogFragment() {

        private var listener: OnCategorySelectedListener? = null
        fun setOnCategorySelectedListener(listener: OnCategorySelectedListener) {
            this.listener = listener
        }

        private fun onCategorySelected(category: String) {
            listener?.onCategorySelected(category)
        }
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet4, container, false)

            val setButton = view.findViewById<Button>(R.id.category_setting)
            setButton.setOnClickListener {
                val radioButtonIds = mapOf(
                    R.id.history to "한국사",
                    R.id.com to "컴퓨터활용능력",
                    R.id.pro to "기사/기능사",
                    R.id.language to "언어",
                    R.id.etc to "기타"
                )

                val selectedTexts = mutableListOf<String>()

                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedTexts.add(text)
                    }
                }

                val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId

                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    val selectedText5 = selectedRadioButton.text.toString()
                    onCategorySelected(selectedText5)
                    Log.d("새로 만든 건데 보내긴 보내질듯 :", selectedText5)
                    // 선택된 카테고리 전달
                    val intent = Intent().apply {
                        putExtra("SELECTED_CATEGORY5", selectedText5)

                    }
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            return view
        }
    }

    class BottomSheet6() : BottomSheetDialogFragment() {

        private var listener: OnCategorySelectedListener? = null
        fun setOnCategorySelectedListener(listener: OnCategorySelectedListener) {
            this.listener = listener
        }

        private fun onCategorySelected(category: String) {
            listener?.onCategorySelected(category)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet5, container, false)

            val setButton = view.findViewById<Button>(R.id.category_setting)
            setButton.setOnClickListener {
                val radioButtonIds = mapOf(
                    R.id.marketing to "마케팅",
                    R.id.plan to "기획",
                    R.id.office to "사무직",
                    R.id.special to "전문/특수/연구직",
                    R.id.transportation to "무역/유통",
                    R.id.service to "서비스",
                    R.id.it to "IT",
                    R.id.design to "디자인",
                    R.id.business to "영업",
                    R.id.education to "교육",
                    R.id.construction to "건설",
                    R.id.health to "의료"
                )

                val selectedTexts = mutableListOf<String>()

                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedTexts.add(text)
                    }
                }

                val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId

                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    val selectedText5 = selectedRadioButton.text.toString()
                    onCategorySelected(selectedText5)
                    Log.d("새로 만든 건데 보내긴 보내질듯 :", selectedText5)
                    // 선택된 카테고리 전달
                    val intent = Intent().apply {
                        putExtra("SELECTED_CATEGORY5", selectedText5)

                    }
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }


            return view
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
                            setTextColor(Color.BLACK)
                            buttonTintList = ColorStateList.valueOf(Color.BLACK)
                        }
                        radioGroup.addView(radioButton)
                    }
                } else {
                    Toast.makeText(context, "No results found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("DatabaseError", "Database error: ${databaseError.message}")
            }
        })
    }
}
