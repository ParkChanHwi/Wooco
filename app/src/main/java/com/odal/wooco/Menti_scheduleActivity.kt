package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Mentischedule : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_schedule)

        val recyclerView: RecyclerView = findViewById(R.id.menti_schedule_recycler_view)

        val items = listOf(
            MentiSheduleActivityAdapter.Item("차우코", "2024-05-31"),
            MentiSheduleActivityAdapter.Item("별명 2", "date2"),
            MentiSheduleActivityAdapter.Item("별명 3", "date3"),
            MentiSheduleActivityAdapter.Item("별명 4", "date4"),
            MentiSheduleActivityAdapter.Item("별명 5", "date5"),
            MentiSheduleActivityAdapter.Item("별명 6", "date6"),
            MentiSheduleActivityAdapter.Item("별명 7", "date7"),
            MentiSheduleActivityAdapter.Item("별명 8", "date8"),
            // Add more items as needed

        )
        val adapter = MentiSheduleActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}