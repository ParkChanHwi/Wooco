package com.odal.wooco.utils

import com.google.firebase.Firebase
import com.google.firebase.database.database

class FirebaseRef {

    companion object {
        val database = Firebase.database
        val categoryInfoRef = database.getReference("category")
        val userInfoRef = database.getReference("userInfo")
        val coachInfoRef = database.getReference("coachInfo")
        val chats = database.getReference("chats")
        val consultRef = database.getReference("consult")
        val classRef = database.getReference("class")
        val reserveInfoRef = database.getReference("reserveInfo") //예약정보 데이터베이스 6/4(금)
        val categoriesRef = database.getReference("coach_categories")
    }
}