package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Coach_registerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_register)

        val recyclerView: RecyclerView = findViewById(R.id.coach_register1_view)
        val recyclerView2: RecyclerView = findViewById(R.id.coach_register2_view)


        val items = listOf(
            Coach_registerActivityAdapter.Item("카테고리", "세부카테고리"),
            Coach_registerActivityAdapter.Item("카테고리", "세부카테고리"),
            Coach_registerActivityAdapter.Item("카테고리", "세부카테고리"),
            Coach_registerActivityAdapter.Item("카테고리", "세부카테고리"),
            Coach_registerActivityAdapter.Item("카테고리", "세부카테고리"),
            Coach_registerActivityAdapter.Item("카테고리", "세부카테고리"),
        )
        val adapter = Coach_registerActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val items2 = listOf(
            Coach_registerActivityAdapter2.Item(R.drawable.document, R.drawable.document, R.drawable.document),
            Coach_registerActivityAdapter2.Item(R.drawable.document, R.drawable.document, R.drawable.document),
            Coach_registerActivityAdapter2.Item(R.drawable.document, R.drawable.document, R.drawable.document)
        )
        val adapter2 = Coach_registerActivityAdapter2(items2)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView2.adapter = adapter2
    }
}