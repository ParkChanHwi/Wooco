package com.odal.wooco

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.odal.wooco.datamodels.UserDataModel
import com.odal.wooco.utils.FirebaseRef

class RegisterActivity : AppCompatActivity() {

    private val Tag = "RegisterActivity"

    private lateinit var auth: FirebaseAuth

    private lateinit var nicknameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var pwEditText: EditText
    private lateinit var pwchEditText: EditText

    private var uid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        nicknameEditText = findViewById(R.id.id_register_name)
        idEditText = findViewById(R.id.id_register_id)
        pwEditText = findViewById(R.id.id_register_pw)
        pwchEditText = findViewById(R.id.id_register_pwch)

        // 뒤로가기 버튼
        val arrowImageView: ImageView = findViewById(R.id.ArrowImageView)
        arrowImageView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registerBtn = findViewById<Button>(R.id.button_commit) // 회원가입 버튼
        registerBtn.setOnClickListener {
            val nickname = nicknameEditText.text.toString()
            val id = idEditText.text.toString()
            val pw = pwEditText.text.toString()
            val pwch = pwchEditText.text.toString()

            if (nickname.isBlank() || id.isBlank() || pw.isBlank() || pwch.isBlank()) {
                Toast.makeText(this, "모든 필드를 채워주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (pw != pwch) {
                Toast.makeText(this, "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(id, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        uid = user?.uid.toString()

                        val userModel = UserDataModel(
                            uid,
                            nickname
                        )

                        FirebaseRef.userInfoRef.child(uid).setValue(userModel)
                            .addOnSuccessListener {
                                // 회원가입 완료시 메인화면으로 이동
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error writing to database", e)
                                Toast.makeText(this, "데이터베이스 저장 실패", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
