package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coach_introduce3)

        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)

        val items = listOf(
            Menti_coach_introduceActivity3Adapter.Item("차우코", "2024-05-31", "감사합니다"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 2", "date2","review"),
            Menti_coach_introduceActivity3Adapter.Item("별명 8", "date8","review"),
            // Add more items as needed

        )
        val adapter = Menti_coach_introduceActivity3Adapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}