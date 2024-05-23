package com.odal.wooco.utils

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthUtils {



    private lateinit var  auth : FirebaseAuth
    fun getuid() : String {
        auth = FirebaseAuth.getInstance()

        return auth.currentUser?.uid.toString()
    }

}