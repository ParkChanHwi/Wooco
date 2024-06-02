package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_coach_introduceActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coach_introduce2)

        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)

        val items = listOf(
            Menti_coach_introduceActivity2Adapter.Item("한 줄 소개", "수업 가능 과목", "차별점","수업 방식")

            // Add more items as needed

        )
        val adapter = Menti_coach_introduceActivity2Adapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}