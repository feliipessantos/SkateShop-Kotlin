package com.example.skateshop

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class DB {
    fun saveUserData(name: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val users = hashMapOf(
            "name" to name
        )

        val documentReference: DocumentReference = db.collection("Users").document(userId)
        documentReference.set(users).addOnSuccessListener {
            Log.d("DB", "Sucess")
        }.addOnFailureListener { erro ->
            Log.d("DB", "Error ${erro.printStackTrace()}")
        }
    }
}
