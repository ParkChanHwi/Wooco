package com.odal.wooco

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val checkAllTerms: CheckBox = findViewById(R.id.all_check)
        val checkTerms: CheckBox = findViewById(R.id.check1)
        val checkPrivacy: CheckBox = findViewById(R.id.check2)
        val checkPush: CheckBox = findViewById(R.id.check3)
        val buttonCommit: Button = findViewById(R.id.button_commit)

        // 전체 약관 동의 체크박스 클릭 시 나머지 체크박스 상태 변경
        checkAllTerms.setOnCheckedChangeListener { _, isChecked ->
            checkTerms.isChecked = isChecked
            checkPrivacy.isChecked = isChecked
            checkPush.isChecked = isChecked
        }

        // 회원가입 완료 버튼 클릭 시
        buttonCommit.setOnClickListener {
            if (checkTerms.isChecked && checkPrivacy.isChecked) {
                // MainActivity로 이동
                //val intent = Intent(this, MainActivity::class.java)
                //startActivity(intent)
                Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()
            } else {
                // 필수 약관 동의 체크박스가 체크되지 않은 경우
                Toast.makeText(this, "필수 약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
