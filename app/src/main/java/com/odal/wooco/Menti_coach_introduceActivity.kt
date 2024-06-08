package com.odal.wooco

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.odal.wooco.datamodels.CoachCategoryDataModel
class Menti_coach_introduceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_coach_introduce)

        // Intent로부터 UID를 가져옴
        val uid = intent.getStringExtra("uid") ?: ""
        Log.d(TAG, "UID: $uid")
        // UID를 사용하여 데이터베이스에서 해당 코치의 카테고리 정보를 가져옴
        getCoachCategoryInfoFromDatabase(uid)
    }

    private fun getCoachCategoryInfoFromDatabase(uid: String) {
        // Firebase Realtime Database에 접근하기 위한 DatabaseReference 인스턴스 생성
        val categoriesRef = FirebaseDatabase.getInstance().getReference("coachCategoryRef").child(uid).child("category")

        // Firebase Realtime Database에서 해당 UID의 카테고리 정보를 가져오는 쿼리 실행
        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 가져온 스냅샷을 순회하면서 카테고리 정보를 추출하여 리스트에 추가
                val coachCategoryInfoList = mutableListOf<CoachCategoryDataModel>()
                for (categorySnapshot in snapshot.children) {
                    val category = categorySnapshot.child("category").getValue(String::class.java) ?: ""
                    Log.d(TAG, "Category: $category")
                    val detail = categorySnapshot.child("detail").getValue(String::class.java) ?: ""
                    Log.d(TAG, "detail: $detail")
                    val coachCategoryDataModel = CoachCategoryDataModel(uid, category, detail)
                    coachCategoryInfoList.add(coachCategoryDataModel)
                }

                // 가져온 카테고리 정보를 이용하여 RecyclerView에 Adapter를 연결하고 업데이트
                updateUI(coachCategoryInfoList)
            }

            override fun onCancelled(error: DatabaseError) {
                // 실패 시 에러 처리
                Log.e(TAG, "Error getting documents: ", error.toException())
            }
        })
    }

    private fun updateUI(coachCategoryInfo: List<CoachCategoryDataModel>) {
        // RecyclerView 참조 가져오기
        val recyclerView: RecyclerView = findViewById(R.id.menti_coach_introduce_recycler_view)

        // RecyclerView에 LinearLayoutManager 설정
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Adapter 생성 및 RecyclerView에 연결
        val adapter = Menti_coach_introduceActivityAdapter(coachCategoryInfo)
        recyclerView.adapter = adapter
    }
}

