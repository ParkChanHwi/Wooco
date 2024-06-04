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

        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = view.findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton.text.toString()

            when (checkedId) {
                R.id.radioButton1 -> {
                    BottomSheet2().show(parentFragmentManager, "BottomSheet2")
                }
                R.id.radioButton2 -> {
                    BottomSheet3().show(parentFragmentManager, "BottomSheet3")
                }
                R.id.radioButton3 -> {
                    BottomSheet4().show(parentFragmentManager, "BottomSheet4")
                }
                R.id.radioButton4 -> {
                    BottomSheet5().show(parentFragmentManager, "BottomSheet5")
                }
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
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet, container, false)
        }
    }

    class BottomSheet3 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet2, container, false)
        }
    }

    class BottomSheet4 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet3, container, false)
        }
    }

    class BottomSheet5 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet4, container, false)
        }
    }

    class BottomSheet6 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet5, container, false)
        }
    }
}
