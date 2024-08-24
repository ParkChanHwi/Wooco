package com.odal.wooco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menti_mypage_record_starActivity : AppCompatActivity() {

    private lateinit var recordStarRecyclerView: RecyclerView
    private lateinit var recordStarAdapter: Menti_mypage_record_starActivityAdapter
    private val recordStarList = mutableListOf<Menti_mypage_record_starActivityAdapter.Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_mypage_record_star)

        recordStarRecyclerView = findViewById(R.id.record_star_recycler_view)
        recordStarAdapter = Menti_mypage_record_starActivityAdapter(recordStarList, this)
        recordStarRecyclerView.layoutManager = LinearLayoutManager(this)
        recordStarRecyclerView.adapter = recordStarAdapter

        // 데이터 추가 (예제 데이터; 실제 데이터로 교체 필요)
        recordStarList.add(
            Menti_mypage_record_starActivityAdapter.Item(
                name = "차우코",
                school_company = "강원대 00학과",
                interest = "AI 연구",
                coachUid = "coach_uid_123" // 실제 코치 UID로 교체
            )
        )
        // 실제 데이터를 불러와서 recordStarList에 추가하는 로직을 구현해야 합니다.
    }
}
