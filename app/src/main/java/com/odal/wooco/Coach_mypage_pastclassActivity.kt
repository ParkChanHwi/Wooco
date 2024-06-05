package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Coach_mypage_pastclassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_mypage_pastclass)

        val recyclerView: RecyclerView = findViewById(R.id.coach_pastclass_recycler_view)

        val items = listOf(
            Coach_mypage_pastclassActivityAdapter.Item("차우코", "2024.06.05"),
            Coach_mypage_pastclassActivityAdapter.Item("차우코", ""),
            Coach_mypage_pastclassActivityAdapter.Item("차우코", "")

        )

        val adapter = Coach_mypage_pastclassActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


}
