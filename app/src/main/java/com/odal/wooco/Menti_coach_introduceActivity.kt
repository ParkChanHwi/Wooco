package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coach_introduce)

        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)

        val items = listOf(
            Menti_coach_introduceActivityAdapter.Item("카테고리", "세부카테고리"),
            Menti_coach_introduceActivityAdapter.Item("카테고리", "세부카테고리"),
            Menti_coach_introduceActivityAdapter.Item("카테고리", "세부카테고리"),
            Menti_coach_introduceActivityAdapter.Item("카테고리", "세부카테고리"),
            Menti_coach_introduceActivityAdapter.Item("카테고리", "세부카테고리"),
            Menti_coach_introduceActivityAdapter.Item("카테고리", "세부카테고리"),




            // Add more items as needed

        )
        val adapter = Menti_coach_introduceActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}