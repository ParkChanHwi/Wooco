package com.odal.wooco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
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
            return inflater.inflate(R.layout.bottom_sheet_one, container, false)
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
