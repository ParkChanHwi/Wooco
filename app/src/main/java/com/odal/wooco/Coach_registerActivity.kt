package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.odal.wooco.datamodels.CoachCategoryDataModel
import com.odal.wooco.utils.FirebaseAuthUtils
import com.odal.wooco.datamodels.CoachDataModel

class Coach_registerActivity : AppCompatActivity() {

    private lateinit var adapter: Coach_registerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_register)

        val nameEditText: EditText = findViewById(R.id.editText)
        val schoolEditText: EditText = findViewById(R.id.editText2)
        val departmentEditText: EditText = findViewById(R.id.editText3)

        val recyclerView: RecyclerView = findViewById(R.id.coach_register1_view)
        val items = mutableListOf<Coach_registerActivityAdapter.Item>()
        adapter = Coach_registerActivityAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val bottomSheet = Coach_register_bottomsheetFragment { category, subcategory ->
                adapter.addItem(Coach_registerActivityAdapter.Item(category, subcategory))
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        val nextButton: Button = findViewById(R.id.next_myself)
        nextButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val school = schoolEditText.text.toString()
            val department = departmentEditText.text.toString()
            val uid = FirebaseAuthUtils.getUid()

            if (name.isNotEmpty() && school.isNotEmpty() && department.isNotEmpty()) {
                val coachData = CoachDataModel(
                    uid = uid,
                    name = name,
                    school = school,
                    interest = department
                )

                val databaseReference = FirebaseDatabase.getInstance().getReference("coachInfo")
                databaseReference.child(uid).setValue(coachData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "코치 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "코치 정보 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }

                adapter.getItemList().forEach { item ->
                    val coachCategory = CoachCategoryDataModel(
                        coachUid = uid,
                        coachName = name,
                        category = item.category3,
                        detail = item.category4
                    )

                    val coachCategoryRef = FirebaseDatabase.getInstance().getReference("coachCategoryRef").child(uid).child("category").push()
                    coachCategoryRef.setValue(coachCategory)
                }

                val intent = Intent(this, Coach_myselfActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("school", school)
                intent.putExtra("interest", department)
                startActivity(intent)
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
