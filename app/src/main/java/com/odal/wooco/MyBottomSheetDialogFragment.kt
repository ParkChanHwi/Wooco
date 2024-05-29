package com.odal.wooco

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일을 인플레이트합니다.
        Log.d("MyBottomSheetDialogFragment", "onCreateView called")
        return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet, container, false)
    }
}

// 아래 bottom sheet 띄우려다가 실패한 코드