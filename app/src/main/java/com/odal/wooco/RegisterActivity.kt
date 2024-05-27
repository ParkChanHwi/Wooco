package com.odal.wooco

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.core.Tag

class RegisterActivity : AppCompatActivity() {

    private val Tag = "RegisterActivity"

    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)
    auth = Firebase.auth

        // 닉네임, ID (중복 확인은 어떻게 할것인지



        //뒤로가기 버튼
        val arrowImageView: ImageView = findViewById(R.id.ArrowImageView)
        arrowImageView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registerBtn = findViewById<Button>(R.id.button_commit) // 회원가입 버튼
        registerBtn.setOnClickListener {
            val id = findViewById<EditText>(R.id.id_register_id)
            val pw = findViewById<EditText>(R.id.id_register_pw)


            auth.createUserWithEmailAndPassword(id.text.toString(), pw.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                       // val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                      //  updateUI(null)
                    }
                }




        }


    }
}
