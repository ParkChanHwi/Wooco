package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Coachschedule : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_schedule)

        val recyclerView: RecyclerView = findViewById(R.id.coach_schedule_recycler_view)

        val items = listOf(
            CoachScheduleActivityAdapter.Item("차우코", "2024-05-31"),
            CoachScheduleActivityAdapter.Item("별명 2", "date2"),
            CoachScheduleActivityAdapter.Item("별명 3", "date3"),
            CoachScheduleActivityAdapter.Item("별명 4", "date4"),
            CoachScheduleActivityAdapter.Item("별명 5", "date5"),
            CoachScheduleActivityAdapter.Item("별명 6", "date6"),
            CoachScheduleActivityAdapter.Item("별명 7", "date7"),
           CoachScheduleActivityAdapter.Item("별명 8", "date8"),
            // Add more items as needed

        )
        val adapter = CoachScheduleActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}