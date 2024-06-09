package com.odal.wooco

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Menti_Reserve1_bottom_sheet1 : BottomSheetDialogFragment() {

    private var listener: OnCategorySelectedListener? = null

    interface OnCategorySelectedListener {
        fun onCategorySelected(category: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategorySelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCategorySelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_one, container, false)

        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val settingButton: Button = view.findViewById(R.id.setting)

        settingButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton: RadioButton = view.findViewById(selectedRadioButtonId)
                val selectedCategory = "진로/기타고민 = ${selectedRadioButton.text}"
                listener?.onCategorySelected(selectedCategory)
                dismiss()
            }
        }

        return view
    }
}

class Menti_Reserve1_bottom_sheet2 : BottomSheetDialogFragment() {

    private var listener: OnCategorySelectedListener? = null

    interface OnCategorySelectedListener {
        fun onCategorySelected(category: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategorySelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCategorySelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_two, container, false)

        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val settingButton: Button = view.findViewById(R.id.setting)

        settingButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton: RadioButton = view.findViewById(selectedRadioButtonId)
                val selectedCategory = "전공/과제 = ${selectedRadioButton.text}"
                listener?.onCategorySelected(selectedCategory)
                dismiss()
            }
        }

        return view
    }
}

class Menti_Reserve1_bottom_sheet3 : BottomSheetDialogFragment() {

    private var listener: OnCategorySelectedListener? = null

    interface OnCategorySelectedListener {
        fun onCategorySelected(category: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategorySelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCategorySelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_three, container, false)

        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val settingButton: Button = view.findViewById(R.id.setting)

        settingButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton: RadioButton = view.findViewById(selectedRadioButtonId)
                val selectedCategory = "면접 = ${selectedRadioButton.text}"
                listener?.onCategorySelected(selectedCategory)
                dismiss()
            }
        }

        return view
    }
}


class Menti_Reserve1_bottom_sheet4 : BottomSheetDialogFragment() {

    private var listener: OnCategorySelectedListener? = null

    interface OnCategorySelectedListener {
        fun onCategorySelected(category: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategorySelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCategorySelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_five_hj, container, false)

        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val settingButton: Button = view.findViewById(R.id.setting)

        settingButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton: RadioButton = view.findViewById(selectedRadioButtonId)
                val selectedCategory = "자소서 = ${selectedRadioButton.text}"
                listener?.onCategorySelected(selectedCategory)
                dismiss()
            }
        }

        return view
    }
}

class Menti_Reserve1_bottom_sheet5 : BottomSheetDialogFragment() {

    private var listener: OnCategorySelectedListener? = null

    interface OnCategorySelectedListener {
        fun onCategorySelected(category: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategorySelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCategorySelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_four_hj, container, false)

        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val settingButton: Button = view.findViewById(R.id.setting)

        settingButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton: RadioButton = view.findViewById(selectedRadioButtonId)
                val selectedCategory = "자격증 = ${selectedRadioButton.text}"
                listener?.onCategorySelected(selectedCategory)
                dismiss()
            }
        }

        return view
    }
}
