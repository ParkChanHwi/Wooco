package com.odal.wooco

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.odal.wooco.datamodels.CoachCategoryDataModel
import com.odal.wooco.datamodels.CoachDataModel

class Menti_coach_introduceActivity : AppCompatActivity() {
    private lateinit var receiverName: String
    private lateinit var receiverUid: String
    private lateinit var receiverSchool: String
    private lateinit var receiverInterest: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coach_introduce)

        // Intent로부터 UID를 가져옴
        val uid = intent.getStringExtra("uid") ?: ""
        Log.d(TAG, "UID: $uid")
        // UID를 사용하여 데이터베이스에서 해당 코치의 카테고리 정보를 가져옴
        getCoachCategoryInfoFromDatabase(uid)
        getCoachInfoFromDatabase(uid)

        // ArrowImageView 클릭 리스너
        val ArrowImageView: ImageView = findViewById(R.id.ArrowImageView)
        ArrowImageView.setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

        val coachName: TextView = findViewById(R.id.user_class_text2)
        val coachSchool: TextView = findViewById(R.id.user_class_text3)
        val coachInterest: TextView = findViewById(R.id.user_class_text4)

        receiverUid = intent.getStringExtra("uid").toString()
        receiverName = intent.getStringExtra("name").toString()
        receiverSchool = intent.getStringExtra("school").toString()
        receiverInterest = intent.getStringExtra("interest").toString()

        coachName.text = receiverName
        coachSchool.text = receiverSchool
        coachInterest.text = receiverInterest


        // 예약하기 버튼에 클릭 리스너를 설정
        val appointmentBtn: Button = findViewById(R.id.appointment_button)
        appointmentBtn.setOnClickListener {
            // 인텐트를 생성. Menti_Reserve1 액티비티를 목적지로 지정
            val intent = Intent(this, Menti_Reserve1::class.  java).apply {
                // 코치 정보를 인텐트에 추가
                putExtra("coach_uid", receiverUid)         // 코치의 고유 식별자(uid)
                putExtra("coach_name", receiverName)       // 코치의 이름
                putExtra("coach_school", receiverSchool)   // 코치의 학교 또는 회사 정보
                putExtra("coach_interest", receiverInterest) // 코치의 특기 또는 관심사
            }
            // 액티비티를 시작. MentiReserve 액티비티로 이동하면서 코치 정보를 함께 전달.
            startActivity(intent)
        }

        // 상담하기 버튼 클릭 리스너
        val consultBtn: Button = findViewById(R.id.consult_button)
        consultBtn.setOnClickListener {
            // 상담 목록 업데이트 함수 호출
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("chat_type", 1)  // 1 for consultation
            }
            startActivity(intent)
        }

        val coachIntro1: TextView = findViewById(R.id.coach_category)
        val coachIntro2: TextView = findViewById(R.id.calss_inform)
        val coachIntro3: TextView = findViewById(R.id.review)

        coachIntro1.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        coachIntro2.setOnClickListener {
            val intent2 = Intent(this, Menti_coach_introduceActivity2::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("school", receiverSchool)
                putExtra("interest", receiverInterest)
            }
            startActivity(intent2)
        }

        coachIntro3.setOnClickListener {
            val intent3 = Intent(this, Menti_coach_introduceActivity3::class.java).apply {
                putExtra("uid", receiverUid)
                putExtra("name", receiverName)
                putExtra("school", receiverSchool)
                putExtra("interest", receiverInterest)
            }
            startActivity(intent3)
        }
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
                    val coachCategoryDataModel = CoachCategoryDataModel(category, detail)
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

    private fun getCoachInfoFromDatabase(uid: String) {
        // Firebase Realtime Database의 "coaches" 노드에서 해당 uid의 코치 정보를 가져옵니다.
        val databaseReference = FirebaseDatabase.getInstance().getReference("coaches").child(uid)

        // ValueEventListener를 추가하여 데이터를 가져옵니다.
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // dataSnapshot에서 CoachDataModel에 해당하는 정보를 가져와서 변수에 저장합니다.
                val coachData = dataSnapshot.getValue(CoachDataModel::class.java)

                // coachData가 null이 아닌 경우에만 변수에 저장합니다.
                coachData?.let {
                    receiverUid = it.uid ?: ""
                    receiverName = it.name ?: ""
                    receiverSchool = it.school ?: ""
                    receiverInterest = it.interest ?: ""

                    // 가져온 정보를 UI에 표시하는 함수를 호출합니다.
                    Log.d("coach", receiverName)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터베이스에서 정보를 가져오는 도중 에러가 발생한 경우 처리합니다.
                Log.e(TAG, "Error fetching coach data: ${databaseError.message}")
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
