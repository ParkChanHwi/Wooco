package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        },3000)
    }
}
// 로그인이 되어있으면 메인 화면으로, 로그인이 안되어있으면 로그인 화면으로 이동하게끔 <코틀린 강의에 방법 있음>
// 앱 쓸때마다 로그인하면 너무 접근성이 떨어질 것 같음