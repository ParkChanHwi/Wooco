package com.odal.wooco

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Menti_mypage_woocoinActivity : AppCompatActivity() {
    private lateinit var userInfoRef: DatabaseReference
    private lateinit var userId: String
    private var userName: String? = null
    private lateinit var woocoinTextView: TextView // woocoin 개수를 표시할 TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_mypage_woocoin)

        // 뒤로가기 버튼
        val ArrowImageView: ImageView = findViewById(R.id.ArrowImageView)
        ArrowImageView.setOnClickListener {
            val intent = Intent(this, Menti_mypageActivity::class.java)
            startActivity(intent)
        }

        // 구매 버튼들
        val purchaseBtn_1: Button = findViewById(R.id.woocoin_rectangle4)
        val purchaseBtn_2: Button = findViewById(R.id.woocoin_rectangle5)
        val purchaseBtn_3: Button = findViewById(R.id.woocoin_rectangle6)
        val purchaseBtn_4: Button = findViewById(R.id.woocoin_rectangle7)
        val purchaseBtn_5: Button = findViewById(R.id.woocoin_rectangle8)

        // Intent로 전달된 정보 받기
        userId = intent.getStringExtra("userId") ?: ""
        userName = intent.getStringExtra("userName")

        // Firebase Realtime Database에서 userInfo 참조
        val database = FirebaseDatabase.getInstance()
        userInfoRef = database.getReference("userInfo").child(userId)

        // woocoin 개수를 표시할 TextView 초기화
        woocoinTextView = findViewById(R.id.woocoin_rectangle2)

        // 기존 우코인 값 불러와서 업데이트
        userInfoRef.child("woocoin_buy").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val existingWoocoin = snapshot.getValue(Int::class.java) ?: 0
                // woocoin 개수를 TextView에 표시
                woocoinTextView.text = existingWoocoin.toString()
                // 각 구매 버튼 클릭 시 해당 개수의 우코인을 구매하도록 구현
                purchaseBtn_1.setOnClickListener { showPurchaseConfirmationDialog(1, existingWoocoin) }
                purchaseBtn_2.setOnClickListener { showPurchaseConfirmationDialog(5, existingWoocoin) }
                purchaseBtn_3.setOnClickListener { showPurchaseConfirmationDialog(10, existingWoocoin) }
                purchaseBtn_4.setOnClickListener { showPurchaseConfirmationDialog(15, existingWoocoin) }
                purchaseBtn_5.setOnClickListener { showPurchaseConfirmationDialog(20, existingWoocoin) }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to read existing woocoin value")
            }
        })
    }

    // Function to show purchase confirmation dialog
    private fun showPurchaseConfirmationDialog(woocoinCount: Int, existingWoocoin: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("우코인 $woocoinCount 개를 구매하시겠습니까?")
            .setCancelable(false)
            .setPositiveButton("예", DialogInterface.OnClickListener { dialog, id ->
                // User clicked Yes button
                buyWoocoin(woocoinCount, existingWoocoin)
            })
            .setNegativeButton("아니오", DialogInterface.OnClickListener { dialog, id ->
                // User clicked No button
                dialog.cancel()
            })

        // Create and show the dialog
        val alert = dialogBuilder.create()
        alert.setTitle("구매 확인")
        alert.show()
    }

    private fun buyWoocoin(woocoinCount: Int, existingWoocoin: Int) {
        val newWoocoin = existingWoocoin + woocoinCount
        userInfoRef.child("woocoin_buy").setValue(newWoocoin)
            .addOnSuccessListener {
                // 우코인을 성공적으로 구매한 경우, TextView 업데이트
                woocoinTextView.text = newWoocoin.toString()

                val intent = Intent(this, Menti_mypage_woocoinActivity::class.java).apply {
                    putExtra("woocoin_buy", newWoocoin)
                    putExtra("userId", userId)
                    putExtra("userName", userName)
                }

                showToast("우코인 $woocoinCount 개를 구매했습니다. 현재 우코인: $newWoocoin 개")
                startActivity(intent)

            }
            .addOnFailureListener { e ->
                showToast("우코인을 구매하는 동안 오류가 발생했습니다: ${e.message}")
            }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
