package com.odal.wooco.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.odal.wooco.datamodels.CoachDataModel

class FirebaseAuthUtils {

    companion object {
        private lateinit var auth: FirebaseAuth

        fun getUid(): String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }

        fun getCoachName(callback: (String?) -> Unit) { //코치 이름을 받아오는 코드
            val uid = getUid()
            val databaseReference = FirebaseDatabase.getInstance().getReference("coacheInfo").child(uid)
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val coachDataModel = snapshot.getValue(CoachDataModel::class.java)
                    callback(coachDataModel?.name)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }
            })
        }
    }
}
