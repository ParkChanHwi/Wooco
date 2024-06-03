package com.odal.wooco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet, container, false)
        }
    }

    class BottomSheet4 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet2, container, false)
        }
    }

    class BottomSheet5 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet3, container, false)
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

    class BottomSheet7 : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet4, container, false)
        }
        fun showBottomSheet(fragmentManager: FragmentManager, tag: String) {
            val bottomSheet = Coach_register_bottomsheetFragment.newInstance()
            bottomSheet.show(fragmentManager, tag)
        }
    }
    }

