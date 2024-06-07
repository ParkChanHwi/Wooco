package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Coach_registerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_register)

        val recyclerView: RecyclerView = findViewById(R.id.coach_register1_view)

        val items = listOf(
            Coach_registerActivityAdapter.Item("카테고리", "세부카테고리")
        )
        val adapter = Coach_registerActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val bottomSheet = Coach_register_bottomsheetFragment() // 누르면 카테고리가 뜸
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
}
