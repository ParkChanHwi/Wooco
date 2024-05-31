package com.odal.wooco

import android.os.Bundle
import java.util.Calendar
import java.text.SimpleDateFormat
import android.util.Log
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Locale

class MentiReserve : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menti_reserve)

        val calendarView: MaterialCalendarView = findViewById(R.id.calendar_view)
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val reserveButton: Button = findViewById(R.id.reserve_button)

        reserveButton.setOnClickListener {
            val selectedDate = calendarView.selectedDate?.date ?: Calendar.getInstance().time
            val day = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)

            val hour = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                timePicker.hour
            } else {
                timePicker.currentHour
            }
            val minute = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                timePicker.minute
            } else {
                timePicker.currentMinute
            }
            val time = String.format("%02d:%02d", hour, minute)

            Toast.makeText(this, "선택한 날짜: $day\n선택한 시간: $time", Toast.LENGTH_LONG).show()
        }
    }
}
