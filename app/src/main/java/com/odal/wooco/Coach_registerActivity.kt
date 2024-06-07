package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.odal.wooco.datamodels.CoachCategoryDataModel
import com.odal.wooco.utils.FirebaseAuthUtils
import com.odal.wooco.datamodels.CoachDataModel

class Coach_registerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.coach_register)

        val nameEditText: EditText = findViewById(R.id.editText)
        val schoolEditText: EditText = findViewById(R.id.editText2)
        val departmentEditText: EditText = findViewById(R.id.editText3)

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

        val nextButton: Button = findViewById(R.id.next_myself)
        nextButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val school = schoolEditText.text.toString()
            val department = departmentEditText.text.toString()
            val uid = FirebaseAuthUtils.getUid()
            val category1 = findViewById<TextView>(R.id.category1).text.toString()
            val category2 = findViewById<TextView>(R.id.category2).text.toString()


                // Firebase에 데이터 저장 로직
                val coachCategory = CoachCategoryDataModel(
                    coachUid = uid,
                    coachName = name,
                    category = category1,
                    detail = category2
                )

                val databaseReference = Firebase.database.reference
                val coachCategoryRef = databaseReference.child("coachCategoryRef").child(uid).child("category")
                coachCategoryRef.setValue(coachCategory)


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
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, Coach_myselfActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("school", school)
            intent.putExtra("interest", department)
            startActivity(intent)
        }
    }
}
