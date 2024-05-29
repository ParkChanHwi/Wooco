package com.odal.wooco

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MentiReserve : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mentee_reserve)

        val btnReserve = findViewById<Button>(R.id.reserve_button)
        btnReserve.setOnClickListener {
            Log.d("MainActivity", "Reserve button clicked")
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("예약 확인")
        builder.setMessage("정말 예약 하시겠습니까?")

        builder.setPositiveButton("예") { dialog, _ ->
            // 예약 처리 코드를 여기에 추가합니다
            dialog.dismiss()
        }

        builder.setNegativeButton("아니오") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}