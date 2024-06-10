package com.odal.wooco

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*
import com.odal.wooco.datamodels.CoachDataModel
import com.odal.wooco.datamodels.ReserveDataModel
import java.text.SimpleDateFormat
import java.util.*

class CoachList : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1
    }

    private lateinit var database: DatabaseReference
    private lateinit var coachAdapter: Coach_Adapter
    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<CoachDataModel>()
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_coachlist)

        setupButtons()
        setupRecyclerView()
        initializeFirebase()
        fetchCoachData()
        moveToFinishClassInfoForPastReservations()
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.kategori1).setOnClickListener {
            showBottomSheet(MyBottomSheetDialogFragment.BottomSheet2(), "SELECTED_CATEGORY1")
        }

        findViewById<Button>(R.id.kategori2).setOnClickListener {
            val bottomSheet2 = MyBottomSheetDialogFragment.BottomSheet2()
            bottomSheet2.setOnCategorySelectedListener(object : MyBottomSheetDialogFragment.OnCategorySelectedListener {
                override fun onCategorySelected(selectedCategory: String) {
                    val set2 = selectedCategory
                    Log.d("set2", set2)
                }
            })
            showBottomSheet(bottomSheet2, "SELECTED_CATEGORY2")
        }


        findViewById<Button>(R.id.kategori3).setOnClickListener {
            val bottomSheet3 = MyBottomSheetDialogFragment.BottomSheet3()
            bottomSheet3.setOnCategorySelectedListener(object : MyBottomSheetDialogFragment.OnCategorySelectedListener {
                override fun onCategorySelected(selectedCategory: String) {
                    val set3 = selectedCategory
                    Log.d("set3", set3)
                }
            })
            showBottomSheet(bottomSheet3, "SELECTED_CATEGORY3")
        }


        findViewById<Button>(R.id.kategori4).setOnClickListener {
            val bottomSheet4 = MyBottomSheetDialogFragment.BottomSheet4()
            bottomSheet4.setOnCategorySelectedListener(object : MyBottomSheetDialogFragment.OnCategorySelectedListener {
                override fun onCategorySelected(selectedCategory: String) {
                    val set4 = selectedCategory
                    Log.d("set4", set4)
                }
            })
            showBottomSheet(bottomSheet4, "SELECTED_CATEGORY4")
        }

        findViewById<Button>(R.id.kategori5).setOnClickListener {
            val bottomSheet5 = MyBottomSheetDialogFragment.BottomSheet5()
            bottomSheet5.setOnCategorySelectedListener(object : MyBottomSheetDialogFragment.OnCategorySelectedListener {
                override fun onCategorySelected(selectedCategory: String) {
                    val set5 = selectedCategory
                    Log.d("set5", set5)
                }
            })
            showBottomSheet(bottomSheet5, "SELECTED_CATEGORY5")
        }

        findViewById<Button>(R.id.kategori6).setOnClickListener {
            val bottomSheet6 = MyBottomSheetDialogFragment.BottomSheet6()
            bottomSheet6.setOnCategorySelectedListener(object : MyBottomSheetDialogFragment.OnCategorySelectedListener {
                override fun onCategorySelected(selectedCategory: String) {
                    val set6 = selectedCategory
                    Log.d("set6", set6)
                }
            })
            showBottomSheet(bottomSheet6, "SELECTED_CATEGORY6")
        }

        setupNavigationButtons()
    }

    private fun setupNavigationButtons() {
        val homeBtn: ImageView = findViewById(R.id.material_sy)
        val chatBtn: ImageView = findViewById(R.id.chat_1)
        val calBtn: ImageView = findViewById(R.id.uiw_date)
        val profileBtn: ImageView = findViewById(R.id.group_513866)

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
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.coachlist_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        coachAdapter = Coach_Adapter(itemList, this)
        recyclerView.adapter = coachAdapter
    }

    private fun initializeFirebase() {
        database = FirebaseDatabase.getInstance().reference
    }

    private fun fetchCoachData() {
        database.child("coachInfo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (dataSnapshot in snapshot.children) {
                    val coach = dataSnapshot.getValue(CoachDataModel::class.java)
                    coach?.let { itemList.add(it) }
                }
                coachAdapter.notifyDataSetChanged()
            }



            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error: ${error.message}")
            }
        })
    }

    private fun showBottomSheet(bottomSheet: BottomSheetDialogFragment, key: String) {
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun moveToFinishClassInfoForPastReservations() {
        val reserveInfoRef = database.child("reserveInfo")
        val finishClassInfoRef = database.child("finishClassInfo")
        val currentTime = Date(System.currentTimeMillis())

        reserveInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val reserve = dataSnapshot.getValue(ReserveDataModel::class.java)
                    reserve?.let {
                        val reserveTime = it.reserve_time?.let { time ->
                            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(time)
                        }
                        if (reserveTime != null && reserveTime.before(currentTime)) {
                            moveReservation(reserve, dataSnapshot.key!!, reserveInfoRef, finishClassInfoRef)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error: ${error.message}")
            }
        })
    }

    private fun moveReservation(reserve: ReserveDataModel, reserveId: String, reserveInfoRef: DatabaseReference, finishClassInfoRef: DatabaseReference) {
        finishClassInfoRef.child(reserveId).setValue(reserve).addOnSuccessListener {
            reserveInfoRef.child(reserveId).removeValue().addOnSuccessListener {
                Log.d("CoachList", "Moved reservation to finishClassInfo: $reserveId")
            }.addOnFailureListener { e ->
                Log.e("CoachList", "Failed to remove reservation: ${e.message}")
            }
        }.addOnFailureListener { e ->
            Log.e("CoachList", "Failed to move reservation: ${e.message}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.extras?.let { bundle ->
                bundle.keySet().forEach { key ->
                    val value = bundle.getString(key)
                    Log.d("Selected Category", "Key: $key, Value: $value")
                    Toast.makeText(this, "선택된 카테고리: $value", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
