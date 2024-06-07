package com.odal.wooco

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.odal.wooco.utils.FirebaseAuthUtils


class SplashActivity : AppCompatActivity() {
    private  val TAG = "splashActivity"
    //private  val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

      //  val uid = auth.currentUser?.uid.toString()
        val uid = FirebaseAuthUtils.getUid()

        if(uid == "null") {  // 로그인이 안되어있는 경우
            Handler().postDelayed({
                val intent = Intent(this, Coach_registerActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            },3000)
        }else {  // 로그인이 되어있는 경우
            Handler().postDelayed({
                val intent = Intent(this, CoachList::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            },3000)
        }


    }
}
