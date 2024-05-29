package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // val database = Firebase.database
        // val myRef =  database.getReference("message")
        // myRef.setValue("Success")
        setContentView(R.layout.menti_coachlist)

        findViewById<Button>(R.id.kategori1).setOnClickListener {
            val intent = Intent(this, CoachList::class.java)
            startActivity(intent)
        }

    }
}