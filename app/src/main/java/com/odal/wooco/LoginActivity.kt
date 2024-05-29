package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

//그냥
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val loginBtn: Button = findViewById(R.id.btn_login)
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val registerTextView: TextView = findViewById(R.id.register) // 회원가입 TextView

        loginBtn.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, CoachList::class.java)
                        startActivity(intent)
                        finish() // 로그인 성공 시 현재 액티비티 종료
                    } else {
                        Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // 회원가입 TextView 클릭 시 RegisterActivity로 이동
        registerTextView.setOnClickListener {
            Log.d("LoginActivity", "Register TextView clicked")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
