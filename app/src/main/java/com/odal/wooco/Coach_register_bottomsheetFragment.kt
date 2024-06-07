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
                    // `coach_register_item.xml`에 값을 표시하는 함수 호출
                    updateCoachRegisterItem(selectedText)
                } else {
                    // 선택된 라디오 버튼이 없을 경우 처리
                    Toast.makeText(requireContext(), "라디오 버튼이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            return view
        }

        private fun updateCoachRegisterItem(selectedText: String) {
            // 여기에서 `coach_register_item.xml`의 뷰에 접근하여 값을 설정합니다.
            val activity = requireActivity()

            // `coach_register_item.xml`의 뷰에 접근
            val category1TextView = activity.findViewById<TextView>(R.id.category1)
            val category2TextView = activity.findViewById<TextView>(R.id.category2)

            category1TextView.text = "진로/기타고민"
            category2TextView.text = selectedText // 필요에 따라 수정

            // `coach_register_item.xml`의 뷰를 업데이트합니다.
            // 이 예제에서는 단순히 텍스트를 설정하지만, 실제로는 다른 로직이 필요할 수 있습니다.
        }
    }


    class BottomSheet3 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.bottom_sheet_two, container, false)
        }
    }

    class BottomSheet4 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.bottom_sheet_three, container, false)
        }
    }

    class BottomSheet5 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.bottom_sheet_four, container, false)
        }
    }

    class BottomSheet6 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.bottom_sheet_five, container, false)
        }
    }
}
