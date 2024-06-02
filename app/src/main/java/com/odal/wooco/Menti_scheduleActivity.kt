package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_scheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_schedule)

        val recyclerView: RecyclerView = findViewById(R.id.menti_schedule_recycler_view)

        val items = listOf(
            Menti_sheduleActivityAdapter.Item("차우코", "2024-05-31"),
            Menti_sheduleActivityAdapter.Item("별명 2", "date2"),
            Menti_sheduleActivityAdapter.Item("별명 3", "date3"),
            Menti_sheduleActivityAdapter.Item("별명 4", "date4"),
            Menti_sheduleActivityAdapter.Item("별명 5", "date5"),
            Menti_sheduleActivityAdapter.Item("별명 6", "date6"),
            Menti_sheduleActivityAdapter.Item("별명 7", "date7"),
            Menti_sheduleActivityAdapter.Item("별명 8", "date8"),
            // Add more items as needed

        )
        val adapter = Menti_sheduleActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}