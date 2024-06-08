package com.odal.wooco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Coach_register_bottomsheetFragment(private val onCategorySelected: (String, String) -> Unit) : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(onCategorySelected: (String, String) -> Unit): Coach_register_bottomsheetFragment {
            return Coach_register_bottomsheetFragment(onCategorySelected)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coach_register_category_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup1: RadioGroup = view.findViewById(R.id.radioGroup1)
        val radioGroup2: RadioGroup = view.findViewById(R.id.radioGroup2)
        val radioGroup3: RadioGroup = view.findViewById(R.id.radioGroup3)

        radioGroup1.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> {
                    BottomSheet2 { category, subcategory ->
                        onCategorySelected(category, subcategory)
                    }.show(parentFragmentManager, "BottomSheet2")
                }
                R.id.radioButton2 -> {
                    BottomSheet3 { category, subcategory ->
                        onCategorySelected(category, subcategory)
                    }.show(parentFragmentManager, "BottomSheet3")
                }
            }
            dismiss()
        }

        radioGroup2.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton3 -> {
                    BottomSheet4 { category, subcategory ->
                        onCategorySelected(category, subcategory)
                    }.show(parentFragmentManager, "BottomSheet4")
                }
                R.id.radioButton4 -> {
                    BottomSheet5 { category, subcategory ->
                        onCategorySelected(category, subcategory)
                    }.show(parentFragmentManager, "BottomSheet5")
                }
            }
            dismiss()
        }

        radioGroup3.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton5 -> {
                    BottomSheet6 { category, subcategory ->
                        onCategorySelected(category, subcategory)
                    }.show(parentFragmentManager, "BottomSheet6")
                }
            }
            dismiss()
        }
    }

    class BottomSheet2(private val onCategorySelected: (String, String) -> Unit) : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.bottom_sheet_one, container, false)
            val radioButtonIds = mapOf(
                R.id.career to "진로",
                R.id.etc to "기타 고민"
            )

            val setButton = view.findViewById<AppCompatButton>(R.id.setting)
            setButton.setOnClickListener {
                var selectedText: String? = null
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedText = text
                        break
                    }
                }
                if (selectedText != null) {
                    onCategorySelected("진로/기타고민", selectedText)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            return view
        }
    }

    class BottomSheet3(private val onCategorySelected: (String, String) -> Unit) : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.bottom_sheet_two, container, false)
            val radioButtonIds = mapOf(
                R.id.humanities to "인문/사회",
                R.id.arts to "예체능",
                R.id.medicine to "보건",
                R.id.engineering to "공학",
                R.id.education to "교육",
                R.id.nature to "자연"
            )

            val setButton = view.findViewById<AppCompatButton>(R.id.setting)
            setButton.setOnClickListener {
                var selectedText: String? = null
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedText = text
                        break
                    }
                }
                if (selectedText != null) {
                    onCategorySelected("전공/과제", selectedText)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            return view
        }
    }

    class BottomSheet4(private val onCategorySelected: (String, String) -> Unit) : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.bottom_sheet_three, container, false)
            val radioButtonIds = mapOf(
                R.id.transition to "편입",
                R.id.company to "기업",
                R.id.publicoffice to "공무원",
                R.id.eduemploy to "임용",
                R.id.hospital to "병원",
                R.id.etc to "기타"
            )

            val setButton = view.findViewById<AppCompatButton>(R.id.setting)
            setButton.setOnClickListener {
                var selectedText: String? = null
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedText = text
                        break
                    }
                }
                if (selectedText != null) {
                    onCategorySelected("면접", selectedText)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            return view
        }
    }

    class BottomSheet5(private val onCategorySelected: (String, String) -> Unit) : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.bottom_sheet_five, container, false)
            val radioButtonIds = mapOf(
                R.id.ProfessionalEngineer to "기사/기능사",
                R.id.language to "어학",
                R.id.computer to "컴퓨터활용능력",
                R.id.khistory to "한국사",
                R.id.etc to "기타"
            )

            val setButton = view.findViewById<AppCompatButton>(R.id.setting)
            setButton.setOnClickListener {
                var selectedText: String? = null
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedText = text
                        break
                    }
                }
                if (selectedText != null) {
                    onCategorySelected("자격증", selectedText)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            return view
        }
    }

    class BottomSheet6(private val onCategorySelected: (String, String) -> Unit) : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.bottom_sheet_four, container, false)
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

            val setButton = view.findViewById<AppCompatButton>(R.id.setting)
            setButton.setOnClickListener {
                var selectedText: String? = null
                for ((radioButtonId, text) in radioButtonIds) {
                    val radioButton = view.findViewById<RadioButton>(radioButtonId)
                    if (radioButton.isChecked) {
                        selectedText = text
                        break
                    }
                }
                if (selectedText != null) {
                    onCategorySelected("자소서", selectedText)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            return view
        }
    }
}
