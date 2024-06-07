package com.odal.wooco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.odal.wooco.datamodels.CoachCategoryDataModel
import com.odal.wooco.utils.FirebaseAuthUtils


class Coach_register_bottomsheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): Coach_register_bottomsheetFragment {
            return Coach_register_bottomsheetFragment()
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

        // 첫 번째 RadioGroup 설정
        val radioGroup1: RadioGroup = view.findViewById(R.id.radioGroup1)
        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> {
                    BottomSheet2().show(parentFragmentManager, "BottomSheet2")
                }

                R.id.radioButton2 -> {
                    BottomSheet3().show(parentFragmentManager, "BottomSheet3")
                }
            }
            dismiss() // 선택 후 바텀 시트 닫기
        }

        // 두 번째 RadioGroup 설정
        val radioGroup2: RadioGroup = view.findViewById(R.id.radioGroup2)
        radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton3 -> {
                    BottomSheet4().show(parentFragmentManager, "BottomSheet4")
                }

                R.id.radioButton4 -> {
                    BottomSheet5().show(parentFragmentManager, "BottomSheet5")
                }
            }
            dismiss() // 선택 후 바텀 시트 닫기
        }

        // 세 번째 RadioGroup 설정
        val radioGroup3: RadioGroup = view.findViewById(R.id.radioGroup3)
        radioGroup3.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton5 -> {
                    BottomSheet6().show(parentFragmentManager, "BottomSheet6")
                }
            }
            dismiss() // 선택 후 바텀 시트 닫기
        }
    }

    class BottomSheet2 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
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
                    // coach_register_item.xml에 값을 표시하는 함수 호출
                    updateCoachRegisterItem(selectedText)
                    // Firebase에 데이터 저장
                   // saveToFirebase(selectedText)
                } else {
                    // 선택된 라디오 버튼이 없을 경우 처리
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            return view
        }

        private fun updateCoachRegisterItem(selectedText: String) {
            // 여기에서 coach_register_item.xml의 뷰에 접근하여 값을 설정합니다.
            val activity = requireActivity()

            // coach_register_item.xml의 뷰에 접근
            val category1TextView = activity.findViewById<TextView>(R.id.category1)
            val category2TextView = activity.findViewById<TextView>(R.id.category2)

            category1TextView.text = "진로/기타고민"
            category2TextView.text = selectedText // 필요에 따라 수정
        }

    }
}


        class BottomSheet3 : BottomSheetDialogFragment() {
            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
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
                        // coach_register_item.xml에 값을 표시하는 함수 호출
                        updateCoachRegisterItem(selectedText)
                        // Firebase에 데이터 저장
                        // saveToFirebase(selectedText)
                    } else {
                        // 선택된 라디오 버튼이 없을 경우 처리
                        Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                return view
            }

            private fun updateCoachRegisterItem(selectedText: String) {
                // 여기에서 coach_register_item.xml의 뷰에 접근하여 값을 설정합니다.
                val activity = requireActivity()

                // coach_register_item.xml의 뷰에 접근
                val category1TextView = activity.findViewById<TextView>(R.id.category1)
                val category2TextView = activity.findViewById<TextView>(R.id.category2)

                category1TextView.text = "전공/과제"
                category2TextView.text = selectedText // 필요에 따라 수정
            }

        }


        class BottomSheet4 : BottomSheetDialogFragment() {
            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
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
                        // coach_register_item.xml에 값을 표시하는 함수 호출
                        updateCoachRegisterItem(selectedText)
                        // Firebase에 데이터 저장
                        // saveToFirebase(selectedText)
                    } else {
                        // 선택된 라디오 버튼이 없을 경우 처리
                        Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                return view
            }

            private fun updateCoachRegisterItem(selectedText: String) {
                // 여기에서 coach_register_item.xml의 뷰에 접근하여 값을 설정합니다.
                val activity = requireActivity()

                // coach_register_item.xml의 뷰에 접근
                val category1TextView = activity.findViewById<TextView>(R.id.category1)
                val category2TextView = activity.findViewById<TextView>(R.id.category2)
                category1TextView.text = "면접"
                category2TextView.text = selectedText // 필요에 따라 수정
            }
        }

        class BottomSheet5 : BottomSheetDialogFragment() {
            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
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
                        // coach_register_item.xml에 값을 표시하는 함수 호출
                        updateCoachRegisterItem(selectedText)
                        // Firebase에 데이터 저장
                        // saveToFirebase(selectedText)
                    } else {
                        // 선택된 라디오 버튼이 없을 경우 처리
                        Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                return view
            }

            private fun updateCoachRegisterItem(selectedText: String) {
                // 여기에서 coach_register_item.xml의 뷰에 접근하여 값을 설정합니다.
                val activity = requireActivity()

                // coach_register_item.xml의 뷰에 접근
                val category1TextView = activity.findViewById<TextView>(R.id.category1)
                val category2TextView = activity.findViewById<TextView>(R.id.category2)
                category1TextView.text = "자격증"
                category2TextView.text = selectedText // 필요에 따라 수정
            }
        }

        class BottomSheet6 : BottomSheetDialogFragment() {
            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
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
                        // coach_register_item.xml에 값을 표시하는 함수 호출
                        updateCoachRegisterItem(selectedText)
                        // Firebase에 데이터 저장
                        // saveToFirebase(selectedText)
                    } else {
                        // 선택된 라디오 버튼이 없을 경우 처리
                        Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                return view
            }

            private fun updateCoachRegisterItem(selectedText: String) {
                // 여기에서 coach_register_item.xml의 뷰에 접근하여 값을 설정합니다.
                val activity = requireActivity()

                // coach_register_item.xml의 뷰에 접근
                val category1TextView = activity.findViewById<TextView>(R.id.category1)
                val category2TextView = activity.findViewById<TextView>(R.id.category2)
                category1TextView.text = "자소서"
                category2TextView.text = selectedText // 필요에 따라 수정
            }
        }

