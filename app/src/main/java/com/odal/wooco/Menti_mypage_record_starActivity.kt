package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_mypage_record_starActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_mypage_record_star)

        val recyclerView: RecyclerView = findViewById(R.id.record_star_recycler_view)

        val items = listOf(
            Menti_mypage_record_starActivityAdapter.Item("차오달", "강원대 00학과", "자격증 - 기사/기능사")


        )

        val adapter = Menti_mypage_record_starActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


}
