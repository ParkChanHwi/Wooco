package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.odal.wooco.datamodels.CoachCategoryDataModel
import com.odal.wooco.datamodels.CoachDataModel
import com.odal.wooco.datamodels.ReserveDataModel
import java.text.SimpleDateFormat
import java.util.*

class CoachList : AppCompatActivity(), MyBottomSheetDialogFragment.FilterCriteriaListener {

    private lateinit var database: DatabaseReference
    private lateinit var coachAdapter: Coach_Adapter
    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<CoachDataModel>()

    private var selectedCategory: String? = null
    private var selectedSubcategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coachlist)

        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)
        val kategoribtn: Button = findViewById(R.id.kategori)

        // 벨 버튼 클릭 시 coach_menti_request 이동
        val bellBtn: RelativeLayout = findViewById(R.id.bell)
        bellBtn.setOnClickListener {
            val intent = Intent(this, Coach_menti_request::class.java)
            startActivity(intent)
        }

        // 필터 버튼들 클릭 시 BottomSheetDialogFragment 표시
        val filterButtons = listOf(
            R.id.kategori1 to MyBottomSheetDialogFragment.BottomSheet1(),
            R.id.kategori2 to MyBottomSheetDialogFragment.BottomSheet2(),
            R.id.kategori3 to MyBottomSheetDialogFragment.BottomSheet3(),
            R.id.kategori4 to MyBottomSheetDialogFragment.BottomSheet4(),
            R.id.kategori5 to MyBottomSheetDialogFragment.BottomSheet5(),
            R.id.kategori6 to MyBottomSheetDialogFragment.BottomSheet6()
        )

        filterButtons.forEach { (buttonId, bottomSheet) ->
            findViewById<Button>(buttonId).setOnClickListener {
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
        }

        // Firebase Database 초기화
        database = FirebaseDatabase.getInstance().reference

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.coachlist_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        coachAdapter = Coach_Adapter(itemList, this)
        recyclerView.adapter = coachAdapter

        // 과거 예약 정보를 이동하고 삭제하는 함수 호출
        moveToFinishClassInfoForPastReservations()

        // 초기 데이터 로드 (필터 적용 여부에 따라 다르게 로드)
        loadFilteredData()

        // 네비게이션 버튼들 클릭 시 동작
        homeBtn.setOnClickListener {
            Toast.makeText(this, "현재 화면입니다.", Toast.LENGTH_SHORT).show()
        }

        chatBtn.setOnClickListener {
            val intent = Intent(this, Menti_Classlist::class.java)
            startActivity(intent)
        }

        calBtn.setOnClickListener {
            val intent = Intent(this, Menti_scheduleActivity::class.java)
            startActivity(intent)
        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }

        kategoribtn.setOnClickListener {
            // 필터를 초기화하고 새로운 데이터를 로드합니다.
            resetFiltersAndReloadData()
        }
    }

    // 필터와 데이터를 초기화하고 새로 불러오는 함수
    private fun resetFiltersAndReloadData() {
        // 필터 상태 초기화
        selectedCategory = null
        selectedSubcategory = null

        // 코치 목록 초기화
        itemList.clear()
        coachAdapter.notifyDataSetChanged()

        // 초기 상태로 데이터 재로드
        loadAllCoaches() // 전체 코치 데이터를 로드하거나, 특정 초기 상태 데이터 로드를 구현
    }

    override fun onFilterCriteriaSelected(category: String, subcategory: String?) {
        selectedCategory = category
        selectedSubcategory = subcategory
        loadFilteredData()
    }

    private fun loadFilteredData() {
        val localCategory = selectedCategory
        val localSubcategory = selectedSubcategory

        if (localCategory == "대학교" && localSubcategory != null) {
            // 대학교 카테고리로 필터링할 때는 학교 이름으로 직접 필터링
            Log.d("CoachList", "Filtering coaches by school: $localSubcategory")
            filterCoachesBySchool(localSubcategory)
        } else if (localCategory != null && localSubcategory != null) {
            // 일반 카테고리로 필터링
            Log.d("CoachList", "Loading filtered data with category: $localCategory and subcategory: $localSubcategory")
            filterCoaches(localCategory, localSubcategory)
        } else if (localCategory != null) {
            // 서브카테고리가 null인 경우 일반적인 카테고리만 필터링
            Log.d("CoachList", "Subcategory is null, loading all coaches for category $localCategory")
            filterCoachesByCategoryOnly(localCategory)
        } else {
            // 모든 코치 로딩
            loadAllCoaches()
        }
    }

    private fun filterCoachesBySchool(schoolName: String) {
        val coachInfoRef = database.child("coachInfo")
        coachInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val filteredCoachUids = mutableSetOf<String>()
                snapshot.children.forEach { dataSnapshot ->
                    val coach = dataSnapshot.getValue(CoachDataModel::class.java)
                    if (coach != null && coach.school == schoolName && coach.uid != null) {
                        Log.d("CoachList", "Adding coach by school: ${coach.uid}")
                        filteredCoachUids.add(coach.uid)
                    }
                }
                if (filteredCoachUids.isNotEmpty()) {
                    loadCoachesByUid(filteredCoachUids)
                } else {
                    Log.d("CoachList", "No coaches found for the school: $schoolName")
                    itemList.clear()
                    coachAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error during loading coaches by school: ${error.message}")
            }
        })
    }

    private fun filterCoaches(category: String, subcategory: String) {
        val coachCategoryRef = database.child("coachCategoryRef") // 수정된 경로

        Log.d("CoachList", "Querying database at path: coachCategoryRef")
        coachCategoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val filteredCoachUids = mutableSetOf<String>()

                snapshot.children.forEach { userSnapshot ->
                    userSnapshot.child("category").children.forEach { categoryEntry ->
                        val coachUid = categoryEntry.child("coachUid").getValue(String::class.java)
                        val detail = categoryEntry.child("detail").getValue(String::class.java)
                        val categoryValue = categoryEntry.child("category").getValue(String::class.java)

                        Log.d("CoachList", "Checking: CategoryValue=$categoryValue, Detail=$detail, CoachUID=$coachUid")
                        if (categoryValue == category && detail == subcategory && coachUid != null) {
                            filteredCoachUids.add(coachUid)
                            Log.d("CoachList", "Added coach UID: $coachUid")
                        } else {
                            Log.d("CoachList", "Mismatch or null UID for category: $categoryValue and detail: $detail")
                        }
                    }
                }

                Log.d("CoachList", "Filtered coach UIDs: $filteredCoachUids")
                if (filteredCoachUids.isNotEmpty()) {
                    loadCoachesByUid(filteredCoachUids)
                } else {
                    Log.d("CoachList", "No coaches found for the specified filters")
                    itemList.clear()
                    coachAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error: ${error.message}")
            }
        })
    }

    private fun loadCoachesByUid(coachUids: Set<String>) {
        val coachInfoRef = database.child("coachInfo")

        Log.d("CoachList", "Loading all coach data from Firebase")
        coachInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                snapshot.children.forEach { dataSnapshot ->
                    val coach = dataSnapshot.getValue(CoachDataModel::class.java)
                    if (coach != null && coach.uid != null && coachUids.contains(coach.uid)) {
                        coach.starScore = 0.0  // 기본값 0으로 설정
                        itemList.add(coach)
                    }
                }
                // 코치 목록을 별점 순으로 정렬 및 업데이트
                calculateStarScoresAndSortCoaches()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error during loading coaches by UID: ${error.message}")
            }
        })
    }

    private fun loadAllCoaches() {
        Log.d("CoachList", "Loading all coaches")
        database.child("coachInfo").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (dataSnapshot in snapshot.children) {
                    val coach = dataSnapshot.getValue(CoachDataModel::class.java)
                    if (coach != null) {
                        coach.starScore = 0.0  // 기본값 0으로 설정
                        itemList.add(coach)
                    }
                }
                // 코치 목록을 별점 순으로 정렬 및 업데이트
                calculateStarScoresAndSortCoaches()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error during loading all coaches: ${error.message}")
            }
        })
    }

    private fun calculateStarScoresAndSortCoaches() {
        val reviewsRef = database.child("reviews")
        val totalCoaches = itemList.size
        var processedCoaches = 0

        if (totalCoaches == 0) {
            Log.d("CoachList", "No coaches to process for star scores.")
            coachAdapter.notifyDataSetChanged()
            return
        }

        // 각 코치의 평균 별점을 계산
        for (coach in itemList) {
            val coachUid = coach.uid ?: continue

            reviewsRef.orderByChild("coachUid").equalTo(coachUid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var totalStars = 0.0
                        var reviewCount = 0

                        for (reviewSnapshot in snapshot.children) {
                            val stars = reviewSnapshot.child("stars").getValue(Double::class.java)
                            if (stars != null) {
                                totalStars += stars
                                reviewCount++
                            }
                        }

                        if (reviewCount > 0) {
                            coach.starScore = totalStars / reviewCount
                        } else {
                            coach.starScore = 0.0  // 리뷰가 없는 경우 기본값 유지
                        }

                        processedCoaches++
                        Log.d("CoachList", "Processed coach UID: $coachUid, Star Score: ${coach.starScore}")

                        // 모든 코치의 별점이 계산되면 정렬하고 어댑터를 업데이트
                        if (processedCoaches == totalCoaches) {
                            // 코치 목록을 별점 순으로 내림차순 정렬
                            itemList.sortByDescending { it.starScore }
                            coachAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Firebase", "Error calculating star scores for UID $coachUid: ${error.message}")
                        processedCoaches++

                        // 에러가 발생해도 다른 코치를 처리하도록 함
                        if (processedCoaches == totalCoaches) {
                            itemList.sortByDescending { it.starScore }
                            coachAdapter.notifyDataSetChanged()
                        }
                    }
                })
        }
    }

    private fun filterCoachesByCategoryOnly(category: String) {
        val coachCategoryRef = database.child("coachCategoryRef")
        coachCategoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val filteredCoachUids = mutableSetOf<String>()
                for (dataSnapshot in snapshot.children) {
                    val categoryData = dataSnapshot.getValue(CoachCategoryDataModel::class.java)
                    if (categoryData != null && categoryData.category == category) {
                        categoryData.coachUid?.let { nonNullableCoachUid ->
                            filteredCoachUids.add(nonNullableCoachUid)
                        }
                    }
                }
                loadCoachesByUid(filteredCoachUids)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error: ${error.message}")
            }
        })
    }

    private fun moveToFinishClassInfoForPastReservations() {
        val reserveInfoRef = database.child("reserveInfo")
        val finishClassInfoRef = database.child("finishClassInfo")
        val currentTime = Date(System.currentTimeMillis())
        Log.d("CoachList", "Current time: $currentTime")

        reserveInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val reserve = dataSnapshot.getValue(ReserveDataModel::class.java)
                    reserve?.let {
                        val reserveTimeString = it.reserve_time
                        if (reserveTimeString != null) {
                            val reserveTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(reserveTimeString)
                            Log.d("CoachList", "reserveTime: $reserveTime")

                            if (reserveTime != null && reserveTime.before(currentTime)) {
                                val reserveId = dataSnapshot.key!!
                                finishClassInfoRef.child(reserveId).setValue(it).addOnSuccessListener {
                                    reserveInfoRef.child(reserveId).removeValue().addOnSuccessListener {
                                        Log.d("CoachList", "Moved reservation to finishClassInfo: $reserveId")
                                    }.addOnFailureListener { e ->
                                        Log.e("CoachList", "Failed to remove reservation: ${e.message}")
                                    }
                                }.addOnFailureListener { e ->
                                    Log.e("CoachList", "Failed to move reservation: ${e.message}")
                                }
                            } else {
                                // Reserve time is not before current time, so do nothing or handle as needed
                                Log.d("CoachList", "Reservation time is not before current time: $reserveTime")
                            }
                        } else {
                            Log.e("CoachList", "reserveTime is null for reservation: ${dataSnapshot.key}")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CoachList", "Database error: ${error.message}")
            }
        })
    }
}
