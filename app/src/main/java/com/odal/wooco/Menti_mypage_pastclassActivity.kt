package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_mypage_pastclassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_mypage_pastclass)

        val recyclerView: RecyclerView = findViewById(R.id.pastclass_recycler_view)

        val items = listOf(
           Menti_mypage_pastclassActivityAdapter.Item("차우코", "강원대 00학과", "2024.06.05"),
            Menti_mypage_pastclassActivityAdapter.Item("차우코", "강원대 00학과", ""),
            Menti_mypage_pastclassActivityAdapter.Item("차우코", "강원대 00학과", "")

        )

        val adapter = Menti_mypage_pastclassActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    }
