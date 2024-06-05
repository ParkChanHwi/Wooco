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

    class BottomSheet2() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet, container, false)
        }
    }
    class BottomSheet3() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.menti_univ_fragment_bottom_sheet2, container, false)
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
