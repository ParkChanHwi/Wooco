package com.odal.wooco.utils

import com.google.firebase.Firebase
import com.google.firebase.database.database

class FirebaseRef {

    companion object {
        val database = Firebase.database
        val userInfoRef = database.getReference("userInfo")
    }
}